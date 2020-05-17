package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.DeviceGroupDto;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.DeviceGroupEntity;
import com.foxconn.iot.entity.DeviceGroupRelationEntity;
import com.foxconn.iot.entity.DeviceGroupRelationVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.CompanyRepository;
import com.foxconn.iot.repository.DeviceGroupRelationRepository;
import com.foxconn.iot.repository.DeviceGroupRepository;
import com.foxconn.iot.service.DeviceGroupService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

	@Autowired
	private DeviceGroupRepository deviceGroupRepository;
	@Autowired
	private DeviceGroupRelationRepository deviceGroupRelationRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	@Transactional
	public void create(DeviceGroupDto dgo) {
		if (!StringUtils.isStrictlyNumeric(dgo.getCompanyId()))
			throw new BizException("Invalid company");
		long companyId = Long.parseLong(dgo.getCompanyId());
		CompanyEntity company = companyRepository.findById(companyId);
		if (company == null)
			throw new BizException("Invalid company");
		
		DeviceGroupEntity entity = new DeviceGroupEntity();
		BeanUtils.copyProperties(dgo, entity);
		entity.setCompany(company);
		
		List<DeviceGroupRelationEntity> relations = new ArrayList<>();

		DeviceGroupRelationEntity self = new DeviceGroupRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		relations.add(self);

		if (!StringUtils.isNullOrEmpty(dgo.getAncestor())) {
			String[] ancestors = dgo.getAncestor().split(",");
			String descendant = dgo.getAncestor().substring(dgo.getAncestor().lastIndexOf(",") + 1);
			if (ancestors.length > 0 && !StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				List<Long> ancestors_ = deviceGroupRelationRepository.queryAncestorByDescendant(descendant_);
				int length = ancestors_.size();

				if (length != ancestors.length) {
					throw new BizException("Invalid device group relations");
				}

				for (int i = 0; i < length; i++) {

					if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
						throw new BizException("Invalid menu relations");
					}

					DeviceGroupRelationEntity relation = new DeviceGroupRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}
			}
		}

		long id = Snowflaker.getId();
		entity.setId(id);
		deviceGroupRepository.save(entity);
		deviceGroupRelationRepository.saveAll(relations);
	}

	@Override
	@Transactional
	public void save(DeviceGroupDto dgo) {
		DeviceGroupEntity entity = deviceGroupRepository.findById(dgo.getId());
		if (entity == null) {
			throw new BizException("Invalid device group");
		}
		if (!StringUtils.isNullOrEmpty(dgo.getName())) {
			entity.setName(dgo.getName());
		}
		deviceGroupRepository.save(entity);
		if (StringUtils.isNullOrEmpty(dgo.getAncestor()))
			return;
		/** 当前菜单的层级关系 */
		List<Long> ancestorsOld = deviceGroupRelationRepository.queryAncestorByDescendant(entity.getId());
		String descendant = dgo.getAncestor().substring(dgo.getAncestor().lastIndexOf(",") + 1);
		if (StringUtils.isStrictlyNumeric(descendant))
			return;

		String[] ancestors = dgo.getAncestor().split(",");
		long descendant_ = Long.parseLong(descendant);

		/** 传入菜单层级查询结果 */
		List<Long> ancestors_ = deviceGroupRelationRepository.queryAncestorByDescendant(descendant_);

		/** 与现有层级相比较，如果不修改部门层级关系 */
		ancestorsOld.removeAll(ancestors_);
		if (ancestorsOld.size() == 1 && ancestorsOld.get(0) == entity.getId()) {
			/** 不需要修改层级关系 */
		} else {

			int length = ancestors_.size();
			List<DeviceGroupRelationEntity> relations = new ArrayList<>();

			for (int i = 0; i < length; i++) {

				if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
					throw new BizException("Invalid menu relations");
				}

				DeviceGroupRelationEntity relation = new DeviceGroupRelationEntity();
				relation.setAncestor(ancestors_.get(i));
				relation.setDescendant(entity.getId());
				relation.setDepth(length - i);
				relations.add(relation);
			}

			/** 删除旧的层级关系 */
			deviceGroupRelationRepository.deleteByDescendant(entity.getId());

			DeviceGroupRelationEntity self = new DeviceGroupRelationEntity();
			self.setAncestor(entity.getId());
			self.setDescendant(entity.getId());
			self.setDepth(0);
			relations.add(self);
			deviceGroupRelationRepository.saveAll(relations);
		}
	}

	private List<DeviceGroupDto> sort(List<DeviceGroupRelationVo> gdrvs, boolean valid) {
		List<DeviceGroupDto> dtos = new ArrayList<>();

		/** 缓存自身的序号 */
		List<Long> indexes = new LinkedList<>();
		/** 缓存是否为根节点 */
		List<Boolean> rootIndexes = new LinkedList<>();
		/** 时候实锤为字节点 */
		List<Boolean> realDescendants = new LinkedList<>();
		List<DeviceGroupDto> selfs = new ArrayList<>();
		for (DeviceGroupRelationVo dgrv : gdrvs) {
			int idx = indexes.indexOf(dgrv.getId());
			/** 自身放入缓存 depth = 0 */
			if (idx == -1) {
				DeviceGroupDto dto = new DeviceGroupDto();
				BeanUtils.copyProperties(dgrv, dto);
				selfs.add(dto);
				indexes.add(dgrv.getId());
				/** 禁用的节点要不要显示 */
				if (valid) {
					rootIndexes.add(dgrv.getStatus() == 0);
				} else {
					rootIndexes.add(false);
				}
				realDescendants.add(false);
			} else {
				/** 存放关系 */

				/** 深度大于0（按深度升序），说明存在从属关系，将该改部门存储到父节点的部门列表中 */
				rootIndexes.set(idx, false);
				int ancestor = indexes.indexOf(dgrv.getAncestor());
				if (ancestor > -1) {
					if (selfs.get(ancestor).getDescendants() == null) {
						List<DeviceGroupDto> dtos_ = new ArrayList<>();
						dtos_.add(selfs.get(idx));
						selfs.get(ancestor).setDescendants(dtos_);
					} else {
						if (!realDescendants.get(idx)) {
							selfs.get(ancestor).getDescendants().add(selfs.get(idx));
						}
					}
					realDescendants.set(idx, true);
				}
			}
		}
		for (int i = 0; i < rootIndexes.size(); i++) {
			if (rootIndexes.get(i)) {
				dtos.add(selfs.get(i));
			}
		}
		return dtos;
	}
	
	@Override
	public List<DeviceGroupDto> queryByCompany(long companyId) {
		List<DeviceGroupRelationVo> dgrvs = deviceGroupRepository.queryDescendants(companyId);
		return sort(dgrvs, true);
	}

	@Override
	@Transactional
	public void updateStatusByIdAndCompany(int status, long id, long companyId) {
		deviceGroupRepository.updateStatusByIdAndCompany(status, id, companyId);
	}

	@Override
	@Transactional
	public void deleteByIdAndCompany(long id, long companyId) {
		deviceGroupRepository.deleteByIdAndCompany(id, companyId);
	}

}

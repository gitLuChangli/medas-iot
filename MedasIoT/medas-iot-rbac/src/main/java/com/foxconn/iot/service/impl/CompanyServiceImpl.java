package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.CompanyDto;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.CompanyRelationEntity;
import com.foxconn.iot.entity.CompanyRelationVo;
import com.foxconn.iot.entity.RoleEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.CompanyRelationRepository;
import com.foxconn.iot.repository.CompanyRepository;
import com.foxconn.iot.repository.RoleRepository;
import com.foxconn.iot.service.CompanyService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyRelationRepository companyRelationRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public CompanyDto crate(CompanyDto company) {
		/** 保存部门信息 */
		CompanyEntity entity = new CompanyEntity();
		BeanUtils.copyProperties(company, entity);
		entity.setId(Snowflaker.getId());
		companyRepository.save(entity);
		company.setId(entity.getId());
		
		/** 保存部门层级关系 */
		CompanyRelationEntity self = new CompanyRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		
		List<CompanyRelationEntity> relations = new ArrayList<>();
		if (company.getAncestorIds() != null && company.getAncestorIds().length > 0) {
			int length = company.getAncestorIds().length;		
			String descendant = company.getAncestorIds()[length - 1];
			if (!StringUtils.isStrictlyNumeric(descendant)) {
				throw new BizException("invalid company relations");
			}
			long descendant_ = Long.parseLong(descendant);
			List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
			if (length != ancestors_.size()) {
				throw new BizException("Invalid company relations");
			}
			for (int i = 0; i < length; i++) {
				if (!(ancestors_.get(i) + "").equals(company.getAncestorIds()[i])) {
					throw new BizException("Invalid company relations");
				}
				CompanyRelationEntity relation = new CompanyRelationEntity();
				relation.setAncestor(ancestors_.get(i));
				relation.setDescendant(entity.getId());
				relation.setDepth(length - i);
				relations.add(relation);
			}				
		} else {
			self.setRoot(1);
		}
		relations.add(self);
		companyRelationRepository.saveAll(relations);
		return company;
	}

	@Override
	@Transactional
	public CompanyDto save(CompanyDto company) {
		CompanyEntity entity = companyRepository.findByCode(company.getCode());
		if (entity == null) {
			throw new BizException("Invalid company");
		}
		/** 修改部门基本信息 */
		if (!StringUtils.isNullOrEmpty(company.getName())) {
			entity.setName(company.getName());
		}
		entity.setDetails(company.getDetails());
		entity.setRegion(company.getRegion());
		entity.setArea(company.getArea());
		if (!StringUtils.isNullOrEmpty(company.getProvince()) && !StringUtils.isNullOrEmpty(company.getCity()) && 
				!StringUtils.isNullOrEmpty(company.getCounty()) && !StringUtils.isNullOrEmpty(company.getAddress())) {
			entity.setProvince(company.getProvince());
			entity.setCity(company.getCity());
			entity.setCounty(company.getCounty());
			entity.setAddress(company.getAddress());
		}
		companyRepository.save(entity);
		BeanUtils.copyProperties(entity, company);
		
		/** 修改部门的层级关系 */
		if (company.getAncestorIds() != null && company.getAncestorIds().length > 0) {
			int length = company.getAncestorIds().length;
			String descendant = company.getAncestorIds()[length - 1];
			if (!StringUtils.isStrictlyNumeric(descendant)) {
				throw new BizException("invalid company relations");
			}
			/** 当前部门的层级关系，注意要去除自己 */
			List<Long> ancestorsOld = companyRelationRepository.queryAncestorByDescendant(entity.getId());
			if (ancestorsOld != null && ancestorsOld.size() > 0) {
				ancestorsOld.remove(ancestorsOld.size() - 1);
			}
			boolean modify = true;
			if (ancestorsOld.size() == length) {
				for (int i = 0; i < ancestorsOld.size(); i++) {
					if (!company.getAncestorIds()[i].equals(ancestorsOld.get(i) + "")) {
						modify = true;
						break;
					}
				}
			} else {
				modify = true;
			}
			if (modify) {
				long descendant_ = Long.parseLong(descendant);
				/** 传入部门层级查询结果 */
				List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
				List<CompanyRelationEntity> relations = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(company.getAncestorIds()[i])) {
						throw new BizException("Invalid company relations");
					}
					CompanyRelationEntity relation = new CompanyRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}
				
				/** 删除旧的层级关系 */
				companyRelationRepository.deleteByDescendant(entity.getId());
				
				CompanyRelationEntity self = new CompanyRelationEntity();
				self.setAncestor(entity.getId());
				self.setDescendant(entity.getId());
				self.setDepth(0);
				relations.add(self);
				companyRelationRepository.saveAll(relations);
			}
		}
		return company;
	}

	@Override
	public CompanyDto findById(long id) {
		CompanyEntity entity = companyRepository.findById(id);
		if (entity != null) {
			CompanyDto dto = new CompanyDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		companyRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void delelteById(long id) {
		companyRepository.deleteById(id);
		companyRelationRepository.deleteByDescendant(id);
		companyRelationRepository.deleteByAncestor(id);
	}

	@Override
	@Transactional
	public void updateRolesById(List<Long> roles, long id) {
		List<RoleEntity> entities = roleRepository.queryByIds(roles);
		companyRepository.updateRolesById(entities, id);
	}
	
	/** 在插入部门信息时，部门主键一定要满足正向增加 */	 
	private List<CompanyDto> sort(List<CompanyRelationVo> crs, boolean valid) {
		/** 先获取部门的层级关系，需要去除自身 */
		List<CompanyRelationEntity> companyRelations = companyRelationRepository.findAll();
		Map<String, String[]> companyMap = new HashMap<>();
		String companyId;
		int length;
		for (CompanyRelationEntity cre : companyRelations) {
			companyId = cre.getDescendant() + "";
			if (cre.getDepth() == 0) continue;
			if (companyMap.containsKey(companyId)) {
				length = companyMap.get(companyId).length;
				companyMap.get(companyId)[length - cre.getDepth()] = cre.getAncestor() + "";
			} else {
				String[] ids = new String[cre.getDepth()];
				ids[0] = cre.getAncestor() + "";
				companyMap.put(companyId, ids);
			}
		}
		
		List<CompanyDto> dtos = new ArrayList<>();
		/** 缓存自身的序号 */
		List<Long> indexes = new LinkedList<>();
		/** 缓存是否为根节点 */
		List<Boolean> rootIndexes = new LinkedList<>();
		/** 时候实锤为字节点 */
		List<Boolean> realDescendants = new LinkedList<>();
		List<CompanyDto> selfs = new ArrayList<>();
		for (CompanyRelationVo cr : crs) {
			int descendant = indexes.indexOf(cr.getId());
			/** 自身放入缓存 depth = 0 */
			if (descendant == -1) {
				CompanyDto dto = new CompanyDto();
				BeanUtils.copyProperties(cr, dto);
				selfs.add(dto);
				if (companyMap.containsKey(cr.getId() + "")) {
					dto.setAncestorIds(companyMap.get(cr.getId() + ""));
				}
				indexes.add(cr.getId());
				/** 禁用的节点要不要显示 */
				if (valid) {
					rootIndexes.add(cr.getStatus() == 0);
				} else {
					rootIndexes.add(false);
				}
				realDescendants.add(false);
			} else {
				/** 存放关系 */
				
				/** 深度大于0（按深度升序），说明存在从属关系，将该改部门存储到父节点的部门列表中 */
				rootIndexes.set(descendant, false);
				int ancestor = indexes.indexOf(cr.getAncestor());
				if (ancestor > -1) {
					if (selfs.get(ancestor).getDescendants() == null) {
						List<CompanyDto> dtos_ = new ArrayList<>();
						dtos_.add(selfs.get(descendant));
						selfs.get(ancestor).setDescendants(dtos_);
					} else {
						if (!realDescendants.get(descendant)) {
							selfs.get(ancestor).getDescendants().add(selfs.get(descendant));
						}
					}
					realDescendants.set(descendant, true);
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
	public List<CompanyDto> queryDescendants() {
		List<CompanyRelationVo> crs = companyRepository.queryDescendants();
		return sort(crs, true);
	}

	@Override
	public List<CompanyDto> queryDescendantsByAncestor(long ancestor) {
		List<CompanyRelationVo> crs = companyRepository.queryDescendantsByAncestor(ancestor);
		return sort(crs, true);
	}
}

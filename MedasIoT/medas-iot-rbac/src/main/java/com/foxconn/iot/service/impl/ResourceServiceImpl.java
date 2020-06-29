package com.foxconn.iot.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ResourceDto;
import com.foxconn.iot.entity.ResourceEntity;
import com.foxconn.iot.entity.ResourceRelationEntity;
import com.foxconn.iot.entity.ResourceRelationVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ResourceRelationRepository;
import com.foxconn.iot.repository.ResourceRepository;
import com.foxconn.iot.service.ResourceService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private ResourceRelationRepository resourceRelationRepository;

	@Override
	@Transactional
	public void create(ResourceDto res) {
		ResourceEntity entity = new ResourceEntity();
		BeanUtils.copyProperties(res, entity);
		entity.setId(Snowflaker.getId());
		resourceRepository.save(entity);
		
		List<ResourceRelationEntity> relations = new ArrayList<>();
		ResourceRelationEntity self = new ResourceRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		relations.add(self);

		if (res.getAncestorIds() != null && res.getAncestorIds().length > 0) {
			int length = res.getAncestorIds().length;
			String descendant = res.getAncestorIds()[length - 1];
			if (StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				List<Long> ancestors_ = resourceRelationRepository.queryAncestorsByDescendant(descendant_);
				if (ancestors_.size() != length) {
					throw new BizException("Invalid resource relations");
				}
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(res.getAncestorIds()[i])) {
						throw new BizException("Invalid resource relations");
					}
					ResourceRelationEntity relation = new ResourceRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}
			}
		}
		resourceRelationRepository.saveAll(relations);
	}

	@Override
	@Transactional
	public void save(ResourceDto res) {
		ResourceEntity entity = resourceRepository.findById(res.getId());
		if (entity == null) {
			throw new BizException("Invalid resource");
		}
		if (!StringUtils.isNullOrEmpty(res.getTitle())) {
			entity.setTitle(res.getTitle());
		}
		entity.setDetails(res.getDetails());
		entity.setIcon(res.getIcon());
		entity.setUrl(res.getUrl());
		entity.setUrl(res.getMethod());
		entity.setIndex(res.getIndex());
		resourceRepository.save(entity);

		if (res.getAncestorIds() != null && res.getAncestorIds().length > 0) {
			int length = res.getAncestorIds().length;
			String descendant = res.getAncestorIds()[length - 1];
			if (!StringUtils.isStrictlyNumeric(descendant)) {
				throw new BizException("Invalid relations");
			}
			/** 当前菜单的层级关系，要去除自己 */
			List<Long> ancestorsOld = resourceRelationRepository.queryAncestorsByDescendant(entity.getId());
			if (ancestorsOld != null && ancestorsOld.size() > 0) {
				ancestorsOld.remove(entity.getId());
			}
			boolean modify = true;
			if (ancestorsOld.size() == length) {
				for (int i = 0; i < ancestorsOld.size(); i++) {
					if (!res.getAncestorIds()[i].equals(ancestorsOld.get(i) + "")) {
						modify = true;
						break;
					}
				}
			} else {
				modify = true;
			}
			if (modify) {
				long descendant_ = Long.parseLong(descendant);
				/** 传入菜单层级查询结果 */
				List<Long> ancestors_ = resourceRelationRepository.queryAncestorsByDescendant(descendant_);				
				List<ResourceRelationEntity> relations = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(res.getAncestorIds()[i])) {
						throw new BizException("Invalid res relations");
					}
					ResourceRelationEntity relation = new ResourceRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}

				/** 删除旧的层级关系 */
				resourceRelationRepository.deleteByDescendant(entity.getId());

				ResourceRelationEntity self = new ResourceRelationEntity();
				self.setAncestor(entity.getId());
				self.setDescendant(entity.getId());
				self.setDepth(0);
				relations.add(self);
				resourceRelationRepository.saveAll(relations);
			}
		}
	}

	@Override
	public ResourceDto findById(long id) {
		ResourceEntity entity = resourceRepository.findById(id);
		if (entity != null) {
			ResourceDto dto = new ResourceDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		resourceRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		resourceRepository.deleteById(id);
		resourceRelationRepository.deleteByAncestor(id);
	}

	private List<ResourceDto> sort(List<ResourceRelationVo> mrs, boolean valid, int type) {
		/** 菜单层级关系，注意要去除自己 */
		List<ResourceRelationEntity> resourceRelations = resourceRelationRepository.findByType(type);
		Map<String, String[]> resourcesMap = new HashMap<>();
		int length = 0;
		String resourceId;
		for (ResourceRelationEntity resource : resourceRelations) {
			if (resource.getDepth() == 0) continue;
			resourceId = resource.getDescendant() + "";
			if (resourcesMap.containsKey(resourceId)) {
				length = resourcesMap.get(resourceId).length;
				resourcesMap.get(resourceId)[length - resource.getDepth()] = resource.getAncestor() + "";
			} else {
				String[] ids = new String[resource.getDepth()];
				ids[0] = resource.getAncestor() + "";
				resourcesMap.put(resourceId, ids);
			}
		}
		
		List<ResourceDto> dtos = new ArrayList<>();
		/** 缓存自身的序号 */
		List<Long> indexes = new LinkedList<>();
		/** 缓存是否为根节点 */
		List<Boolean> rootIndexes = new LinkedList<>();
		/** 时候实锤为字节点 */
		List<Boolean> realDescendants = new LinkedList<>();
		List<ResourceDto> selfs = new ArrayList<>();
		for (ResourceRelationVo mr : mrs) {
			int descendant = indexes.indexOf(mr.getId());
			/** 自身放入缓存 depth = 0 */
			if (descendant == -1) {
				ResourceDto dto = new ResourceDto();
				BeanUtils.copyProperties(mr, dto);
				if (resourcesMap.containsKey(mr.getId() + "")) {
					dto.setAncestorIds(resourcesMap.get(mr.getId() + ""));
				}
				selfs.add(dto);
				indexes.add(mr.getId());
				/** 禁用的节点要不要显示 */
				if (valid) {
					rootIndexes.add(mr.getStatus() == 0);
				} else {
					rootIndexes.add(true);
				}
				realDescendants.add(false);
			} else {
				/** 存放关系 */

				/** 深度大于0（按深度升序），说明存在从属关系，将该改部门存储到父节点的部门列表中 */
				rootIndexes.set(descendant, false);
				int ancestor = indexes.indexOf(mr.getAncestor());
				if (ancestor > -1) {
					if (selfs.get(ancestor).getDescendants() == null) {
						List<ResourceDto> dtos_ = new ArrayList<>();
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
	public List<ResourceDto> queryDescendants(boolean valid, int type) {
		List<ResourceRelationVo> relations = resourceRepository.queryDescendants(type);
		return sort(relations, valid, type);
	}

	@Override
	public List<ResourceDto> queryDescendantsByRoleIds(Long[] roleIds, int type) {
		List<Object> objs = resourceRepository.queryByRoleIds(roleIds, type);
		List<Long> ancestors = new ArrayList<>();
		for (Object obj : objs) {
			ancestors.add(new BigInteger(obj.toString()).longValue());
		}
		List<ResourceRelationVo> relations = resourceRepository.queryDescendantsByAncestors(ancestors);
		return sort(relations, true, type);
	}
	
	@Override
	public List<ResourceDto> queryDescendantsByUserId(long userid, int type) {
		List<Object> objs = resourceRepository.queryByUserId(userid, type);
		List<Long> ancestors = new ArrayList<>();
		for (Object obj : objs) {
			ancestors.add(new BigInteger(obj.toString()).longValue());
		}
		List<ResourceRelationVo> relations = resourceRepository.queryDescendantsByAncestors(ancestors);
		return sort(relations, true, type);
	}
}

package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.dto.ResourceDto;
import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.entity.ResourceEntity;
import com.foxconn.iot.entity.ResourceRelationEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.PermissionRepository;
import com.foxconn.iot.repository.ResourceRelationRepository;
import com.foxconn.iot.repository.ResourceRepository;
import com.foxconn.iot.service.PermissionService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private ResourceRelationRepository resourceRelationRepository;

	@Override
	public void create(PermissionDto permission) {
		PermissionEntity entity = new PermissionEntity();
		BeanUtils.copyProperties(permission, entity);
		List<Long> ids = new ArrayList<>();
		if (permission.getMenuIds() != null && permission.getMenuIds().size() > 0) {
			for (String[] items : permission.getMenuIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
		}
		if (permission.getButtonIds() != null && permission.getButtonIds().size() > 0) {
			
			for (String[] items : permission.getButtonIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
		}
		Set<ResourceEntity> resources = resourceRepository.queryByIds(ids);
		entity.setResources(resources);
		entity.setId(Snowflaker.getId());
		permissionRepository.save(entity);
	}

	@Override
	public void save(PermissionDto permission) {
		PermissionEntity entity = permissionRepository.findById(permission.getId());
		if (entity == null) {
			throw new BizException("Invalid permission");
		}
		if (!StringUtils.isNullOrEmpty(permission.getTitle())) {
			entity.setTitle(permission.getTitle());
		}
		entity.setDetails(permission.getDetails());
		List<Long> ids = new ArrayList<>();
		if (permission.getMenuIds() != null && permission.getMenuIds().size() > 0) {
			for (String[] items : permission.getMenuIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
		}
		if (permission.getButtonIds() != null && permission.getButtonIds().size() > 0) {
			for (String[] items : permission.getButtonIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
		}
		if (ids.size() == 0) {
			entity.setResources(null);
		} else {
			Set<ResourceEntity> resources = resourceRepository.queryByIds(ids);
			entity.setResources(resources);
		}
		permissionRepository.save(entity);
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		permissionRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		permissionRepository.deleteById(id);
	}

	@Override
	public List<PermissionDto> findAll() {
		List<ResourceRelationEntity> resRelations = resourceRelationRepository.findAll();
		Map<String, String[]> resMap = new HashMap<>();
		int length = 0;
		String descendant;
		for (ResourceRelationEntity res : resRelations) {
			descendant = res.getDescendant() + "";
			if (resMap.containsKey(descendant)) {
				length = resMap.get(descendant).length;
				resMap.get(descendant)[length - res.getDepth() - 1] = res.getAncestor() + "";
			} else {
				String[] ids = new String[res.getDepth() + 1];
				ids[0] = res.getAncestor() + "";
				resMap.put(descendant, ids);
			}
		}
		List<PermissionEntity> entities = permissionRepository.findAll();
		List<PermissionDto> dtos = new ArrayList<>();
		for (PermissionEntity entity : entities) {
			PermissionDto dto = new PermissionDto();
			BeanUtils.copyProperties(entity, dto);
			List<ResourceDto> menus = new ArrayList<>();
			List<ResourceDto> buttons = new ArrayList<>();
			List<String[]> menuIds = new ArrayList<>();
			List<String[]> buttonIds = new ArrayList<>();
			for (ResourceEntity res : entity.getResources()) {
				ResourceDto m = new ResourceDto();
				BeanUtils.copyProperties(res, m);
				if (res.getType() == 0) {
					menus.add(m);
					menuIds.add(resMap.get(m.getId() + ""));
				} else {
					buttons.add(m);
					buttonIds.add(resMap.get(m.getId() + ""));
				}
				
			}
			dto.setMenuList(menus);
			dto.setMenuIds(menuIds);
			dto.setButtonList(buttons);
			dto.setButtonIds(buttonIds);
			dtos.add(dto);
		}
		return dtos;
	}
}

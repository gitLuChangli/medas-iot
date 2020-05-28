package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.dto.RoleDto;
import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.entity.RoleEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.PermissionRepository;
import com.foxconn.iot.repository.RoleRepository;
import com.foxconn.iot.service.RoleService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public void create(RoleDto role) {
		RoleEntity entity = new RoleEntity();
		BeanUtils.copyProperties(role, entity);
		if (!StringUtils.isNullOrEmpty(role.getPermissions())) {
			String[] items = role.getPermissions().split(",'");
			List<Long> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Long.parseLong(item));
				}
			}

			List<PermissionEntity> permissions = permissionRepository.findByIdIn(ids);
			entity.setPermissions(permissions);
		}
		entity.setId(Snowflaker.getId());
		roleRepository.save(entity);
	}

	@Override
	public void save(RoleDto role) {
		RoleEntity entity = roleRepository.findById(role.getId());
		if (entity == null) {
			throw new BizException("Invalid role");
		}
		if (!StringUtils.isNullOrEmpty(role.getDetails())) {
			entity.setDetails(role.getDetails());
		}

		if (!StringUtils.isNullOrEmpty(role.getPermissions())) {
			String[] items = role.getPermissions().split(",'");
			List<Long> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Long.parseLong(item));
				}
			}

			List<PermissionEntity> permissions = permissionRepository.findByIdIn(ids);
			entity.setPermissions(permissions);
		}
		roleRepository.save(entity);
	}
	
	@Override
	public RoleDto findById(long id) {
		RoleEntity entity = roleRepository.findById(id);
		if (entity != null) {
			RoleDto dto = new RoleDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		roleRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public List<PermissionDto> queryPermissionsById(long id) {
		List<PermissionEntity> permissions = roleRepository.queryPermissionsById(id);
		List<PermissionDto> dtos = new ArrayList<>();
		for (PermissionEntity permission : permissions) {
			PermissionDto dto = new PermissionDto();
			BeanUtils.copyProperties(permission, dto);
			dtos.add(dto);
		}
		return dtos;
	}
}

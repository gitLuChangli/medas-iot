package com.foxconn.iot.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.dto.RoleDto;
import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.entity.RoleEntity;
import com.foxconn.iot.entity.RolePermissionVo;
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
		if (role.getPermissionIds() != null && role.getPermissionIds().length > 0) {
			List<Long> ids = new ArrayList<>();
			for (String item : role.getPermissionIds()) {
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
		if (role.getPermissionIds() != null && role.getPermissionIds().length > 0) {
			List<Long> ids = new ArrayList<>();
			for (String item : role.getPermissionIds()) {
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

	@Override
	public List<RolePermissionVo> queryAll() {
		List<RolePermissionVo> roles = new ArrayList<>();
		List<Object[]> roles_ = roleRepository.queryAll();
		for (Object[] role : roles_) {
			RolePermissionVo vo = new RolePermissionVo();
			if (role != null && role.length == 9) {
				vo.setId(role[0].toString());
				vo.setName((String) role[1]);
				vo.setTitle((String)role[2]);
				vo.setDetails((String) role[3]);
				vo.setCreateOn((Date) role[4]);
				vo.setStatus((int) role[5]);
				vo.setPermissionNames(role[6].toString().split(","));
				vo.setPermissionTitles(role[7].toString().split(","));
				vo.setPermissionIds(role[8].toString().split(","));
				roles.add(vo);
			}
		}
		return roles;
	}
	
	@Override
	public List<Long> queryIds(long userid) {
		List<Object> objs = roleRepository.queryIds(userid);
		List<Long> ids = new ArrayList<>();
		for (Object obj : objs) {
			ids.add(new BigInteger(obj.toString()).longValue());
		}
		return ids;
	}
}

package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.MenuDto;
import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.entity.MenuEntity;
import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.MenuRepository;
import com.foxconn.iot.repository.PermissionRepository;
import com.foxconn.iot.service.PermissionService;
import com.mysql.cj.util.StringUtils;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public void create(PermissionDto permission) {
		PermissionEntity entity = new PermissionEntity();
		BeanUtils.copyProperties(permission, entity);
		if (!StringUtils.isNullOrEmpty(permission.getMenus())) {
			String[] items = permission.getMenus().split(",");
			List<Integer> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Integer.parseInt(item));
				}
			}

			List<MenuEntity> menues = menuRepository.queryByIds(ids);
			entity.setMenus(menues);
		}
		permissionRepository.save(entity);
	}

	@Override
	public void save(PermissionDto permission) {
		PermissionEntity entity = permissionRepository.findById(permission.getId());
		if (entity == null) {
			throw new BizException("Invalid permission");
		}
		if (!StringUtils.isNullOrEmpty(permission.getDetails())) {
			entity.setDetails(permission.getDetails());
		}
		if (!StringUtils.isNullOrEmpty(permission.getMenus())) {
			String[] items = permission.getMenus().split(",");
			List<Integer> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Integer.parseInt(item));
				}
			}

			List<MenuEntity> menues = menuRepository.queryByIds(ids);
			entity.setMenus(menues);
		}
		permissionRepository.save(entity);
	}

	@Override
	public PermissionDto findById(int id) {
		PermissionEntity entity = permissionRepository.findById(id);
		if (entity != null) {
			PermissionDto dto = new PermissionDto();
			BeanUtils.copyProperties(entity, dto);

			List<MenuEntity> menus = permissionRepository.queryMenusById(id);
			if (menus.size() > 0) {
				List<MenuDto> menuList = new ArrayList<>();
				for (MenuEntity menu : menus) {
					MenuDto md = new MenuDto();
					BeanUtils.copyProperties(menu, md);
					menuList.add(md);
				}
				dto.setMenuList(menuList);
			}
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, int id) {
		permissionRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		permissionRepository.deleteById(id);
	}

}

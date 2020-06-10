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

import com.foxconn.iot.dto.ButtonDto;
import com.foxconn.iot.dto.MenuDto;
import com.foxconn.iot.dto.PermissionDto;
import com.foxconn.iot.entity.ButtonEntity;
import com.foxconn.iot.entity.ButtonRelationEntity;
import com.foxconn.iot.entity.MenuEntity;
import com.foxconn.iot.entity.MenuRelationEntity;
import com.foxconn.iot.entity.PermissionEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ButtonRelationRepository;
import com.foxconn.iot.repository.ButtonRepository;
import com.foxconn.iot.repository.MenuRelationRepository;
import com.foxconn.iot.repository.MenuRepository;
import com.foxconn.iot.repository.PermissionRepository;
import com.foxconn.iot.service.PermissionService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private ButtonRepository buttonRepository;
	@Autowired
	private MenuRelationRepository menuRelationRepository;
	@Autowired
	private ButtonRelationRepository buttonRelationRepository;

	@Override
	public void create(PermissionDto permission) {
		PermissionEntity entity = new PermissionEntity();
		BeanUtils.copyProperties(permission, entity);
		if (permission.getMenuIds() != null && permission.getMenuIds().size() > 0) {
			List<Long> ids = new ArrayList<>();
			for (String[] items : permission.getMenuIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
			Set<MenuEntity> menues = menuRepository.queryByIds(ids);
			entity.setMenus(menues);
		}
		if (permission.getButtonIds() != null && permission.getButtonIds().size() > 0) {
			List<Long> ids = new ArrayList<>();
			for (String[] items : permission.getButtonIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
			Set<ButtonEntity> buttons = buttonRepository.queryByIds(ids);
			entity.setButtons(buttons);
		}
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
		if (permission.getMenuIds() != null && permission.getMenuIds().size() > 0) {
			List<Long> ids = new ArrayList<>();
			for (String[] items : permission.getMenuIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
			if (entity.getMenus() != null) {

			}
			Set<MenuEntity> menues = menuRepository.queryByIds(ids);
			entity.setMenus(menues);
		} else {
			entity.setMenus(null);
		}
		if (permission.getButtonIds() != null && permission.getButtonIds().size() > 0) {
			List<Long> ids = new ArrayList<>();
			for (String[] items : permission.getButtonIds()) {
				if (items != null && items.length > 0) {
					String descendant = items[items.length - 1];
					if (StringUtils.isStrictlyNumeric(descendant)) {
						ids.add(Long.parseLong(descendant));
					}
				}
			}
			Set<ButtonEntity> buttons = buttonRepository.queryByIds(ids);
			entity.setButtons(buttons);
		} else {
			entity.setButtons(null);
		}
		permissionRepository.save(entity);
	}

	@Override
	public PermissionDto findById(long id) {
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

			List<ButtonEntity> buttons = permissionRepository.queryButtonsById(id);
			if (buttons.size() > 0) {
				List<ButtonDto> buttonList = new ArrayList<>();
				for (ButtonEntity button : buttons) {
					ButtonDto bd = new ButtonDto();
					BeanUtils.copyProperties(button, dto);
					buttonList.add(bd);
				}
				dto.setButtonList(buttonList);
			}

			return dto;
		}
		return null;
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
		List<MenuRelationEntity> menuRelations = menuRelationRepository.findAll();
		Map<String, String[]> menusMap = new HashMap<>();
		int length = 0;
		String descendant;
		for (MenuRelationEntity menu : menuRelations) {
			descendant = menu.getDescendant() + "";
			if (menusMap.containsKey(descendant)) {
				length = menusMap.get(descendant).length;
				menusMap.get(descendant)[length - menu.getDepth() - 1] = menu.getAncestor() + "";
			} else {
				String[] ids = new String[menu.getDepth() + 1];
				ids[0] = menu.getAncestor() + "";
				menusMap.put(descendant, ids);
			}
		}
		List<ButtonRelationEntity> buttonRelations = buttonRelationRepository.findAll();
		Map<String, String[]> buttonsMap = new HashMap<>();
		for (ButtonRelationEntity button : buttonRelations) {
			descendant = button.getDescendant() + "";
			if (buttonsMap.containsKey(descendant)) {
				length = buttonsMap.get(descendant).length;
				buttonsMap.get(descendant)[length - button.getDepth() - 1] = button.getAncestor() + "";
			} else {
				String[] ids = new String[button.getDepth() + 1];
				ids[0] = button.getAncestor() + "";
				buttonsMap.put(descendant, ids);
			}
		}

		List<PermissionEntity> entities = permissionRepository.findAll();
		List<PermissionDto> dtos = new ArrayList<>();
		for (PermissionEntity entity : entities) {
			PermissionDto dto = new PermissionDto();
			BeanUtils.copyProperties(entity, dto);
			List<MenuDto> menus = new ArrayList<>();
			List<String[]> menuIds = new ArrayList<>();
			for (MenuEntity menu : entity.getMenus()) {
				MenuDto m = new MenuDto();
				BeanUtils.copyProperties(menu, m);
				menus.add(m);
				menuIds.add(menusMap.get(m.getId() + ""));
			}
			dto.setMenuList(menus);
			dto.setMenuIds(menuIds);
			List<ButtonDto> buttons = new ArrayList<>();
			List<String[]> buttonIds = new ArrayList<>();
			for (ButtonEntity button : entity.getButtons()) {
				ButtonDto b = new ButtonDto();
				BeanUtils.copyProperties(button, b);
				buttons.add(b);
				buttonIds.add(buttonsMap.get(b.getId() + ""));
			}
			dto.setButtonList(buttons);
			dto.setButtonIds(buttonIds);
			dtos.add(dto);
		}
		return dtos;
	}
}

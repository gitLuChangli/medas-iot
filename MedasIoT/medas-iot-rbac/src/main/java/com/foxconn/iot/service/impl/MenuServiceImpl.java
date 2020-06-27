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

import com.foxconn.iot.dto.MenuDto;
import com.foxconn.iot.entity.MenuEntity;
import com.foxconn.iot.entity.MenuRelationEntity;
import com.foxconn.iot.entity.MenuRelationVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.MenuRelationRepository;
import com.foxconn.iot.repository.MenuRepository;
import com.foxconn.iot.service.MenuService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private MenuRelationRepository menuRelationRepository;

	@Override
	@Transactional
	public void create(MenuDto menu) {
		MenuEntity entity = new MenuEntity();
		BeanUtils.copyProperties(menu, entity);
		entity.setId(Snowflaker.getId());
		menuRepository.save(entity);
		
		List<MenuRelationEntity> relations = new ArrayList<>();
		MenuRelationEntity self = new MenuRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		relations.add(self);

		if (menu.getAncestorIds() != null && menu.getAncestorIds().length > 0) {
			int length = menu.getAncestorIds().length;
			String descendant = menu.getAncestorIds()[length - 1];
			if (StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				List<Long> ancestors_ = menuRelationRepository.queryAncestorsByDescendant(descendant_);
				if (ancestors_.size() != length) {
					throw new BizException("Invalid menu relations");
				}
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(menu.getAncestorIds()[i])) {
						throw new BizException("Invalid menu relations");
					}
					MenuRelationEntity relation = new MenuRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}
			}
		}
		menuRelationRepository.saveAll(relations);
	}

	@Override
	@Transactional
	public void save(MenuDto menu) {
		MenuEntity entity = menuRepository.findById(menu.getId());
		if (entity == null) {
			throw new BizException("Invalid menu");
		}
		if (!StringUtils.isNullOrEmpty(menu.getTitle())) {
			entity.setTitle(menu.getTitle());
		}
		entity.setDetails(menu.getDetails());
		entity.setIcon(menu.getIcon());
		entity.setUrl(menu.getUrl());
		entity.setIndex(menu.getIndex());
		menuRepository.save(entity);

		if (menu.getAncestorIds() != null && menu.getAncestorIds().length > 0) {
			int length = menu.getAncestorIds().length;
			String descendant = menu.getAncestorIds()[length - 1];
			if (!StringUtils.isStrictlyNumeric(descendant)) {
				throw new BizException("Invalid relations");
			}
			/** 当前菜单的层级关系，要去除自己 */
			List<Long> ancestorsOld = menuRelationRepository.queryAncestorsByDescendant(entity.getId());
			if (ancestorsOld != null && ancestorsOld.size() > 0) {
				ancestorsOld.remove(entity.getId());
			}
			boolean modify = true;
			if (ancestorsOld.size() == length) {
				for (int i = 0; i < ancestorsOld.size(); i++) {
					if (!menu.getAncestorIds()[i].equals(ancestorsOld.get(i) + "")) {
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
				List<Long> ancestors_ = menuRelationRepository.queryAncestorsByDescendant(descendant_);				
				List<MenuRelationEntity> relations = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(menu.getAncestorIds()[i])) {
						throw new BizException("Invalid menu relations");
					}
					MenuRelationEntity relation = new MenuRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}

				/** 删除旧的层级关系 */
				menuRelationRepository.deleteByDescendant(entity.getId());

				MenuRelationEntity self = new MenuRelationEntity();
				self.setAncestor(entity.getId());
				self.setDescendant(entity.getId());
				self.setDepth(0);
				relations.add(self);
				menuRelationRepository.saveAll(relations);
			}
		}
	}

	@Override
	public MenuDto findById(long id) {
		MenuEntity entity = menuRepository.findById(id);
		if (entity != null) {
			MenuDto dto = new MenuDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		menuRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		menuRepository.deleteById(id);
		menuRelationRepository.deleteByAncestor(id);
	}

	private List<MenuDto> sort(List<MenuRelationVo> mrs, boolean valid) {
		/** 菜单层级关系，注意要去除自己 */
		List<MenuRelationEntity> menuRelations = menuRelationRepository.findAll();
		Map<String, String[]> menusMap = new HashMap<>();
		int length = 0;
		String menuId;
		for (MenuRelationEntity menu : menuRelations) {
			if (menu.getDepth() == 0) continue;
			menuId = menu.getDescendant() + "";
			if (menusMap.containsKey(menuId)) {
				length = menusMap.get(menuId).length;
				menusMap.get(menuId)[length - menu.getDepth()] = menu.getAncestor() + "";
			} else {
				String[] ids = new String[menu.getDepth()];
				ids[0] = menu.getAncestor() + "";
				menusMap.put(menuId, ids);
			}
		}
		
		List<MenuDto> dtos = new ArrayList<>();
		/** 缓存自身的序号 */
		List<Long> indexes = new LinkedList<>();
		/** 缓存是否为根节点 */
		List<Boolean> rootIndexes = new LinkedList<>();
		/** 时候实锤为字节点 */
		List<Boolean> realDescendants = new LinkedList<>();
		List<MenuDto> selfs = new ArrayList<>();
		for (MenuRelationVo mr : mrs) {
			int descendant = indexes.indexOf(mr.getId());
			/** 自身放入缓存 depth = 0 */
			if (descendant == -1) {
				MenuDto dto = new MenuDto();
				BeanUtils.copyProperties(mr, dto);
				if (menusMap.containsKey(mr.getId() + "")) {
					dto.setAncestorIds(menusMap.get(mr.getId() + ""));
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
						List<MenuDto> dtos_ = new ArrayList<>();
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
	public List<MenuDto> queryDescendants(boolean valid) {
		List<MenuRelationVo> relations = menuRepository.queryDescendants();
		return sort(relations, valid);
	}

	@Override
	public List<MenuDto> queryDescendantsByAncestor(long ancestor) {
		List<MenuRelationVo> relations = menuRepository.queryDescendantsByAncestor(ancestor);
		return sort(relations, true);
	}

	@Override
	public List<Long> queryAncestorsByDescendant(long descendant) {
		return menuRelationRepository.queryAncestorsByDescendant(descendant);
	}

}

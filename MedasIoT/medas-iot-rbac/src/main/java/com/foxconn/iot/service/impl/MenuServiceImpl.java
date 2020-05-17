package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.mysql.cj.util.StringUtils;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private MenuRelationRepository menuRelationRepository;

	@Override
	@Transactional
	public MenuDto create(MenuDto menu) {
		MenuEntity entity = new MenuEntity();
		BeanUtils.copyProperties(menu, entity);
		menuRepository.save(entity);

		List<MenuRelationEntity> relations = new ArrayList<>();

		MenuRelationEntity self = new MenuRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		relations.add(self);

		if (!StringUtils.isNullOrEmpty(menu.getAncestor())) {
			String[] ancestors = menu.getAncestor().split(",");
			String descendant = menu.getAncestor().substring(menu.getAncestor().lastIndexOf(",") + 1);
			if (ancestors.length > 0 && StringUtils.isStrictlyNumeric(descendant)) {
				int descendant_ = Integer.parseInt(descendant);
				List<Integer> ancestors_ = menuRelationRepository.queryAncestorByDescendant(descendant_);
				int length = ancestors_.size();

				if (length != ancestors.length) {
					throw new BizException("Invalid menu relations");
				}

				for (int i = 0; i < length; i++) {

					if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
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
		menu.setId(entity.getId());
		return menu;
	}

	@Override
	@Transactional
	public MenuDto save(MenuDto menu) {
		MenuEntity entity = menuRepository.findById(menu.getId());
		if (entity == null) {
			throw new BizException("Invalid menu");
		}

		if (!StringUtils.isNullOrEmpty(menu.getName())) {
			entity.setName(menu.getName());
		}
		if (!StringUtils.isNullOrEmpty(menu.getIcon())) {
			entity.setIcon(menu.getIcon());
		}
		if (!StringUtils.isNullOrEmpty(menu.getDetails())) {
			entity.setDetails(menu.getDetails());
		}
		if (!StringUtils.isNullOrEmpty(menu.getUrl())) {
			entity.setDetails(menu.getUrl());
		}
		entity.setStatus(menu.getStatus());
		menuRepository.save(entity);
		if (!StringUtils.isNullOrEmpty(menu.getAncestor())) {
			/** 当前菜单的层级关系 */
			List<Integer> ancestorsOld = menuRelationRepository.queryAncestorByDescendant(entity.getId());
			String descendant = menu.getAncestor().substring(menu.getAncestor().lastIndexOf(",") + 1);
			if (StringUtils.isStrictlyNumeric(descendant)) {
				String[] ancestors = menu.getAncestor().split(",");
				int descendant_ = Integer.parseInt(descendant);
				/** 传入菜单层级查询结果 */
				List<Integer> ancestors_ = menuRelationRepository.queryAncestorByDescendant(descendant_);

				/** 与现有层级相比较，如果不修改部门层级关系 */
				ancestorsOld.removeAll(ancestors_);
				if (ancestorsOld.size() == 1 && ancestorsOld.get(0) == entity.getId()) {
					/** 不需要修改层级关系 */
				} else {

					int length = ancestors_.size();
					List<MenuRelationEntity> relations = new ArrayList<>();

					for (int i = 0; i < length; i++) {

						if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
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
		return menu;
	}

	@Override
	public MenuDto findById(int id) {
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
	public void updateStatusById(int status, int id) {
		menuRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		menuRepository.deleteById(id);
	}

	/** 在插入部门信息时，部门主键一定要满足正向增加 */
	private List<MenuDto> sort(List<MenuRelationVo> mrs, boolean valid) {
		List<MenuDto> dtos = new ArrayList<>();

		/** 缓存自身的序号 */
		List<Integer> indexes = new LinkedList<>();
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
				selfs.add(dto);
				indexes.add(mr.getId());
				/** 禁用的节点要不要显示 */
				if (valid) {
					rootIndexes.add(mr.getStatus() == 0);
				} else {
					rootIndexes.add(false);
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
	public List<MenuDto> queryDescendants() {
		List<MenuRelationVo> relations = menuRepository.queryDescendants();
		return sort(relations, true);
	}

	@Override
	public List<MenuDto> queryDescendantsByAncestor(int ancestor) {
		List<MenuRelationVo> relations = menuRepository.queryDescendantsByAncestor(ancestor);
		return sort(relations, true);
	}

}

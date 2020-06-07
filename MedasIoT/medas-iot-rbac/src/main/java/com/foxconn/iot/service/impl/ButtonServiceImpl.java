package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ButtonDto;
import com.foxconn.iot.entity.ButtonEntity;
import com.foxconn.iot.entity.ButtonRelationEntity;
import com.foxconn.iot.entity.ButtonRelationVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ButtonRelationRepository;
import com.foxconn.iot.repository.ButtonRepository;
import com.foxconn.iot.service.ButtonService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class ButtonServiceImpl implements ButtonService {

	@Autowired
	private ButtonRepository buttonRepository;
	@Autowired
	private ButtonRelationRepository buttonRelationRepository;

	@Override
	@Transactional
	public void create(ButtonDto button) {
		ButtonEntity entity = new ButtonEntity();
		BeanUtils.copyProperties(button, entity);
		entity.setId(Snowflaker.getId());
		buttonRepository.save(entity);

		List<ButtonRelationEntity> relations = new ArrayList<>();

		ButtonRelationEntity self = new ButtonRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		relations.add(self);

		if (button.getAncestor() != null && button.getAncestor().length > 0) {
			int length = button.getAncestor().length;
			String descendant = button.getAncestor()[length - 1];
			if (StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				List<Long> ancestors_ = buttonRelationRepository.queryAncestorsByDescendant(descendant_);
				if (length != ancestors_.size()) {
					throw new BizException("Invalid button relations");
				}
				for (int i = 0; i < length; i++) {
					if (!(ancestors_.get(i) + "").equals(button.getAncestor()[i])) {
						throw new BizException("Invalid button relations");
					}
					ButtonRelationEntity relation = new ButtonRelationEntity();
					relation.setAncestor(ancestors_.get(i));
					relation.setDescendant(entity.getId());
					relation.setDepth(length - i);
					relations.add(relation);
				}
			}
		}
		buttonRelationRepository.saveAll(relations);
	}

	@Override
	@Transactional
	public void save(ButtonDto button) {
		ButtonEntity entity = buttonRepository.findById(button.getId());
		if (entity == null) {
			throw new BizException("Invalid button");
		}

		if (!StringUtils.isNullOrEmpty(button.getName())) {
			entity.setName(button.getName());
		}
		if (!StringUtils.isNullOrEmpty(button.getIcon())) {
			entity.setIcon(button.getIcon());
		}
		if (!StringUtils.isNullOrEmpty(button.getDetails())) {
			entity.setDetails(button.getDetails());
		}
		if (!StringUtils.isNullOrEmpty(button.getUrl())) {
			entity.setUrl(button.getUrl());
		}
		if (!StringUtils.isNullOrEmpty(button.getMethod())) {
			entity.setMethod(button.getMethod());
		}
		entity.setStatus(button.getStatus());
		buttonRepository.save(entity);
		if (button.getAncestor() != null && button.getAncestor().length > 0) {
			/** 当前菜单的层级关系 */
			List<Long> ancestorsOld = buttonRelationRepository.queryAncestorsByDescendant(entity.getId());
			int length = button.getAncestor().length;
			String descendant = button.getAncestor()[length - 1];
			if (StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				/** 传入菜单层级查询结果 */
				List<Long> ancestors_ = buttonRelationRepository.queryAncestorsByDescendant(descendant_);

				/** 与现有层级相比较，如果不修改部门层级关系 */
				ancestorsOld.removeAll(ancestors_);
				if (ancestorsOld.size() == 0) {
					/** 不需要修改层级关系 */
				} else {
					List<ButtonRelationEntity> relations = new ArrayList<>();
					for (int i = 0; i < length; i++) {
						if (!(ancestors_.get(i) + "").equals(button.getAncestor()[i])) {
							throw new BizException("Invalid button relations");
						}
						ButtonRelationEntity relation = new ButtonRelationEntity();
						relation.setAncestor(ancestors_.get(i));
						relation.setDescendant(entity.getId());
						relation.setDepth(length - i);
						relations.add(relation);
					}

					/** 删除旧的层级关系 */
					buttonRelationRepository.deleteByDescendant(entity.getId());

					ButtonRelationEntity self = new ButtonRelationEntity();
					self.setAncestor(entity.getId());
					self.setDescendant(entity.getId());
					self.setDepth(0);
					relations.add(self);
					buttonRelationRepository.saveAll(relations);
				}
			}
		}
	}

	@Override
	public ButtonDto findById(long id) {
		ButtonEntity entity = buttonRepository.findById(id);
		if (entity != null) {
			ButtonDto dto = new ButtonDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		buttonRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		buttonRepository.deleteById(id);
	}

	/** 在插入部门信息时，部门主键一定要满足正向增加 */
	private List<ButtonDto> sort(List<ButtonRelationVo> mrs, boolean valid) {
		List<ButtonDto> dtos = new ArrayList<>();

		/** 缓存自身的序号 */
		List<Long> indexes = new LinkedList<>();
		/** 缓存是否为根节点 */
		List<Boolean> rootIndexes = new LinkedList<>();
		/** 时候实锤为字节点 */
		List<Boolean> realDescendants = new LinkedList<>();
		List<ButtonDto> selfs = new ArrayList<>();
		for (ButtonRelationVo mr : mrs) {
			int descendant = indexes.indexOf(mr.getId());
			/** 自身放入缓存 depth = 0 */
			if (descendant == -1) {
				ButtonDto dto = new ButtonDto();
				BeanUtils.copyProperties(mr, dto);
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
						List<ButtonDto> dtos_ = new ArrayList<>();
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
	public List<ButtonDto> queryDescendants(boolean valid) {
		List<ButtonRelationVo> relations = buttonRepository.queryDescendants();
		return sort(relations, valid);
	}

	@Override
	public List<ButtonDto> queryDescendantsByAncestor(long ancestor) {
		List<ButtonRelationVo> relations = buttonRepository.queryDescendantsByAncestor(ancestor);
		return sort(relations, true);
	}

	@Override
	public List<Long> queryAncestorsByDescendant(long descendant) {
		return buttonRelationRepository.queryAncestorsByDescendant(descendant);
	}
}

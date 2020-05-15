package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		
		/** 部门id通过雪花算法设置 */
		long id = Snowflaker.getId();
		
		/** 保存部门信息 */
		CompanyEntity entity = new CompanyEntity();
		BeanUtils.copyProperties(company, entity);
		entity.setId(id);
		companyRepository.save(entity);
		company.setId(entity.getId());
		
		CompanyRelationEntity self = new CompanyRelationEntity();
		self.setAncestor(entity.getId());
		self.setDescendant(entity.getId());
		self.setDepth(0);
		
		/** 保存部门层级关系 */
		List<CompanyRelationEntity> relations = new ArrayList<>();
		if (!StringUtils.isNullOrEmpty(company.getAncestor())) {
			String[] ancestors = company.getAncestor().split(",");
			String descendant = company.getAncestor().substring(company.getAncestor().lastIndexOf(",") + 1);			
			if (ancestors.length > 0 && StringUtils.isStrictlyNumeric(descendant)) {
				long descendant_ = Long.parseLong(descendant);
				
				List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
				int length = ancestors_.size();
				
				if (length != ancestors.length) {
					throw new BizException("Invalid company relations");
				}
				
				for (int i = 0; i < length; i++) {
					
					if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
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
		
		/** 修改部门的基本信息 */
		CompanyEntity entity = companyRepository.findByCode(company.getCode());
		if (entity == null) {
			throw new BizException("Invalid company");
		}
		
		if (!StringUtils.isNullOrEmpty(company.getName())) {
			entity.setName(company.getName());
		}
		if (!StringUtils.isNullOrEmpty(company.getDetails())) {
			entity.setDetails(company.getDetails());
		}
		if (!StringUtils.isNullOrEmpty(company.getRegion()) && !StringUtils.isNullOrEmpty(company.getProvince())
				&& !StringUtils.isNullOrEmpty(company.getCity()) && !StringUtils.isNullOrEmpty(company.getCounty())
				&& !StringUtils.isNullOrEmpty(company.getArea()) && !StringUtils.isNullOrEmpty(company.getAddress())) {
			entity.setRegion(company.getRegion());
			entity.setProvince(company.getProvince());
			entity.setCity(company.getCity());
			entity.setCounty(company.getCounty());
			entity.setArea(company.getArea());
			entity.setAddress(company.getAddress());
		}
		companyRepository.save(entity);
		BeanUtils.copyProperties(entity, company);
		
		/** 修改部门的层级关系 */
		if (!StringUtils.isNullOrEmpty(company.getAncestor())) {
			
			/** 当前部门的层级关系 */
			List<Long> ancestorsOld = companyRelationRepository.queryAncestorByDescendant(entity.getId());
			
			String descendant = company.getAncestor().substring(company.getAncestor().lastIndexOf(",") + 1);
			if (StringUtils.isStrictlyNumeric(descendant)) {
				String[] ancestors = company.getAncestor().split(",");
				long descendant_ = Long.parseLong(descendant);
				/** 传入部门层级查询结果 */
				List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
				
				
				/** 与现有层级相比较，如果不修改部门层级关系 */
				ancestorsOld.removeAll(ancestors_);
				if (ancestorsOld.size() == 1 && ancestorsOld.get(0) == entity.getId()) {
					/** 不需要修改层级关系 */
				} else {
					
					int length = ancestors_.size();
					List<CompanyRelationEntity> relations = new ArrayList<>();
					
					for (int i = 0; i < length; i++) {
						
						if (!(ancestors_.get(i) + "").equals(ancestors[i])) {
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
	public void updateRolesById(List<Integer> roles, long id) {
		List<RoleEntity> entities = roleRepository.queryByIds(roles);
		companyRepository.updateRolesById(entities, id);
	}
	
	/** 在插入部门信息时，部门主键一定要满足正向增加 */	 
	private List<CompanyDto> sort(List<CompanyRelationVo> crs, boolean valid) {
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

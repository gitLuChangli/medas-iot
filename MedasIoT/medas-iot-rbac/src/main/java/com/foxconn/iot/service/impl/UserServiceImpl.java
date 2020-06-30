package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.UserDetailDto;
import com.foxconn.iot.dto.UserDto;
import com.foxconn.iot.dto.UserRolesDto;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.RoleEntity;
import com.foxconn.iot.entity.UserEntity;
import com.foxconn.iot.entity.UserVo;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.CompanyRelationRepository;
import com.foxconn.iot.repository.CompanyRepository;
import com.foxconn.iot.repository.RoleRepository;
import com.foxconn.iot.repository.UserRepository;
import com.foxconn.iot.service.UserService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompanyRelationRepository companyRelationRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public void create(UserDto user) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user, entity);
		
		if (user.getCompanyIds() == null) {
			throw new BizException("Company null");
		}
		
		long descendant = user.getCompanyIds()[user.getCompanyIds().length - 1];
		List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant);
		int length = ancestors_.size();
		if (length == 0) {
			throw new BizException("Invalid company relations");		
		}
		for (int i = 0; i < length; i++) {
			if (!(ancestors_.get(i) + "").equals(user.getCompanyIds()[i])) {
				throw new BizException("Invalid company relations");
			}
		}				
		CompanyEntity company = companyRepository.findById(descendant);
		if (company == null) {
			throw new BizException("Invalid company relations");
		}
		entity.setCompany(company);
		if (user.getRoles() != null) {
			List<Long> ids = new ArrayList<>(user.getRoles().length);
			Collections.addAll(ids, user.getRoles());
			List<RoleEntity> roles = roleRepository.queryByIds(ids);
			entity.setRoles(roles);
		}
		if (StringUtils.isNullOrEmpty(entity.getPwd())) {
			entity.setPwd("password1!");
		}
		entity.setPwd(encoder.encode(entity.getPwd()));
		entity.setId(Snowflaker.getId());
		userRepository.save(entity);
	}

	@Override
	public void save(UserDto user) {
		UserEntity entity = userRepository.findByNo(user.getNo());
		if (entity == null) {
			throw new BizException("Invalid user");
		}
		if (!StringUtils.isNullOrEmpty(user.getName())) {
			entity.setName(user.getName());
		}
		if (!StringUtils.isNullOrEmpty(user.getEmail())) {
			entity.setEmail(user.getEmail());
		}
		entity.setOpenId(user.getOpenId());
		entity.setIcivetId(user.getIcivetId());
		entity.setPhone(user.getPhone());
		entity.setExt(user.getExt());
		entity.setAvatarUrl(user.getAvatarUrl());
		entity.setStatus(user.getStatus());
		
		if (user.getCompanyIds() != null) {
			long descendant = user.getCompanyIds()[user.getCompanyIds().length - 1];
			List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant);
			int length = ancestors_.size();
			if (length == 0) {
				throw new BizException("Invalid company relations");		
			}
			for (int i = 0; i < length; i++) {
				if (!(ancestors_.get(i) + "").equals(user.getCompanyIds()[i])) {
					throw new BizException("Invalid company relations");
				}
			}				
			CompanyEntity company = companyRepository.findById(descendant);
			if (company == null) {
				throw new BizException("Invalid company relations");
			}
			entity.setCompany(company);
		}
		if (user.getRoles() != null) {
			List<Long> ids = new ArrayList<>(user.getRoles().length);
			Collections.addAll(ids, user.getRoles());
			List<RoleEntity> roles = roleRepository.queryByIds(ids);
			entity.setRoles(roles);
		}
		userRepository.save(entity);
	}

	@Override
	public UserDetailDto findById(long id) {
		UserEntity entity = userRepository.findById(id);
		if (entity != null) {
			UserDetailDto dto = new UserDetailDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	public UserDto findByNO(String no) {
		UserEntity entity = userRepository.findByNo(no);
		if (entity != null) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long id) {
		userRepository.updateStatusById(status, id);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
	
	private Page<UserDetailDto> generate(Pageable pageable, Page<UserVo> vos) {
		List<UserDetailDto> dtos = new ArrayList<>();
		for (UserVo vo : vos.getContent()) {
			UserDetailDto dto = new UserDetailDto();
			BeanUtils.copyProperties(vo, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, vos.getTotalElements());
	}
	
	@Override
	public Page<UserDetailDto> query(Pageable pageable) {
		Page<UserVo> vos = userRepository.query(pageable);
		return generate(pageable, vos);
	}
	
	@Override
	public Page<UserDetailDto> query(long companyid,  Pageable pageable) {
		Page<UserVo> vos = userRepository.queryByCompanyId(companyid, pageable);
		return generate(pageable, vos);
	}

	@Override
	@Transactional
	public void updatePwdById(String pwd, long id) {
		userRepository.updatePwdById(encoder.encode(pwd), id);
	}
	
	@Override
	@Transactional
	public void resetPwd(String pwd, long id) {
		userRepository.resetPwd(encoder.encode(pwd), id);
	}
	
	@Override
	public List<Long> queryCompanyRelations(long userid) {
		return userRepository.queryCompanyRelations(userid);
	}

	@Override
	@Transactional
	public void setRoles(UserRolesDto user) {
		UserEntity entity = userRepository.findById(user.getId());
		if (entity == null) {
			throw new BizException("Invalid user");
		}
		if (user.getRoleIds() != null) {
			List<Long> ids = new ArrayList<>(user.getRoleIds().length);
			Collections.addAll(ids, user.getRoleIds());
			List<RoleEntity> roles = roleRepository.queryByIds(ids);
			entity.setRoles(roles);
		} else {
			entity.setRoles(null);
		}
		userRepository.save(entity);
	}
}

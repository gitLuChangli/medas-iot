package com.foxconn.iot.service.impl;

import java.util.ArrayList;
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
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.RoleEntity;
import com.foxconn.iot.entity.UserEntity;
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
		
		String descendant = user.getCompanyIds()[user.getCompanyIds().length - 1];
		if (!StringUtils.isStrictlyNumeric(descendant)) {
			throw new BizException("Invalid company relations");
		}
		long descendant_ = Long.parseLong(descendant);
		List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
		int length = ancestors_.size();
		if (length == 0) {
			throw new BizException("Invalid company relations");		
		}
		for (int i = 0; i < length; i++) {
			if (!(ancestors_.get(i) + "").equals(user.getCompanyIds()[i])) {
				throw new BizException("Invalid company relations");
			}
		}				
		CompanyEntity company = companyRepository.findById(descendant_);
		if (company == null) {
			throw new BizException("Invalid company relations");
		}
		entity.setCompany(company);
		if (!StringUtils.isNullOrEmpty(user.getRoles())) {
			String[] items = user.getRoles().split(",");
			List<Long> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Long.parseLong(item));
				}
			}
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
			String descendant = user.getCompanyIds()[user.getCompanyIds().length - 1];
			if (!StringUtils.isStrictlyNumeric(descendant)) {
				throw new BizException("Invalid company relations");
			}
			long descendant_ = Long.parseLong(descendant);
			List<Long> ancestors_ = companyRelationRepository.queryAncestorByDescendant(descendant_);
			int length = ancestors_.size();
			if (length == 0) {
				throw new BizException("Invalid company relations");		
			}
			for (int i = 0; i < length; i++) {
				if (!(ancestors_.get(i) + "").equals(user.getCompanyIds()[i])) {
					throw new BizException("Invalid company relations");
				}
			}				
			CompanyEntity company = companyRepository.findById(descendant_);
			if (company == null) {
				throw new BizException("Invalid company relations");
			}
			entity.setCompany(company);
		}
		if (!StringUtils.isNullOrEmpty(user.getRoles())) {
			String[] items = user.getRoles().split(",");
			List<Long> ids = new ArrayList<>();
			for (String item : items) {
				if (StringUtils.isStrictlyNumeric(item)) {
					ids.add(Long.parseLong(item));
				}
			}
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
	
	@Override
	public Page<UserDetailDto> findByStatus(int status, Pageable pageable) {
		Page<UserEntity> entities = userRepository.findByStatus(status, pageable);
		if (entities.getTotalElements() > 0) {
			List<UserDetailDto> dtos = new ArrayList<>();
			for (UserEntity entity : entities.getContent()) {
				UserDetailDto dto = new UserDetailDto();
				BeanUtils.copyProperties(entity, dto);
				dtos.add(dto);
			}
			return new PageImpl<>(dtos, pageable, entities.getTotalElements());
		}
		return null;
	}

	@Override
	public Page<UserDetailDto> queryByCompanyAndStatus(long companyid, int status, Pageable pageable) {
		Page<UserEntity> entities = userRepository.queryByCompanyIdAndStatus(companyid, status, pageable);
		if (entities.getTotalElements() > 0) {
			List<UserDetailDto> dtos = new ArrayList<>();
			for (UserEntity entity : entities.getContent()) {
				UserDetailDto dto = new UserDetailDto();
				BeanUtils.copyProperties(entity, dto);
				dtos.add(dto);
			}
			return new PageImpl<>(dtos, pageable, entities.getTotalElements());
		}
		return null;
	}

	@Override
	@Transactional
	public void updatePwdById(String pwd, long id) {
		userRepository.updatePwdById(encoder.encode(pwd), id);
	}
	
	@Override
	public List<Long> queryCompanyRelations(long userid) {
		return userRepository.queryCompanyRelations(userid);
	}
}

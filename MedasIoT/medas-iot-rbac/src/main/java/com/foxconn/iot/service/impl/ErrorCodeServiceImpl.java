package com.foxconn.iot.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ErrorCodeDto;
import com.foxconn.iot.entity.CompanyEntity;
import com.foxconn.iot.entity.ErrorCodeEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.CompanyRepository;
import com.foxconn.iot.repository.ErrorCodeRepository;
import com.foxconn.iot.service.ErrorCodeService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {
	
	@Autowired
	private ErrorCodeRepository errorCodeRepository;
	@Autowired
	private CompanyRepository companyRepositor;

	@Override
	public void adminCreate(ErrorCodeDto error) {
		ErrorCodeEntity entity = new ErrorCodeEntity();
		BeanUtils.copyProperties(error, entity);
		long id = Snowflaker.getId();
		entity.setId(id);
		entity.setLevel(1);
		errorCodeRepository.save(entity);
	}

	@Override
	public void create(ErrorCodeDto error, long companyId) {
		ErrorCodeEntity entity = new ErrorCodeEntity();
		BeanUtils.copyProperties(error, entity);
		CompanyEntity company = companyRepositor.findById(companyId);
		if (company == null) {
			throw new BizException("Invalid company");
		}
		entity.setCompany(company);
		entity.setLevel(0);
		long id = Snowflaker.getId();
		entity.setId(id);
		errorCodeRepository.save(entity);
	}
	
	@Override
	public void adminSave(ErrorCodeDto error) {
		ErrorCodeEntity entity = errorCodeRepository.findById(error.getId());
		if (entity == null) {
			throw new BizException("Invalid error code");
		}
		
		if (!StringUtils.isNullOrEmpty(error.getMsg())) {
			entity.setMsg(error.getMsg());
		}
		errorCodeRepository.save(entity);
	}

	@Override
	public void save(ErrorCodeDto error, long companyId) {
		ErrorCodeEntity entity = errorCodeRepository.queryByIdAndCompany(error.getId(), companyId);
		if (entity == null) {
			throw new BizException("The error code not belong to this company");
		}
		if (!StringUtils.isNullOrEmpty(error.getMsg())) {
			entity.setMsg(error.getMsg());
		}
		errorCodeRepository.save(entity);
	}
	
	@Override
	@Transactional
	public void adminDeleteById(long errorId) {
		errorCodeRepository.deleteById(errorId);
	}

	@Override
	@Transactional
	public void deleteByIdAndCompany(long errorId, long companyId) {
		errorCodeRepository.deleteByIdAndCompany(errorId, companyId);
	}

	@Override
	public Page<ErrorCodeDto> findAll(Pageable pageable) {
		Page<ErrorCodeEntity> entities = errorCodeRepository.findAll(pageable);
		List<ErrorCodeDto> dtos = new ArrayList<>();
		for (ErrorCodeEntity entity : entities.getContent()) {
			ErrorCodeDto dto = new ErrorCodeDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	public Page<ErrorCodeDto> queryByCompany(long companyId, Pageable pageable) {
		Page<ErrorCodeEntity> entities = errorCodeRepository.queryByCompany(companyId, pageable);
		List<ErrorCodeDto> dtos = new ArrayList<>();
		for (ErrorCodeEntity entity : entities.getContent()) {
			ErrorCodeDto dto = new ErrorCodeDto();
			BeanUtils.copyProperties(entity, dto);
			dtos.add(dto);
		}
		return new PageImpl<>(dtos, pageable, entities.getTotalElements());
	}

	@Override
	@Transactional
	public void updateStatusById(int status, long errorId) {
		errorCodeRepository.updateStatusById(status, errorId);
	}

	@Override
	@Transactional
	public void updateStatusByIdAndCompany(int status, long errorId, long companyId) {
		errorCodeRepository.updateStatusByIdAndCompany(status, errorId, companyId);
	}
}

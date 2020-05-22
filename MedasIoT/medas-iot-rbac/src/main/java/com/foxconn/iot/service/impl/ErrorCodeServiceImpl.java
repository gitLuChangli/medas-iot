package com.foxconn.iot.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.iot.dto.ErrorCodeDto;
import com.foxconn.iot.entity.ErrorCodeEntity;
import com.foxconn.iot.exception.BizException;
import com.foxconn.iot.repository.ErrorCodeRepository;
import com.foxconn.iot.service.ErrorCodeService;
import com.foxconn.iot.support.Snowflaker;
import com.mysql.cj.util.StringUtils;

@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {
	
	@Autowired
	private ErrorCodeRepository errorCodeRepository;

	@Override
	public void create(ErrorCodeDto error) {
		ErrorCodeEntity entity = new ErrorCodeEntity();
		BeanUtils.copyProperties(error, entity);
		long id = Snowflaker.getId();
		entity.setId(id);
		errorCodeRepository.save(entity);
	}

	@Override
	public void save(ErrorCodeDto error) {
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
	@Transactional
	public void deleteById(long id) {
		errorCodeRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteByIdAndCompany(long id, long companyId) {
		errorCodeRepository.updateStatusByIdAndCompany(id, companyId);
	}

}

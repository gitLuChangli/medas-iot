package com.foxconn.iot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.foxconn.iot.dto.ErrorCodeDto;

public interface ErrorCodeService {
	
	/**
	 * 创建全局消息，level=1,companyId=0
	 * @param error
	 */
	void adminCreate(ErrorCodeDto error);
	
	/**
	 * 创建部门特有消息
	 * @param error
	 * @param companyId
	 */
	void create(ErrorCodeDto error, long companyId);
	
	void adminSave(ErrorCodeDto error);
	
	void save(ErrorCodeDto error, long companyId);
	
	void adminDeleteById(long errorId);
	
	void deleteByIdAndCompany(long id, long companyId);
	
	Page<ErrorCodeDto> findAll(Pageable pageable);
	
	Page<ErrorCodeDto> queryByCompany(long companyId, Pageable pageable); 
	
	void updateStatusById(int status, long errorId);
	
	void updateStatusByIdAndCompany(int status, long errorId, long companyId);
}

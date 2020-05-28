package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.CompanyDto;

public interface CompanyService {
	
	CompanyDto crate(CompanyDto company);
	
	CompanyDto save(CompanyDto company);
	
	CompanyDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void delelteById(long id);
	
	void updateRolesById(List<Long> roles, long id); 
	
	List<CompanyDto> queryDescendants();
	
	List<CompanyDto> queryDescendantsByAncestor(long ancestor);
}

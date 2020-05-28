package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.ButtonDto;

public interface ButtonService {
	
	void create(ButtonDto button);
	
	void save(ButtonDto menu);
	
	ButtonDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void deleteById(long id);
	
	List<ButtonDto> queryDescendants();
	
	List<ButtonDto> queryDescendantsByAncestor(long ancestor);
}

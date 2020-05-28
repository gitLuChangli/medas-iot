package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.MenuDto;

public interface MenuService {
	
	void create(MenuDto menu);
	
	void save(MenuDto menu);
	
	MenuDto findById(long id);
	
	void updateStatusById(int status, long id);
	
	void deleteById(long id);
	
	List<MenuDto> queryDescendants();
	
	List<MenuDto> queryDescendantsByAncestor(long ancestor);
}

package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.MenuDto;

public interface MenuService {
	
	MenuDto create(MenuDto menu);
	
	MenuDto save(MenuDto menu);
	
	MenuDto findById(int id);
	
	void updateStatusById(int status, int id);
	
	void deleteById(int id);
	
	List<MenuDto> queryDescendants();
	
	List<MenuDto> queryDescendantsByAncestor(int ancestor);
}

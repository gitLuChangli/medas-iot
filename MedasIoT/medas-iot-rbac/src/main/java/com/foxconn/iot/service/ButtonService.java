package com.foxconn.iot.service;

import java.util.List;

import com.foxconn.iot.dto.ButtonDto;

public interface ButtonService {

	void create(ButtonDto button);

	void save(ButtonDto menu);

	ButtonDto findById(long id);

	void updateStatusById(int status, long id);

	void deleteById(long id);

	List<ButtonDto> queryDescendants(boolean valid);

	List<ButtonDto> queryDescendantsByAncestor(long ancestor);

	List<Long> queryAncestorsByDescendant(long descendant);

	List<ButtonDto> queryDescendantsByRoleIds(Long[] roleIds);

	List<ButtonDto> queryDescendantsByUserId(long userid);
}

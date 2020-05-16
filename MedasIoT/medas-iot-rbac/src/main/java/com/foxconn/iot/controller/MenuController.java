package com.foxconn.iot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.MenuDto;
import com.foxconn.iot.service.MenuService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(MenuDto.MenuCreate.class) @RequestBody MenuDto menu, BindingResult result) {
		menuService.create(menu);
	}

	@GetMapping(value = "/{id:\\d+}")
	@CommonResponse
	public MenuDto query(@PathVariable(value = "id") int id) {
		return menuService.findById(id);
	}

	@PutMapping(value = "/")
	@CommonResponse
	public void update(@Valid @JsonView(MenuDto.MenuSave.class) @RequestBody MenuDto menu, BindingResult result) {
		menuService.save(menu);
	}

	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") int id, @PathVariable(value = "status") int status) {
		menuService.updateStatusById(status, id);
	}

	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") int id) {
		menuService.deleteById(id);
	}

	@GetMapping(value = "/descendants")
	@CommonResponse
	public List<MenuDto> queryAllDescendants() {
		return menuService.queryDescendants();
	}

	@GetMapping(value = "/ancestor/{id:\\d+}")
	@CommonResponse
	public List<MenuDto> queryDescendantsByAncestor(@PathVariable(value = "id") int id) {
		return menuService.queryDescendantsByAncestor(id);
	}
}

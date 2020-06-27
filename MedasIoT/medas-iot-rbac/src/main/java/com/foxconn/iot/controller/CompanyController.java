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

import com.foxconn.iot.dto.CompanyDto;
import com.foxconn.iot.service.CompanyService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@CommonResponse
	@PostMapping(value = "/")
	public void create(@Valid @RequestBody CompanyDto company,
			BindingResult result) {
		companyService.crate(company);
	}

	@CommonResponse
	@GetMapping(value = "/{id:\\d+}")
	public CompanyDto query(@PathVariable(value = "id") long id) {
		return companyService.findById(id);
	}

	@CommonResponse
	@PutMapping(value = "/")
	public void update(@Valid @RequestBody CompanyDto company) {
		companyService.save(company);
	}

	@CommonResponse
	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		companyService.updateStatusById(status, id);
	}
	
	@CommonResponse
	@PutMapping(value = "/roles/{id:\\d+}")
	public void updateRoles(@PathVariable(value = "id") long id, @RequestBody List<Long> roles) {
		companyService.updateRolesById(roles, id);
	}

	@CommonResponse
	@DeleteMapping(value = "/{id:\\d+}")
	public void delete(@PathVariable(value = "id") long id) {
		companyService.delelteById(id);
	}
	
	/**
	 *  查询所有的部门
	 * @return
	 */
	@CommonResponse
	@GetMapping(value = "/descendants")
	public List<CompanyDto> queryDescendants() {
		return companyService.queryDescendants();
	}
	
	/**
	 * 查询该节点下属部门
	 * 
	 * @param id
	 * @return
	 */
	@CommonResponse
	@GetMapping(value = "/descendants/{id:\\d+}")
	public List<CompanyDto> queryDescendantsByAncestor(@PathVariable(value = "id") long id) {
		return companyService.queryDescendantsByAncestor(id);
	}
	
	/**
	 * 查看该部门的层级关系（方便前端显示部门层级）
	 * @param id
	 * @return
	 */
	@CommonResponse
	@GetMapping(value = "/ancestors/{id:\\d+}")
	public List<String> queryAncestorIds(@PathVariable(value = "id") long id) {
		return companyService.queryAncestors(id);
	}
}

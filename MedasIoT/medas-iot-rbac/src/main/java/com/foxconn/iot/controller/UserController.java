package com.foxconn.iot.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.foxconn.iot.dto.UserDetailDto;
import com.foxconn.iot.dto.UserDto;
import com.foxconn.iot.service.UserService;
import com.foxconn.iot.support.CommonResponse;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/")
	@CommonResponse
	public void create(@Valid @JsonView(UserDto.UserCreate.class) @RequestBody UserDto user, BindingResult result) {
		userService.create(user);
	}

	@GetMapping(value = "/{id:\\d+}")
	@CommonResponse
	public UserDetailDto query(@PathVariable(value = "id") long id) {
		return userService.findById(id);
	}

	/**
	 * 修改用户
	 * 
	 * 只需要传入工号，姓名、邮箱、部门、角色不传值不修改，其他参数传空直接设为空
	 * @param user
	 * @param result
	 */
	@PutMapping(value = "/")
	@CommonResponse
	public void update(@RequestBody UserDto user, BindingResult result) {
		userService.save(user);
	}

	@PutMapping(value = "/disable/{id:\\d+}/{status:^[01]$}")
	@CommonResponse
	public void disable(@PathVariable(value = "id") long id, @PathVariable(value = "status") int status) {
		userService.updateStatusById(status, id);
	}

	@DeleteMapping(value = "/{id:\\d+}")
	@CommonResponse
	public void delete(@PathVariable(value = "id") long id) {
		userService.deleteById(id);
	}

	@GetMapping(value = "/company/{id:\\d+}")
	@CommonResponse
	public Page<UserDetailDto> queryByCommpany(@PathVariable(value = "id") long companyid,
			@PageableDefault(size = 15) Pageable pageable) {
		return userService.queryByCompany(companyid, pageable);
	}
	
	@PutMapping(value = "/change_pwd")
	@CommonResponse
	public void changePwd(HttpSession session, @RequestParam(value = "pwd", required = true) String newpwd) {
		long id = (long) session.getAttribute("user_id");
		userService.updatePwdById(newpwd, id);
	}
	
	@PutMapping(value = "/change_pwd/{id:\\d}")
	@CommonResponse
	public void adminChangePwd(@PathVariable(value = "id") long id, @RequestParam(value = "pwd") String pwd) {
		userService.updatePwdById(pwd, id);
	}
}

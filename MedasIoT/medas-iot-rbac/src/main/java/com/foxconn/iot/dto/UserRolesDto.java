package com.foxconn.iot.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserRolesDto {
	
	@JsonFormat(shape = Shape.STRING)
	@NotNull(message = "用戶編號不能爲空")
	private long id;
	
	@JsonInclude(value = Include.NON_NULL)
	@JsonSerialize(as = Long[].class)
	private Long[] roleIds;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
}

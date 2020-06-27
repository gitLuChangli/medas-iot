package com.foxconn.iot.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class RoleDto {

	@JsonFormat(shape = Shape.STRING)
	private long id;

	@NotBlank(message = "角色名稱不能為空")
	private String name;

	@NotBlank(message = "角色標題不能爲空")
	private String title;
	
	private String details;

	private int status;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createOn;

	@JsonInclude(value = Include.NON_NULL)
	@JsonDeserialize(as = String[].class)
	private String[] permissionIds;
	
	@JsonInclude(value = Include.NON_NULL)
	private String[] permissions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}
}

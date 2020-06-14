package com.foxconn.iot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class RolePermissionVo {
	
	private String id;
	
	private String name;
	
	private String title;
	
	private String details;
	
	private Date createOn;
	
	private int status;
	
	private String[] permissionNames;
	
	private String[] permissionTitles;
	
	private String[] permissionIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getCreateOn() {
		return createOn;
	}

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String[] getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(String[] permissionNames) {
		this.permissionNames = permissionNames;
	}

	public String[] getPermissionTitles() {
		return permissionTitles;
	}

	public void setPermissionTitles(String[] permissionTitles) {
		this.permissionTitles = permissionTitles;
	}

	public String[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}
}

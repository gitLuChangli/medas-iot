package com.foxconn.iot.sso.entity;

import java.util.List;

public class User {
	
	private long id;
	
	private String no;
	
	private String password;
	
	private List<String> roles;
	
	private int status;
	
	private int modify;
	
	private long companyId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getModify() {
		return modify;
	}

	public void setModify(int modify) {
		this.modify = modify;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}

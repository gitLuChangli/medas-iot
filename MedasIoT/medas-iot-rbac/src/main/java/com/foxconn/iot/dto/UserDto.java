package com.foxconn.iot.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

public class UserDto {
	
	public interface UserSave {}
	
	public interface UserCreate extends UserSave {}
	
	@JsonView(UserSave.class)
	@NotBlank(message = "工號不能為空")
	private String no;
	
	@JsonView(UserSave.class)
	@NotBlank(message = "姓名不能為空")
	private String name;
	
	/**
	 * 如果传入的密码为空时，设置成默认password1!
	 */
	@JsonView(UserCreate.class)
	private String pwd;
	
	@JsonView(UserSave.class)
	private String email;
	
	@JsonView(UserSave.class)
	private String openId;
	
	@JsonView(UserSave.class)
	private String icivetId;
	
	@JsonView(UserSave.class)
	private String phone;
	
	@JsonView(UserSave.class)
	private String ext;
	
	@JsonView(UserSave.class)
	private String avatarUrl;
	
	@JsonView(UserSave.class)
	private int status;
	
	@JsonView(UserSave.class)
	@NotBlank(message = "部門不能為空")
	private String companyId;
	
	@JsonView(UserSave.class)
	private String roles;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIcivetId() {
		return icivetId;
	}

	public void setIcivetId(String icivetId) {
		this.icivetId = icivetId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}

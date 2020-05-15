package com.foxconn.iot.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class UserDto {
	
	public interface UserSimple {}
	
	public interface UserCreate extends UserSimple {}
	
	public interface UserDetail extends UserSimple {}
	
	@JsonView(UserSimple.class)
	private int id;
	
	@JsonView(UserSimple.class)
	@NotBlank(message = "工號不能為空")
	private String no;
	
	@JsonView(UserSimple.class)
	@NotBlank(message = "姓名不能為空")
	private String name;
	
	@JsonView(UserCreate.class)
	@NotBlank(message = "密碼不能為空")
	private String pwd;
	
	@JsonView(UserSimple.class)
	private String email;
	
	@JsonView(UserDetail.class)
	private String openId;
	
	@JsonView(UserDetail.class)
	private String icivetId;
	
	@JsonView(UserDetail.class)
	private String phone;
	
	@JsonView(UserDetail.class)
	private String ext;
	
	@JsonView(UserDetail.class)
	private String avatarUrl;
	
	@JsonView(UserDetail.class)
	private int status;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonView(UserDetail.class)
	private Date createOn;
	
	@JsonInclude(value = Include.NON_NULL)
	@JsonView(UserCreate.class)
	@NotBlank(message = "部門不能為空")
	private String companyId;
	
	@JsonInclude(value = Include.NON_NULL)
	@JsonView(UserCreate.class)
	private String roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
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

package com.foxconn.iot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserDto {
	
	public interface Basic {
	}

	public interface Create extends Basic {
		
	}
	
	public interface Save extends Basic {
		
	}
	
	@JsonView(Save.class)
	@JsonFormat(shape = Shape.STRING)
	@NotNull(message = "用戶編號不能爲空")
	private long id;
	
	@JsonView(Basic.class)
	@NotBlank(message = "工號不能為空")
	private String no;

	@JsonView(Basic.class)
	@NotBlank(message = "姓名不能為空")
	private String name;

	/**
	 * 如果传入的密码为空时，设置成默认password1!
	 */
	@JsonView(Create.class)
	private String pwd;

	@JsonView(Basic.class)
	@NotBlank(message = "邮箱地址不能为空")
	private String email;

	@JsonView(Basic.class)
	private String openId;

	@JsonView(Basic.class)
	private String icivetId;

	@JsonView(Basic.class)
	private String phone;

	@JsonView(Basic.class)
	private String ext;

	@JsonView(Basic.class)
	private String avatarUrl;

	@JsonView(Basic.class)
	private int status;

	@JsonView(Basic.class)
	@NotNull(message = "部門關係不能為空")
	@JsonInclude(value = Include.NON_NULL)
	@JsonDeserialize(as = Long[].class)
	private Long[] companyIds;

	@JsonView(Basic.class)
	@JsonInclude(value = Include.NON_NULL)
	@JsonSerialize(as = Long[].class)
	private Long[] roles;
	
	private int modify;

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

	public Long[] getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(Long[] companyIds) {
		this.companyIds = companyIds;
	}

	public Long[] getRoles() {
		return roles;
	}

	public void setRoles(Long[] roles) {
		this.roles = roles;
	}

	public int getModify() {
		return modify;
	}

	public void setModify(int modify) {
		this.modify = modify;
	}
}

package com.foxconn.iot.sso.dto;

import javax.validation.constraints.NotBlank;

public class ChangePwdDto {
	
	@NotBlank(message = "工号不能为空")
	private String username;
	
	@NotBlank(message = "旧密码不能为空")
	private String password;
	
	@NotBlank(message = "新密码不能为空")
	private String newpwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
}

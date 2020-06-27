package com.foxconn.iot.entity;

import java.util.Date;

public class UserVo {

private long id;
	
	private String no;
	
	private String name;
	
	private String email;
	
	private String openId;
	
	private String icivetId;
	
	private String phone;
	
	private String ext;
	
	private String avatarUrl;
	
	private int status;
	
	private Date createOn;
	
	private long companyId;
	
	private String companyName;
	
	public UserVo() {
		super();
	}

	public UserVo(long id, String no, String name, String email, String openId, String icivetId, String phone,
			String ext, String avatarUrl, int status, Date createOn, long companyId, String companyName) {
		super();
		this.id = id;
		this.no = no;
		this.name = name;
		this.email = email;
		this.openId = openId;
		this.icivetId = icivetId;
		this.phone = phone;
		this.ext = ext;
		this.avatarUrl = avatarUrl;
		this.status = status;
		this.createOn = createOn;
		this.companyId = companyId;
		this.companyName = companyName;
	}

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

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}

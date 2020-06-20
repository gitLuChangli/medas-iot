package com.foxconn.iot.entity;

import java.util.Date;

public class ApplicationVo {
	
	private long id;
	
	private String appId;
	
	private String secret;
	
	private String name;
	
	private String details;
	
	private Date createOn;
	
	private Date lastModify;
	
	private int status;
	
	private Long parentId;
	
	private String parentName;
	
	public ApplicationVo() {
		super();
	}

	public ApplicationVo(long id, String appId, String secret, String name, String details, Date createOn,
			Date lastModify, int status, Long parentId, String parentName) {
		super();
		this.id = id;
		this.appId = appId;
		this.secret = secret;
		this.name = name;
		this.details = details;
		this.createOn = createOn;
		this.lastModify = lastModify;
		this.status = status;
		this.parentId = parentId;
		this.parentName = parentName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

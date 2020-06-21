package com.foxconn.iot.entity;

import java.util.Date;

public class ApplicationVersionVo {
	
	private long id;
	
	private String version;
	
	private String link;
	
	private String details;
	
	private Date createOn;
	
	private int status;
	
	private long applicationId;
	
	public ApplicationVersionVo() {
		super();
	}

	public ApplicationVersionVo(long id, String version, String link, String details, Date createOn, int status,
			long applicationId) {
		super();
		this.id = id;
		this.version = version;
		this.link = link;
		this.details = details;
		this.createOn = createOn;
		this.status = status;
		this.applicationId = applicationId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
}

package com.foxconn.iot.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class UpdateRecordDto {
	
	@JsonFormat(shape = Shape.STRING)
	private long id;
	
	private String sn;
	
	private String appId;
	
	private String applicationName;
	
	@JsonFormat(shape = Shape.STRING)
	private long versionId;
	
	private String version;
	
	private String link;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
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

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
}

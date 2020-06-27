package com.foxconn.iot.entity;

import java.util.Date;

public class UpdateRecordVo {
	
	private long id;
	
	private String sn;
	
	private String appId;
	
	private String applicationName;
	
	private long versionId;
	
	private String version;
	
	private String link;
	
	private Date createOn;
	
	public UpdateRecordVo() {
		super();
	}

	public UpdateRecordVo(long id, String sn, String appId, String applicationName, long versionId, String version, String link,
			Date createOn) {
		super();
		this.id = id;
		this.sn = sn;
		this.appId = appId;
		this.applicationName = applicationName;
		this.versionId = versionId;
		this.version = version;
		this.link = link;
		this.createOn = createOn;
	}

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

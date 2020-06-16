package com.foxconn.iot.entity;

import java.util.Date;

public class DeviceVersionVo {
	
	private long id;
	
	private String version;
	
	private String hardVersion;
	
	private String imageUrl;
	
	private String details;
	
	private Date createOn;
	
	private long deviceTypeId;
	
	public DeviceVersionVo() {
		super();
	}

	public DeviceVersionVo(long id, String version, String hardVersion, String imageUrl, String details, Date createOn,
			long deviceTypeId) {
		super();
		this.id = id;
		this.version = version;
		this.hardVersion = hardVersion;
		this.imageUrl = imageUrl;
		this.details = details;
		this.createOn = createOn;
		this.deviceTypeId = deviceTypeId;
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

	public String getHardVersion() {
		return hardVersion;
	}

	public void setHardVersion(String hardVersion) {
		this.hardVersion = hardVersion;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public long getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
}

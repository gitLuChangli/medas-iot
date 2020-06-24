package com.foxconn.iot.entity;

import java.util.Date;

public class DeviceVo {
	
	private long id;
	
	private String model;
	
	private String name;
	
	private String sn;
	
	private String details;
	
	private String parameter;
	
	private Date createOn;
	
	private int status;
	
	private String firmware;
	
	private String software;
	
	private long companyId;
	
	private String companyName;
	
	private long applicationId;
	
	private String applicationName;
	
	private long versionId;
	
	private String version;
	
	public DeviceVo() {
		super();
	}

	public DeviceVo(long id, String model, String name, String sn, String details, String parameter, Date createOn,
			int status, String firmware, String software, long companyId, String companyName, long applicationId,
			String applicationName, long versionId, String version) {
		super();
		this.id = id;
		this.model = model;
		this.name = name;
		this.sn = sn;
		this.details = details;
		this.parameter = parameter;
		this.createOn = createOn;
		this.status = status;
		this.firmware = firmware;
		this.software = software;
		this.companyId = companyId;
		this.companyName = companyName;
		this.applicationId = applicationId;
		this.applicationName = applicationName;
		this.versionId = versionId;
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
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

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
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
}

package com.foxconn.iot.dto;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DeviceDto {

	@JsonFormat(shape = Shape.STRING)
	private Long id;
	
	private String model;
	
	private String name;
	
	private String sn;
	
	private String details;
	
	private String parameter;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createOn;
	
	private int status;
	
	private String firmware;
	
	private String software;
	
	@JsonFormat(shape = Shape.STRING)
	private Long companyId;
	
	private String companyName;
	
	@JsonFormat(shape = Shape.STRING)
	private Long applicationId;
	
	private String applicationName;
	
	@JsonFormat(shape = Shape.STRING)
	private Long versionId;
	
	private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setData(Object[] objs) {
		if (objs != null && objs.length == 16) {
			if (objs[0] != null) {
				this.id = new BigInteger(objs[0].toString()).longValue();
			}
			this.model = (String) objs[1];
			this.name = (String) objs[2];
			this.sn = (String) objs[3];
			this.details = (String) objs[4];
			this.parameter = (String) objs[5];
			this.createOn = (Date) objs[6];
			this.status = (Integer) objs[7];
			this.firmware = (String) objs[8];
			this.software = (String) objs[9];
			if (objs[10] != null) {
				this.companyId = new BigInteger(objs[10].toString()).longValue();
			}
			this.companyName = (String) objs[11];
			if (objs[12] != null) {
				this.versionId = new BigInteger(objs[12].toString()).longValue();
			}
			this.version = (String) objs[13];
			if (objs[14] != null) {
				this.applicationId = new BigInteger(objs[14].toString()).longValue();
			}
			this.applicationName = (String) objs[15];
		}
	}
}

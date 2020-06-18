package com.foxconn.iot.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class DeviceCompanyDto {
	
	@JsonFormat(shape = Shape.STRING)
	@NotNull(message = "設備編號不能為空")
	private long deviceId;
	
	@NotEmpty(message = "部門關係不能為空")
	@JsonFormat(shape = Shape.ARRAY)
	@JsonDeserialize(as = long[].class)
	private long[] companyIds;

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public long[] getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(long[] companyIds) {
		this.companyIds = companyIds;
	}
}

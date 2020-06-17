package com.foxconn.iot.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class DeviceAddDto {
	
	@NotNull(message = "設備版本號不能爲空")
	private long versionId;
	
	@NotNull(message = "設備序列號不能爲空")
	@JsonDeserialize(as = String[].class)
	private String[] sns;

	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public String[] getSns() {
		return sns;
	}

	public void setSns(String[] sns) {
		this.sns = sns;
	}
}

package com.foxconn.iot.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class UpdateTaskDto {

	@JsonFormat(shape = Shape.STRING)
	@NotNull(message = "應用版本編號不能爲空")
	private long versionId;
	
	@NotNull(message = "設備序列號不能爲空")
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

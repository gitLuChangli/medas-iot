package com.foxconn.iot.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DeviceTypeDto {
	
	@JsonFormat(shape = Shape.STRING)
	private long id;
	
	@NotBlank(message = "型号不能为空")
	private String model;
	
	@NotBlank(message = "名称不能为空")
	private String name;
	
	private String details;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createOn;

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
}

package com.foxconn.iot.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ApplicationDto {

	public interface Basic {
	}

	public interface Create extends Basic {
	}

	public interface Save extends Basic {
	}

	public interface Detail extends Basic {
	}

	@JsonView(Save.class)
	@NotNull(message = "應用編號不能為空")
	@JsonFormat(shape = Shape.STRING)
	private long id;

	@NotBlank(message = "appId應用編號不能為空")
	@JsonView(Basic.class)
	private String appId;

	@NotBlank(message = "api密鑰不能為空")
	@JsonView(Basic.class)
	private String secret;

	@NotBlank(message = "應用名稱不能為空")
	@JsonView(Basic.class)
	private String name;

	@JsonView(Basic.class)
	private String details;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@JsonView(Basic.class)
	private Date createOn;

	@JsonView(Basic.class)
	private int status;

	@JsonInclude(value = Include.NON_NULL)
	@JsonView(Basic.class)
	@JsonFormat(shape = Shape.STRING)
	@JsonDeserialize(as = Long.class)
	private Long parentId;

	@JsonInclude(value = Include.NON_NULL)
	@JsonView(Detail.class)
	private String parentName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

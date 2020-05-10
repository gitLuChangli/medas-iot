package com.foxconn.iot.core.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonView;

public class WxServiceAccountDto {

	public interface WxServiceAccountSimple {
	}

	public interface WxServiceAccountDetail extends WxServiceAccountSimple {
	}

	@JsonFormat(shape = Shape.STRING)
	@JsonView(WxServiceAccountDetail.class)
	private long id;

	@NotBlank(message = "微信公众平台服务号ID不能为空")
	@JsonView(WxServiceAccountSimple.class)
	private String appId;

	@NotBlank(message = "微信公众平台服务号访问密钥不能为空")
	@JsonView(WxServiceAccountSimple.class)
	private String secret;

	@NotBlank(message = "微信公众平台服务号名称不能为空")
	@JsonView(WxServiceAccountSimple.class)
	private String name;

	@JsonView(WxServiceAccountDetail.class)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonView(WxServiceAccountDetail.class)
	private Date lastModify;
	
	@JsonView(WxServiceAccountDetail.class)
	private int status;

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

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

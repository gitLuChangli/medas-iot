package com.foxconn.iot.core.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonView;

public class WxServiceTemplateDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public interface WxServiceTemplateSimple {
	
	}

	public interface WxServiceTemplateCreate extends WxServiceTemplateSimple {
		
	}
	
	public interface WxServiceTemplateDetail extends WxServiceTemplateSimple {
	}

	@JsonView(WxServiceTemplateDetail.class)
	@JsonFormat(shape = Shape.STRING)
	private long id;

	@JsonView(WxServiceTemplateSimple.class)
	@NotBlank(message = "微信公众平台服务号模板ID不能为空")
	private String templateId;

	@JsonView(WxServiceTemplateSimple.class)
	@NotBlank(message = "微信公众平台服务号名称不能为空")
	private String name;

	@JsonView(WxServiceTemplateSimple.class)
	private String format;

	@JsonView(WxServiceTemplateDetail.class)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;

	@JsonView(WxServiceTemplateDetail.class)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastModify;

	@JsonView(WxServiceTemplateDetail.class)
	private int status;
	
	@NotBlank(message = "微信公众平台服务号ID不能为空")
	private String accountId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
	
	@JsonView(WxServiceTemplateCreate.class)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}

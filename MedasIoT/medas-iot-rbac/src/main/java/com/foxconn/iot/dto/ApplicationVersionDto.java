package com.foxconn.iot.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonView;

public class ApplicationVersionDto {
	
	public interface Basic {}
	
	public interface Create extends Basic {}
	
	public interface Save extends Basic {}
	
	@JsonView(Save.class)
	@JsonFormat(shape = Shape.STRING)
	private long id;
	
	@JsonView(Basic.class)
	@NotBlank(message = "版本號不能為空")
	private String version;
	
	@JsonView(Basic.class)
	@NotBlank(message = "應用下載地址不能為空")
	private String link;
	
	@JsonView(Basic.class)
	private String details;
	
	@JsonView(Basic.class)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;
	
	@JsonView(Basic.class)
	private int status;
	
	@JsonView(Create.class)
	@JsonFormat(shape = Shape.STRING)
	@NotNull(message = "應用編號不能為空")
	private long applicationId;

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
}

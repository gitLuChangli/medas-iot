package com.foxconn.iot.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonView;

public class ClientVersionDto {
	
	public interface ClientVersionSimple {}
	
	public interface ClientVersionCreate extends ClientVersionSimple {}
	
	public interface ClientVersionDetail extends ClientVersionSimple {}
	
	@JsonView(ClientVersionDetail.class)
	@JsonFormat(shape = Shape.STRING)
	private long id;
	
	@JsonView(ClientVersionSimple.class)
	private String version;
	
	@JsonView(ClientVersionSimple.class)
	private String link;
	
	@JsonView(ClientVersionSimple.class)
	private String description;

	@JsonView(ClientVersionDetail.class)
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;
	
	@JsonView(ClientVersionDetail.class)
	private int publish;
	
	@JsonView(ClientVersionDetail.class)
	private int status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonView(ClientVersionCreate.class)
	private String appId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public int getPublish() {
		return publish;
	}

	public void setPublish(int publish) {
		this.publish = publish;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}

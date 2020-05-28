package com.foxconn.iot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="tb_button")
@EntityListeners(AuditingEntityListener.class)
public class ButtonEntity {
	
	@Id
	private long id;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@Column(name = "details", length = 255)
	private String details;
	
	@Column(name = "icon", length = 45)
	private String icon;
	
	@Column(name = "url", length = 255)
	private String url;
	
	@Column(name = "create_on", updatable = false)
	private Date createOn;
	
	@Column(name="status")
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
}
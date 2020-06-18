package com.foxconn.iot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_property_item")
@EntityListeners(AuditingEntityListener.class)
public class PropertyEntity {
	
	@Id
	private long id;
	
	@Column(name = "name", length = 45)
	private String name;
	
	@Column(name="title", length = 45)
	private String title;
	
	@Column(name="details", length = 255)
	private String details;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	private ApplicationEntity application;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}
}

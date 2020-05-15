package com.foxconn.iot.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_dev_type", uniqueConstraints = {
		@UniqueConstraint(name = "uq_dev_type_model", columnNames = "model") })
@EntityListeners(AuditingEntityListener.class)
public class DeviceTypeEntity {

	@Id
	private long id;

	@Column(name = "model", length = 45, nullable = false)
	private String model;

	@Column(name = "name", length = 90, nullable = false)
	private String name;

	@Column(name = "details", length = 255)
	private String details;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on", updatable = false)
	private Date createOn;

	@Column(name = "status")
	private int status;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "dev_type_id")
	private List<DeviceVersionEntity> versions;
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<DeviceVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(List<DeviceVersionEntity> versions) {
		this.versions = versions;
	}
	
}

package com.foxconn.iot.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "tb_dev_version")
public class DeviceVersionEntity {

	@Id
	private long id;

	@Column(name = "ver", length = 45, nullable = false)
	private String version;

	@Column(name = "hard_ver", length = 45)
	private String hardVersion;

	@Column(name = "img_url", length = 255)
	private String imageUrl;

	@Column(name = "details", length = 255)
	private String details;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on", updatable = false)
	private Date createOn;

	@ManyToOne(targetEntity = DeviceTypeEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "dev_type_id")
	private DeviceTypeEntity deviceType;

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

	public String getHardVersion() {
		return hardVersion;
	}

	public void setHardVersion(String hardVersion) {
		this.hardVersion = hardVersion;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public DeviceTypeEntity getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceTypeEntity deviceType) {
		this.deviceType = deviceType;
	}

}

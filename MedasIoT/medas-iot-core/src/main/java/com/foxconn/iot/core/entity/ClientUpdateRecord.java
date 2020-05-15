package com.foxconn.iot.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_client_update_record")
@EntityListeners(AuditingEntityListener.class)
public class ClientUpdateRecord {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="device_no", length = 32, nullable = false)
	private String deviceNO;
	
	@Column(name = "client_appid", length = 20, nullable = false)
	private String clientAppId;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="client_ver", nullable = false)
	private String clientVersion;
	
	@Column(name="client_link", nullable = false)
	private String clientLink;
	
	@Column(name="create_on")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClientAppId() {
		return clientAppId;
	}

	public void setClientAppId(String clientAppId) {
		this.clientAppId = clientAppId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientLink() {
		return clientLink;
	}

	public void setClientLink(String clientLink) {
		this.clientLink = clientLink;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
}
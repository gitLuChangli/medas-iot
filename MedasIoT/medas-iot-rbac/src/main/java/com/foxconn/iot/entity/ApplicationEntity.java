package com.foxconn.iot.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_app", uniqueConstraints = { @UniqueConstraint(name = "uq_app_id", columnNames = "app_id"),
		@UniqueConstraint(name = "uq_app_name", columnNames = "name") })
@EntityListeners(AuditingEntityListener.class)
public class ApplicationEntity {

	@Id
	private long id;

	@Column(name = "app_id", length = 45, nullable = false)
	private String appId;

	@Column(name = "secret", length = 90, nullable = false)
	private String secret;

	@Column(name = "name", length = 90, nullable = false)
	private String name;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on", updatable = false)
	private Date createOn;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "last_modify")
	private Date lastModify;

	@Column(name = "status")
	private int status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "app_id")
	private Set<ApplicationVersionEntity> versions;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "app_id")
	@OrderBy("createOn DESC")
	private Set<PropertyEntity> parameters;
	
	@JoinColumn(name = "p_id")
	@ManyToOne(cascade = CascadeType.ALL)
	private ApplicationEntity mainApplication;
	
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

	public Set<ApplicationVersionEntity> getVersions() {
		return versions;
	}

	public void setVersions(Set<ApplicationVersionEntity> versions) {
		this.versions = versions;
	}

	public Set<PropertyEntity> getParameters() {
		return parameters;
	}

	public void setParameters(Set<PropertyEntity> parameters) {
		this.parameters = parameters;
	}

	public ApplicationEntity getMainApplication() {
		return mainApplication;
	}

	public void setMainApplication(ApplicationEntity mainApplication) {
		this.mainApplication = mainApplication;
	}
}

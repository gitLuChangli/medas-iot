package com.foxconn.iot.core.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_wx_service_account")
public class WxServiceAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "微信公众平台服务号ID不能为空")
	@Column(name = "appid", unique = true, nullable = false)
	private String appId;

	@NotBlank(message = "微信公众平台服务号访问密钥不能为空")
	@Column(name = "secret", nullable = false)
	private String secret;

	@NotBlank(message = "微信公众平台服务号名称不能为空")
	@Column(name = "name", nullable = false)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@Column(name = "create_on", updatable = false, length = 20)
	private Date createOn;

	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@Column(name = "last_modify", length = 20)
	private Date lastModify;

	@Column(name = "status")
	private int status;

	@OneToMany(targetEntity = WxServiceTemplate.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "wx_service_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<WxServiceTemplate> templates;

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

	public Set<WxServiceTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(Set<WxServiceTemplate> templates) {
		this.templates = templates;
	}
	
}

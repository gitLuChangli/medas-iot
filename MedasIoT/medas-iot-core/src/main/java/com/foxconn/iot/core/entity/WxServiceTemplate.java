package com.foxconn.iot.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_wx_service_template")
@EntityListeners(AuditingEntityListener.class)
public class WxServiceTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "微信公众平台服务号消息模板ID不能为空")
	@Column(name = "template_id", unique = true, nullable = false)
	private String templateId;
	
	
	@NotBlank(message="微信公众平台服务号消息模板名称")
	@Column(name="name", nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wx_service_id")
	private WxServiceAccount account;
	
	@Column(name = "format")
	private String format;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_on", updatable = false, length = 20)
	@CreatedDate
	private Date createOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_modify", length = 20)
	@UpdateTimestamp
	private Date lastModify;
	
	@Column(name="status")
	private int status;

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

	public WxServiceAccount getAccount() {
		return account;
	}

	public void setAccount(WxServiceAccount account) {
		this.account = account;
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
	
}

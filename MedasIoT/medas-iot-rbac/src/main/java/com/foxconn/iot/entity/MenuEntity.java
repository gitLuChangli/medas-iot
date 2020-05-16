package com.foxconn.iot.entity;

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
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_menu", uniqueConstraints = { @UniqueConstraint(name = "uq_menu_name", columnNames = "name") })
@EntityListeners(AuditingEntityListener.class)
public class MenuEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 菜单名称
	 */
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	/**
	 * 详情
	 */
	@Column(name = "details", length = 255)
	private String details;

	/**
	 * 图标
	 */
	@Column(name = "icon", length = 100)
	private String icon;

	/**
	 * 地址
	 */
	@Column(name = "url", length = 255)
	private String url;

	@CreatedDate
	@Column(name = "create_on", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createOn;

	@Column(name = "status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

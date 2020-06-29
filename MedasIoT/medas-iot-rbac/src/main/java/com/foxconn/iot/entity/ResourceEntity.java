package com.foxconn.iot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_res", uniqueConstraints = { @UniqueConstraint(name = "uq_res_name", columnNames = "name") })
@EntityListeners(AuditingEntityListener.class)
public class ResourceEntity {

	@Id
	private long id;

	/**
	 * 资源名称
	 */
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	/**
	 * 资源标题
	 */
	@Column(name = "title", nullable = false, length = 45)
	private String title;
	
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

	/**
	 * 访问方式
	 */
	@Column(name="method", length = 45)
	private String method;
	
	/**
	 * 顺序
	 */
	@Column(name = "idx")
	private int index;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	private int status;
	
	/**
	 * 资源类型，1：按钮，0：菜单
	 */
	@Column(name = "type")
	private int type;

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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}

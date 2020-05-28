package com.foxconn.iot.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_permission", uniqueConstraints = {
		@UniqueConstraint(name = "uq_permission_name", columnNames = {"name"}) })
@EntityListeners(AuditingEntityListener.class)
public class PermissionEntity {

	@Id
	private long id;

	/**
	 * 权限名称
	 */
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	/**
	 * 权限详情
	 */
	@Column(name = "details", length = 255)
	private String details;

	@Column(name = "status")
	private int status;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on", updatable = false)
	private Date createOn;

	@ManyToMany(targetEntity = MenuEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "tb_permission_menu", joinColumns = {
			@JoinColumn(name = "permission_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "menu_id", referencedColumnName = "id") })
	private List<MenuEntity> menus;
	
	@ManyToMany(targetEntity = ButtonEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "tb_permission_button", joinColumns = {
			@JoinColumn(name="permission_id", referencedColumnName = "id")}, inverseJoinColumns = {
					@JoinColumn(name="button_id", referencedColumnName = "id")})
	private List<ButtonEntity> buttons;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public List<MenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuEntity> menus) {
		this.menus = menus;
	}

	public List<ButtonEntity> getButtons() {
		return buttons;
	}

	public void setButtons(List<ButtonEntity> buttons) {
		this.buttons = buttons;
	}

}

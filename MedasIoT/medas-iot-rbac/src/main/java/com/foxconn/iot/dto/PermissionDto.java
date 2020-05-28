package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class PermissionDto {
	
	public interface PermissionCreate {}
	
	public interface PermissionSave extends PermissionCreate {}
	
	@JsonView(PermissionSave.class)
	private long id;
	
	@JsonView(PermissionCreate.class)
	@NotBlank(message = "權限名稱不能為空")
	private String name;
	
	@JsonView(PermissionCreate.class)
	private String details;
	
	@JsonView(PermissionSave.class)
	private int status;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;
	
	@JsonView(PermissionCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	private String menus;
	
	@JsonInclude(value = Include.NON_NULL)
	private List<MenuDto> menuList;
	
	@JsonView(PermissionCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	private String buttons;
	
	@JsonInclude(value = Include.NON_NULL)
	private List<ButtonDto> buttonList;

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

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public List<MenuDto> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuDto> menuList) {
		this.menuList = menuList;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public List<ButtonDto> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<ButtonDto> buttonList) {
		this.buttonList = buttonList;
	}
}

package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class MenuDto {

	public interface MenuBasic {}
	
	public interface MenuCreate extends MenuBasic {}
	
	public interface MenuSave extends MenuCreate {}
	
	@JsonView(MenuSave.class)
	private int id;

	@NotBlank(message = "菜單名稱不能為空")
	@JsonView(MenuBasic.class)
	private String name;

	@JsonView(MenuBasic.class)
	private String details;

	@JsonView(MenuBasic.class)
	private String icon;

	@JsonView(MenuBasic.class)
	private String url;

	@JsonView(MenuBasic.class)
	private Date createOn;

	@JsonView(MenuBasic.class)
	private int status;

	@JsonView(MenuCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	private String ancestor;

	@JsonInclude(value = Include.NON_NULL)
	private List<MenuDto> descendants;

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

	public String getAncestor() {
		return ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	public List<MenuDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<MenuDto> descendants) {
		this.descendants = descendants;
	}

}

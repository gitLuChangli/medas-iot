package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class MenuDto {

	public interface MenuBasic {}
	
	public interface MenuCreate extends MenuBasic {}
	
	public interface MenuSave extends MenuCreate {}
	
	@JsonView(MenuSave.class)
	@JsonFormat(shape = Shape.STRING)
	private long id;

	@NotBlank(message = "菜單名稱不能為空")
	@JsonView(MenuBasic.class)
	private String name;

	@NotBlank(message = "菜單標題不能為空")
	@JsonView(MenuBasic.class)
	private String title;
	
	@JsonView(MenuBasic.class)
	private String details;

	@JsonView(MenuBasic.class)
	private String icon;

	@JsonView(MenuBasic.class)
	private String url;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@JsonView(MenuBasic.class)
	private Date createOn;

	@JsonView(MenuBasic.class)
	private int status;
	
	@JsonView(MenuBasic.class)
	private int index;

	@JsonView(MenuCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	@JsonDeserialize(as = String[].class)
	private String[] ancestorIds;

	@JsonInclude(value = Include.NON_NULL)
	private List<MenuDto> descendants;

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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String[] getAncestorIds() {
		return ancestorIds;
	}

	public void setAncestorIds(String[] ancestorIds) {
		this.ancestorIds = ancestorIds;
	}

	public List<MenuDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<MenuDto> descendants) {
		this.descendants = descendants;
	}
}

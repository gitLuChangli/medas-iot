package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class ButtonDto {

	public interface ButtonBasic {
	}

	public interface ButtonCreate extends ButtonBasic {
	}

	public interface ButtonSave extends ButtonCreate {
	}

	@JsonFormat(shape = Shape.STRING)
	@JsonView(ButtonSave.class)
	private long id;

	@NotBlank(message = "菜單名稱不能為空")
	@JsonView(ButtonBasic.class)
	private String name;

	@NotBlank(message = "標題不能為空")
	@JsonView(ButtonSave.class)
	private String title;

	@JsonView(ButtonBasic.class)
	private String details;

	@JsonView(ButtonBasic.class)
	private String icon;

	@JsonView(ButtonBasic.class)
	private String url;

	@JsonView(ButtonBasic.class)
	private String method;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonView(ButtonBasic.class)
	private Date createOn;

	@JsonView(ButtonBasic.class)
	private int status;

	@JsonView(ButtonBasic.class)
	@JsonInclude(value = Include.NON_NULL)
	private String[] ancestorIds;

	@JsonInclude(value = Include.NON_NULL)
	private List<ButtonDto> descendants;

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

	public String[] getAncestorIds() {
		return ancestorIds;
	}

	public void setAncestorIds(String[] ancestorIds) {
		this.ancestorIds = ancestorIds;
	}

	public List<ButtonDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<ButtonDto> descendants) {
		this.descendants = descendants;
	}

}

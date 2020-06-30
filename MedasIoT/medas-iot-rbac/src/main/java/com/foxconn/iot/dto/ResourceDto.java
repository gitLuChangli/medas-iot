package com.foxconn.iot.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonView;

public class ResourceDto{

	public interface Basic {
	}

	public interface Create extends Basic {
	}

	public interface Save extends Create {
	}

	@JsonFormat(shape = Shape.STRING)
	@JsonView(Save.class)
	private long id;

	@NotBlank(message = "名稱不能為空")
	@JsonView(Basic.class)
	private String name;

	@NotBlank(message = "標題不能為空")
	@JsonView(Basic.class)
	private String title;

	@JsonView(Basic.class)
	private String details;

	@JsonView(Basic.class)
	private String icon;

	@JsonView(Basic.class)
	private String url;

	@JsonView(Basic.class)
	private String method;

	@JsonView(Basic.class)
	private int index;

	@JsonView(Basic.class)
	private int status;

	@JsonView(Basic.class)
	private int type;

	@JsonView(Basic.class)
	@JsonInclude(value = Include.NON_NULL)
	@JsonDeserialize(as = String[].class)
	private String[] ancestorIds;

	@JsonInclude(value = Include.NON_NULL)
	private List<ResourceDto> descendants;

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

	public String[] getAncestorIds() {
		return ancestorIds;
	}

	public void setAncestorIds(String[] ancestorIds) {
		this.ancestorIds = ancestorIds;
	}

	public List<ResourceDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<ResourceDto> descendants) {
		this.descendants = descendants;
	}
}

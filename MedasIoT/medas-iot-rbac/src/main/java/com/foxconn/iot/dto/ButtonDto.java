package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class ButtonDto {

	public interface ButtonBasic {}
	
	public interface ButtonCreate extends ButtonBasic {}
	
	public interface ButtonSave extends ButtonCreate {}
	
	@JsonView(ButtonSave.class)
	private long id;

	@NotBlank(message = "菜單名稱不能為空")
	@JsonView(ButtonBasic.class)
	private String name;

	@JsonView(ButtonBasic.class)
	private String details;

	@JsonView(ButtonBasic.class)
	private String icon;

	@JsonView(ButtonBasic.class)
	private String url;

	@JsonView(ButtonBasic.class)
	private Date createOn;

	@JsonView(ButtonBasic.class)
	private int status;

	@JsonView(ButtonCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	private String ancestor;

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

	public List<ButtonDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<ButtonDto> descendants) {
		this.descendants = descendants;
	}

}

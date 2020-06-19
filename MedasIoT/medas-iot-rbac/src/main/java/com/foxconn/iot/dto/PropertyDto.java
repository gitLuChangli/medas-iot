package com.foxconn.iot.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class PropertyDto {
	
	public interface Basic {}
	
	public interface Create extends Basic {}
	
	//@JsonView(Basic.class)
	@JsonFormat(shape = Shape.STRING)
	private long id;
	
	//@JsonView(Basic.class)
	@NotBlank(message = "屬性name不能為空")
	private String name;
	
	//@JsonView(Basic.class)
	@NotBlank(message = "屬性標題不能為空")
	private String title;
	
	//@JsonView(Basic.class)
	private String details;
	
	//@JsonView(Create.class)
	@JsonInclude(value = Include.NON_NULL)
	private long applicationId;

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

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
}

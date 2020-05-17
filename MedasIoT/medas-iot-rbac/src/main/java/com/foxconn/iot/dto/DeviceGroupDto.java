package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DeviceGroupDto {

	private long id;
	
	@NotBlank(message = "设备分组名称不能为空")
	private String name;
	
	@JsonInclude(value = Include.NON_NULL)
	@NotBlank(message = "部门不能为空")
	private String companyId;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createOn;
	
	private int status;
	
	@JsonInclude(value = Include.NON_NULL)
	private String ancestor;
	
	@JsonInclude(value =Include.NON_NULL)
	private List<DeviceGroupDto> descendants;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public List<DeviceGroupDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<DeviceGroupDto> descendants) {
		this.descendants = descendants;
	}
	
}

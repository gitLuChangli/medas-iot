package com.foxconn.iot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class CompanyDto {

	public interface CompanyCreate {}
	
	@JsonFormat(shape = Shape.STRING)
	private long id;

	@JsonView(CompanyCreate.class)
	@NotBlank(message = "費用代碼不能為空")
	private String code;

	@JsonView(CompanyCreate.class)
	@NotBlank(message = "部門名稱不能為空")
	private String name;

	private String details;

	private String region;

	private String province;

	private String city;

	private String county;

	private String address;

	private String area;

	private int status;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
	private Date createOn;

	/**
	 * 所属部门，多层级用逗号隔开
	 */
	@JsonView(CompanyCreate.class)
	@JsonInclude(value = Include.NON_NULL)
	private String ancestor;
	
	/**
	 * 下属部门
	 */
	@JsonInclude(value = Include.NON_NULL)
	private List<CompanyDto> descendants;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getAncestor() {
		return ancestor;
	}

	public void setAncestor(String ancestor) {
		this.ancestor = ancestor;
	}

	public List<CompanyDto> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<CompanyDto> descendants) {
		this.descendants = descendants;
	}
	
}

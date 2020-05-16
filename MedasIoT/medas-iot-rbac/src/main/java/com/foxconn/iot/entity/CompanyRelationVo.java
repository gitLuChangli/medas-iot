package com.foxconn.iot.entity;

import java.util.Date;

public class CompanyRelationVo {
	
	private long id;
	
	private String code;
	
	private String name;
	
	private String details;
	
	private String region;
	
	private String province;
	
	private String city;
	
	private String county;
	
	private String address;
	
	private String area;
	
	private int status;
	
	private Date createOn;
	
	private long ancestor;
	
	private int depth;
	
	private int root;

	
	public CompanyRelationVo() {
		super();
	}

	public CompanyRelationVo(long id, String code, String name, String details, String region, String province,
			String city, String county, String address, String area, int status, Date createOn, long ancestor,
			int depth, int root) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.details = details;
		this.region = region;
		this.province = province;
		this.city = city;
		this.county = county;
		this.address = address;
		this.area = area;
		this.status = status;
		this.createOn = createOn;
		this.ancestor = ancestor;
		this.depth = depth;
		this.root = root;
	}

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

	public long getAncestor() {
		return ancestor;
	}

	public void setAncestor(long ancestor) {
		this.ancestor = ancestor;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}
	
}

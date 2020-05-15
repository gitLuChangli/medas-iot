package com.foxconn.iot.entity;

import java.util.Date;

public class MenuRelationVo {
	
	private int id;
	
	private String name;
	
	private String details;
	
	private String icon;
	
	private String url;
	
	private Date createOn;
	
	private int status;
	
	private int ancestor;
	
	private int depth;
	
	
	public MenuRelationVo() {
		super();
	}

	public MenuRelationVo(int id, String name, String details, String icon, String url, Date createOn, int status,
			int ancestor, int depth) {
		super();
		this.id = id;
		this.name = name;
		this.details = details;
		this.icon = icon;
		this.url = url;
		this.createOn = createOn;
		this.status = status;
		this.ancestor = ancestor;
		this.depth = depth;
	}

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

	public int getAncestor() {
		return ancestor;
	}

	public void setAncestor(int ancestor) {
		this.ancestor = ancestor;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
package com.foxconn.iot.entity;

import java.util.Date;

public class DeviceGroupRelationVo {

	private long id;
	
	private String name;
	
	private Date createOn;
	
	private int status;
	
	private long ancestor;
	
	private int depth;
	
	public DeviceGroupRelationVo() {
		super();
	}

	public DeviceGroupRelationVo(long id, String name, Date createOn, int status, long ancestor, int depth) {
		super();
		this.id = id;
		this.name = name;
		this.createOn = createOn;
		this.status = status;
		this.ancestor = ancestor;
		this.depth = depth;
	}

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
	
}

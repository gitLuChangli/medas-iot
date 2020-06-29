package com.foxconn.iot.entity;

public class ResourceRelationVo {
	
	private long id;
	
	private String name;
	
	private String title;
	
	private String details;
	
	private String icon;
	
	private String url;
	
	private String method;
	
	private int index;
	
	private int status;
	
	private int type;
	
	private long ancestor;
	
	private int depth;
	
	public ResourceRelationVo() {
		super();
	}
	
	public ResourceRelationVo(long id, String name, String title, String details, String icon, String url,
			String method, int index, int status, int type, long ancestor, int depth) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.details = details;
		this.icon = icon;
		this.url = url;
		this.method = method;
		this.index = index;
		this.status = status;
		this.type = type;
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
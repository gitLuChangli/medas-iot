package com.foxconn.iot.entity;

public class PermissionMenuRelationVo {
	
	private long permissionId;
	
	private long ancestor;
	
	private long descendent;
	
	private int depth;

	public PermissionMenuRelationVo(long permissionId, long ancestor, long descendent, int depth) {
		super();
		this.permissionId = permissionId;
		this.ancestor = ancestor;
		this.descendent = descendent;
		this.depth = depth;
	}

	public PermissionMenuRelationVo() {
		super();
	}

	public long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}

	public long getAncestor() {
		return ancestor;
	}

	public void setAncestor(long ancestor) {
		this.ancestor = ancestor;
	}

	public long getDescendent() {
		return descendent;
	}

	public void setDescendent(long descendent) {
		this.descendent = descendent;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}

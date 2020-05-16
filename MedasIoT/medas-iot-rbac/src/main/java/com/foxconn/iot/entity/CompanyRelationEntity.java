package com.foxconn.iot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_company_relation", indexes = {
		@Index(name = "idx_company_relation_ancestor", columnList = "ancestor"),
		@Index(name = "idx_company_relation_descendant", columnList = "descendant") }, uniqueConstraints = {
				@UniqueConstraint(name = "uq_company_relation", columnNames = { "ancestor", "descendant" }) })
public class CompanyRelationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ancestor")
	private long ancestor;

	@Column(name = "descendant")
	private long descendant;

	@Column(name = "depth")
	private int depth;
	
	@Column(name="root")
	private int root;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getAncestor() {
		return ancestor;
	}

	public void setAncestor(long ancestor) {
		this.ancestor = ancestor;
	}

	public long getDescendant() {
		return descendant;
	}

	public void setDescendant(long descendant) {
		this.descendant = descendant;
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

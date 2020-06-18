package com.foxconn.iot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_update_task")
@EntityListeners(AuditingEntityListener.class)
public class UpdateTaskEntity {

	@Id
	private long id;
	
	@Column(name = "sn", length = 45)
	private String sn;
	
	@JoinColumn(name = "ver_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private ApplicationVersionEntity version;
	
	@CreatedDate
	@Column(name = "create_on", updatable = false)
	private Date createOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public ApplicationVersionEntity getVersion() {
		return version;
	}

	public void setVersion(ApplicationVersionEntity version) {
		this.version = version;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
}

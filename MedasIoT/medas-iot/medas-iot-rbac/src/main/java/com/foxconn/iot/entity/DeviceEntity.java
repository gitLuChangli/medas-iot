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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_dev", uniqueConstraints = {@UniqueConstraint(name="uq_dev_sn", columnNames = "sn"), 
		@UniqueConstraint(name="uq_dev_mac", columnNames = "mac")})
@EntityListeners(AuditingEntityListener.class)
public class DeviceEntity {
	
	@Id
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ver_id")
	private DeviceVersionEntity version;
	
	@Column(name = "sn", length = 32, nullable = false)
	private String sn;
	
	@Column(name = "mac", length = 32, nullable = false)
	private String mac;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyEntity company;
	
	@Column(name = "param", length = 255)
	private String parameter;
	
	@Column(name="details", length = 255)
	private String details;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dev_group_id")
	private DeviceGroupEntity group;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on")
	private Date createOn;
	
	@Column(name = "status")
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DeviceVersionEntity getVersion() {
		return version;
	}

	public void setVersion(DeviceVersionEntity version) {
		this.version = version;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public DeviceGroupEntity getGroup() {
		return group;
	}

	public void setGroup(DeviceGroupEntity group) {
		this.group = group;
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
	
}

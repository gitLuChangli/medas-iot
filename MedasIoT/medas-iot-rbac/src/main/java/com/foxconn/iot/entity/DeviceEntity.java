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

/**
 * 设备一旦录入，禁止修改序列号，型号及名称，允许修改版本号（已禁止修改设备类型基本信息）
 *
 */
@Entity
@Table(name = "tb_dev", uniqueConstraints = {@UniqueConstraint(name="uq_dev_sn", columnNames = "sn")})
@EntityListeners(AuditingEntityListener.class)
public class DeviceEntity {
	
	@Id
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ver_id")
	private DeviceVersionEntity version;
	
	/**
	 * 设备型号，适当的冗余
	 */
	@Column(name = "model", length = 45, nullable = false)
	private String model;

	/**
	 * 设备名称，适当冗余
	 */
	@Column(name = "name", length = 90, nullable = false)
	private String name;
	
	@Column(name = "sn", length = 32, nullable = false)
	private String sn;
	
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
	@Column(name = "create_on", updatable = false)
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
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

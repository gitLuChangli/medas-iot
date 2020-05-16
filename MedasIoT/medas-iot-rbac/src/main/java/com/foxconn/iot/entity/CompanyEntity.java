package com.foxconn.iot.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tb_company", uniqueConstraints = { @UniqueConstraint(name = "uq_company_code", columnNames = "code") })
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity {

	@Id
	@Column(name = "id")
	private long id;

	/**
	 * 部门费用代码
	 */
	@Column(name = "code", length = 45, nullable = false)
	private String code;

	/**
	 * 部门名称
	 */
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	/**
	 * 部门详情
	 */
	@Column(name = "details", length = 255)
	private String details;

	/**
	 * 地区（方便电子看板地图显示）
	 */
	@Column(name = "region", length = 45)
	private String region;

	/**
	 * 省市自治区
	 */
	@Column(name = "province", length = 255)
	private String province;

	/**
	 * 地级市
	 */
	@Column(name = "city", length = 255)
	private String city;

	/**
	 * 区县
	 */
	@Column(name = "county", length = 255)
	private String county;

	/**
	 * 部门地址
	 */
	@Column(name = "address", length = 255)
	private String address;

	/**
	 * 园区
	 */
	@Column(name = "area", length = 255)
	private String area;

	/**
	 * 状态
	 */
	@Column(name = "status", length = 1)
	private int status;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_on", length = 20, updatable = false)
	private Date createOn;

	/**
	 * 分组角色
	 */
	@ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
	@JoinTable(name = "tb_company_role", joinColumns = {
			@JoinColumn(name = "company_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role", referencedColumnName = "id") })
	private List<RoleEntity> roles;

	/**
	 * 设备分组
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private List<DeviceGroupEntity> deviceGroups;
	
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

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public List<DeviceGroupEntity> getDeviceGroups() {
		return deviceGroups;
	}

	public void setDeviceGroups(List<DeviceGroupEntity> deviceGroups) {
		this.deviceGroups = deviceGroups;
	}
	
}

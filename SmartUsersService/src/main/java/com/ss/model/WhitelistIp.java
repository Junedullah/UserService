/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the whitelist_ip database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "whitelist_ip")
@NamedQuery(name = "WhitelistIp.findAll", query = "SELECT w FROM WhitelistIp w")
public class WhitelistIp extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "whitelist_ip_id")
	private int whitelistIpId;

	private String description;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "is_active")
	private Boolean isActive;

	public WhitelistIp() {
	}

	public int getWhitelistIpId() {
		return whitelistIpId;
	}

	public void setWhitelistIpId(int whitelistIpId) {
		this.whitelistIpId = whitelistIpId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
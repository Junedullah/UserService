/**SmartSoftware User - Service */
/**
 * Description: Dto WhiteListIp  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.WhitelistIp;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoWhiteListIp {

	private int whitelistIpId;
	private String description;
	private String ipAddress;
	private Boolean isActive;
	private List<Integer> deleteIds;
	private String messageType;

	public DtoWhiteListIp() {
		super();
	}

	/**
	 * @param whitelistIpId
	 * @param description
	 */
	public DtoWhiteListIp(WhitelistIp whitelistIp) {
		super();
		this.whitelistIpId = whitelistIp.getWhitelistIpId();
		this.ipAddress = whitelistIp.getIpAddress();
		if (whitelistIp.getDescription() != null) {
			this.description = whitelistIp.getDescription();
		} else {
			this.description = "";
		}
		this.isActive = whitelistIp.getIsActive();
	}
	/**
	 * @return the whitelistIpId
	 */
	public int getWhitelistIpId() {
		return whitelistIpId;
	}
	/**
	 * @param whitelistIpId the whitelistIpId to set
	 */
	public void setWhitelistIpId(int whitelistIpId) {
		this.whitelistIpId = whitelistIpId;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Integer> getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(List<Integer> deleteIds) {
		this.deleteIds = deleteIds;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}

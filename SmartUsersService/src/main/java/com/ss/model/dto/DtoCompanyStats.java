/**
 * BTI - BAAN for Technology And Trade IntL. 
 * Copyright @ 2017 BTI. 
 * 
 * All rights reserved.
 * 
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF BTI. 
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE 
 * PRIOR EXPRESS WRITTEN PERMISSION OF BTI.
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Description: DTO Access Role class having getter and setter for fields (POJO) Name
 * Name of Project: BTI
 * Created on: July 06, 2017
 * Modified on: July 09, 2017 10:30:38 AM
 * @author seasia
 * Version: 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoCompanyStats {

	private Integer activeUserCount;
	private Integer inActiveUserCount;
	private Integer userGroupCount;
	private Integer roleGroupCount;
	private Integer roleCount;

	public DtoCompanyStats() {
		super();
	}
	
	/**
	 * @return the activeUserCount
	 */
	public Integer getActiveUserCount() {
		return activeUserCount;
	}
	/**
	 * @param activeUserCount the activeUserCount to set
	 */
	public void setActiveUserCount(Integer activeUserCount) {
		this.activeUserCount = activeUserCount;
	}
	/**
	 * @return the inActiveUserCount
	 */
	public Integer getInActiveUserCount() {
		return inActiveUserCount;
	}
	/**
	 * @param inActiveUserCount the inActiveUserCount to set
	 */
	public void setInActiveUserCount(Integer inActiveUserCount) {
		this.inActiveUserCount = inActiveUserCount;
	}
	/**
	 * @return the userGroupCount
	 */
	public Integer getUserGroupCount() {
		return userGroupCount;
	}
	/**
	 * @param userGroupCount the userGroupCount to set
	 */
	public void setUserGroupCount(Integer userGroupCount) {
		this.userGroupCount = userGroupCount;
	}
	/**
	 * @return the roleGroupCount
	 */
	public Integer getRoleGroupCount() {
		return roleGroupCount;
	}
	/**
	 * @param roleGroupCount the roleGroupCount to set
	 */
	public void setRoleGroupCount(Integer roleGroupCount) {
		this.roleGroupCount = roleGroupCount;
	}
	/**
	 * @return the roleCount
	 */
	public Integer getRoleCount() {
		return roleCount;
	}
	/**
	 * @param roleCount the roleCount to set
	 */
	public void setRoleCount(Integer roleCount) {
		this.roleCount = roleCount;
	}

}

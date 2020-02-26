/**SmartSoftware User - Service */
/**
 * Description: Dto Role Group  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.RoleGroup;
import com.ss.util.UtilRandomKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoRoleGroup {

	private Integer id;
	private String roleGroupCode;
	private String roleGroupName;
	private String roleGroupDescription;
	private List<Integer> roleIdList;
	private List<DtoAccessRole> roleGroupRoleList;
	private List<Integer> ids;
	private List<Integer> roleIds;
	private List<String> roleNames;
	private boolean isActive;

	/**
	 * @param roleGroup
	 */
	public DtoRoleGroup(RoleGroup roleGroup) {
		this.id = roleGroup.getRoleGroupId();
		this.roleGroupCode = roleGroup.getRoleGroupCode();
		if (UtilRandomKey.isNotBlank(roleGroup.getRoleGroupName())) {
			this.roleGroupName = roleGroup.getRoleGroupName();
		} else {
			this.roleGroupName = "";
		}
		if (UtilRandomKey.isNotBlank(roleGroup.getRoleGroupDescription())) {
			this.roleGroupDescription = roleGroup.getRoleGroupDescription();
		} else {
			this.roleGroupDescription = "";
		}

	}

	/**
	 * @param id
	 * @param roleGroupCode
	 * @param roleGroupName
	 * @param roleGroupDescription
	 * @param roleIdList
	 */
	public DtoRoleGroup(Integer id, String roleGroupCode, String roleGroupName, String roleGroupDescription,
			List<Integer> roleIdList) {
		super();
		this.id = id;
		this.roleGroupCode = roleGroupCode;
		if (UtilRandomKey.isNotBlank(roleGroupName)) {
			this.roleGroupName = roleGroupName;
		} else {
			this.roleGroupName = "";
		}
		if (UtilRandomKey.isNotBlank(roleGroupDescription)) {
			this.roleGroupDescription = roleGroupDescription;
		} else {
			this.roleGroupDescription = "";
		}
		this.roleIdList = roleIdList;
	}

	/**
	 * @param id
	 * @param roleGroupCode
	 * @param roleGroupName
	 * @param roleGroupDescription
	 */
	public DtoRoleGroup(Integer id, String roleGroupCode, String roleGroupName, String roleGroupDescription) {
		super();
		this.id = id;
		this.roleGroupCode = roleGroupCode;
		if (UtilRandomKey.isNotBlank(roleGroupName)) {
			this.roleGroupName = roleGroupName;
		} else {
			this.roleGroupName = "";
		}
		if (UtilRandomKey.isNotBlank(roleGroupDescription)) {
			this.roleGroupDescription = roleGroupDescription;
		} else {
			this.roleGroupDescription = "";
		}
	}

	public DtoRoleGroup(){
		 
	 }
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleGroupCode() {
		return roleGroupCode;
	}
	public void setRoleGroupCode(String roleGroupCode) {
		this.roleGroupCode = roleGroupCode;
	}
	/**
	 * @return the roleGroupName
	 */
	public String getRoleGroupName() {
		return roleGroupName;
	}
	/**
	 * @param roleGroupName the roleGroupName to set
	 */
	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
	/**
	 * @return the roleGroupDescription
	 */
	public String getRoleGroupDescription() {
		return roleGroupDescription;
	}
	/**
	 * @param roleGroupDescription the roleGroupDescription to set
	 */
	public void setRoleGroupDescription(String roleGroupDescription) {
		this.roleGroupDescription = roleGroupDescription;
	}
	/**
	 * @return the roleIdList
	 */
	public List<Integer> getRoleIdList() {
		return roleIdList;
	}
	/**
	 * @param roleIdList the roleIdList to set
	 */
	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}
	/**
	 * @return the roleGroupRoleList
	 */
	public List<DtoAccessRole> getRoleGroupRoleList() {
		return roleGroupRoleList;
	}
	/**
	 * @param roleGroupRoleList the roleGroupRoleList to set
	 */
	public void setRoleGroupRoleList(List<DtoAccessRole> roleGroupRoleList) {
		this.roleGroupRoleList = roleGroupRoleList;
	}
	/**
	 * @return the ids
	 */
	public List<Integer> getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}

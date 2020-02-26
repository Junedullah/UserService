/**SmartSoftware User - Service */
/**
 * Description: DTO Access Role class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 19, 2020
 * Modified on: March 19, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.AccessRole;
import com.ss.util.UtilRandomKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoAccessRole {

	private Integer id;
	private Integer accessRoleId;
	private String roleCode;
	private String roleName;
	private String roleDescription;
	private List<Integer> ids;
//	List<DtoModule> modulesList;
	private boolean isActive;

	/**
	 * @param accessRole
	 */
	public DtoAccessRole(AccessRole accessRole) {
		this.id = accessRole.getAccessRoleId();
		this.roleCode = accessRole.getRoleCode();

		if (UtilRandomKey.isNotBlank(accessRole.getRoleName())) {
			this.roleName = accessRole.getRoleName();
		} else {
			this.roleName = "";
		}

		if (UtilRandomKey.isNotBlank(accessRole.getRoleDescription())) {
			this.roleDescription = accessRole.getRoleDescription();
		} else {
			this.roleDescription = "";
		}
	}

	/**
	 * @param id
	 * @param roleCode
	 * @param roleName
	 * @param roleDescription
	 */
	public DtoAccessRole(Integer id, String roleCode, String roleName, String roleDescription) {
		super();
		this.id = id;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleName = roleName!=null && !roleName.trim().isEmpty()?roleName:"";
		this.roleDescription =roleDescription!=null && !roleDescription.trim().isEmpty()?roleDescription:"";
	}

	public DtoAccessRole() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/*public List<DtoModule> getModulesList() {
		return modulesList;
	}

	public void setModulesList(List<DtoModule> modulesList) {
		this.modulesList = modulesList;
	}*/

	public Integer getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(Integer accessRoleId) {
		this.accessRoleId = accessRoleId;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}

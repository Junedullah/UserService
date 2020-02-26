/**SmartSoftware User - Service */
/**
 * Description: Dto User Group  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.UserGroup;
import com.ss.util.UtilRandomKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoUserGroup {

	private int id;
	private String groupCode;
	private String groupName;
	private String groupDesc;
	private String roleGroup;
	private List<Integer> roleGroupList;
	private List<String> selecteRoleGroup;
	private List<DtoRoleGroup> listRoleGroup;
	private List<Integer> deleteIds;
	private String languageName;
	private String languageId;
	List<Integer> roleGroupIds;
	List<String> roleGroupNames;
	private boolean isActive;
	private Integer companyId;

	/**
	 * @param userGroup
	 */
	public DtoUserGroup(UserGroup userGroup) {
		this.id = userGroup.getUserGroupId();

		if (UtilRandomKey.isNotBlank(userGroup.getGroupCode())) {
			this.groupCode = userGroup.getGroupCode();
		} else {
			this.groupCode = "";
		}

		if (UtilRandomKey.isNotBlank(userGroup.getGroupName())) {
			this.groupName = userGroup.getGroupName();
		} else {
			this.groupName = "";
		}

		if (UtilRandomKey.isNotBlank(userGroup.getGroupDesc())) {
			this.groupDesc = userGroup.getGroupDesc();
		} else {
			this.groupDesc = "";
		}
	}

	/**
	 * @param id
	 * @param groupName
	 * @param groupCode
	 */
	public DtoUserGroup(int id, String groupName, String groupCode) {
		super();
		this.id = id;
		if (UtilRandomKey.isNotBlank(groupName)) {
			this.groupName = groupName;
		} else {
			this.groupName = "";
		}
		this.groupCode = groupCode;
	}

	public DtoUserGroup() {

	}

	/**
	 * @param id
	 * @param groupCode
	 * @param groupName
	 * @param groupDesc
	 * @param isActive
	 * @param roleGroupList
	 */
	public DtoUserGroup(int id, String groupCode, String groupName, String groupDesc, Boolean isActive,
			List<Integer> roleGroupList) {
		super();
		this.id = id;
		this.groupCode = groupCode;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.isActive = isActive;
		this.roleGroupList = roleGroupList;
	}

	/**
	 * @param id
	 * @param groupCode
	 * @param groupName
	 * @param groupDesc
	 * @param isActive
	 */
	public DtoUserGroup(int id, String groupCode, String groupName, String groupDesc, Boolean isActive) {
		super();
		this.id = id;
		this.groupCode = groupCode;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public List<String> getSelecteRoleGroup() {
		return selecteRoleGroup;
	}

	public void setSelecteRoleGroup(List<String> selecteRoleGroup) {
		this.selecteRoleGroup = selecteRoleGroup;
	}

	public List<Integer> getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(List<Integer> deleteIds) {
		this.deleteIds = deleteIds;
	}

	public List<Integer> getRoleGroupList() {
		return roleGroupList;
	}

	public void setRoleGroupList(List<Integer> roleGroupList) {
		this.roleGroupList = roleGroupList;
	}

	public List<DtoRoleGroup> getListRoleGroup() {
		return listRoleGroup;
	}

	public void setListRoleGroup(List<DtoRoleGroup> listRoleGroup) {
		this.listRoleGroup = listRoleGroup;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public List<Integer> getRoleGroupIds() {
		return roleGroupIds;
	}

	public void setRoleGroupIds(List<Integer> roleGroupIds) {
		this.roleGroupIds = roleGroupIds;
	}

	public List<String> getRoleGroupNames() {
		return roleGroupNames;
	}

	public void setRoleGroupNames(List<String> roleGroupNames) {
		this.roleGroupNames = roleGroupNames;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}

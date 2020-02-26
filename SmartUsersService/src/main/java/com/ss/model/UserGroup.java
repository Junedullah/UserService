/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_group database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "user_group")
@NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u")
public class UserGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_group_id")
	private int userGroupId;

	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "group_desc")
	private String groupDesc;

	@Column(name = "group_name")
	private String groupName;

	// bi-directional many-to-one association to UserGroupRoleGroup
	@OneToMany(mappedBy = "userGroup")
	private List<UserGroupRoleGroup> userGroupRoleGroups;

	public UserGroup() {
	}

	public int getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<UserGroupRoleGroup> getUserGroupRoleGroups() {
		return this.userGroupRoleGroups;
	}

	public void setUserGroupRoleGroups(List<UserGroupRoleGroup> userGroupRoleGroups) {
		this.userGroupRoleGroups = userGroupRoleGroups;
	}

	public UserGroupRoleGroup addUserGroupRoleGroup(UserGroupRoleGroup userGroupRoleGroup) {
		getUserGroupRoleGroups().add(userGroupRoleGroup);
		userGroupRoleGroup.setUserGroup(this);

		return userGroupRoleGroup;
	}

	public UserGroupRoleGroup removeUserGroupRoleGroup(UserGroupRoleGroup userGroupRoleGroup) {
		getUserGroupRoleGroups().remove(userGroupRoleGroup);
		userGroupRoleGroup.setUserGroup(null);

		return userGroupRoleGroup;
	}

}
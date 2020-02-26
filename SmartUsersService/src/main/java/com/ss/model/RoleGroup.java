/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the role_group database table.
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
@Table(name = "role_group")
@NamedQuery(name = "RoleGroup.findAll", query = "SELECT r FROM RoleGroup r")
public class RoleGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_group_id")
	private int roleGroupId;

	@Column(name = "role_group_code")
	private String roleGroupCode;

	@Column(name = "role_group_description")
	private String roleGroupDescription;

	@Column(name = "role_group_name")
	private String roleGroupName;

	// bi-directional many-to-one association to RoleGroupAccessRole
	@OneToMany(mappedBy = "roleGroup")
	private List<RoleGroupAccessRole> roleGroupAccessRoles;

	// bi-directional many-to-one association to UserGroupRoleGroup
	@OneToMany(mappedBy = "roleGroup")
	private List<UserGroupRoleGroup> userGroupRoleGroups;

	public RoleGroup() {
	}

	public int getRoleGroupId() {
		return this.roleGroupId;
	}

	public void setRoleGroupId(int roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public String getRoleGroupCode() {
		return this.roleGroupCode;
	}

	public void setRoleGroupCode(String roleGroupCode) {
		this.roleGroupCode = roleGroupCode;
	}

	public String getRoleGroupDescription() {
		return roleGroupDescription;
	}

	public void setRoleGroupDescription(String roleGroupDescription) {
		this.roleGroupDescription = roleGroupDescription;
	}

	public String getRoleGroupName() {
		return this.roleGroupName;
	}

	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}

	public List<RoleGroupAccessRole> getRoleGroupAccessRoles() {
		return this.roleGroupAccessRoles;
	}

	public void setRoleGroupAccessRoles(List<RoleGroupAccessRole> roleGroupAccessRoles) {
		this.roleGroupAccessRoles = roleGroupAccessRoles;
	}

	public RoleGroupAccessRole addRoleGroupAccessRole(RoleGroupAccessRole roleGroupAccessRole) {
		getRoleGroupAccessRoles().add(roleGroupAccessRole);
		roleGroupAccessRole.setRoleGroup(this);

		return roleGroupAccessRole;
	}

	public RoleGroupAccessRole removeRoleGroupAccessRole(RoleGroupAccessRole roleGroupAccessRole) {
		getRoleGroupAccessRoles().remove(roleGroupAccessRole);
		roleGroupAccessRole.setRoleGroup(null);

		return roleGroupAccessRole;
	}

	public List<UserGroupRoleGroup> getUserGroupRoleGroups() {
		return this.userGroupRoleGroups;
	}

	public void setUserGroupRoleGroups(List<UserGroupRoleGroup> userGroupRoleGroups) {
		this.userGroupRoleGroups = userGroupRoleGroups;
	}

	public UserGroupRoleGroup addUserGroupRoleGroup(UserGroupRoleGroup userGroupRoleGroup) {
		getUserGroupRoleGroups().add(userGroupRoleGroup);
		userGroupRoleGroup.setRoleGroup(this);

		return userGroupRoleGroup;
	}

	public UserGroupRoleGroup removeUserGroupRoleGroup(UserGroupRoleGroup userGroupRoleGroup) {
		getUserGroupRoleGroups().remove(userGroupRoleGroup);
		userGroupRoleGroup.setRoleGroup(null);

		return userGroupRoleGroup;
	}

}
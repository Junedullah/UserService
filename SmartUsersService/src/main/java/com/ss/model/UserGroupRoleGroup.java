/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_group_role_group database table.
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "user_group_role_group")
@NamedQuery(name = "UserGroupRoleGroup.findAll", query = "SELECT u FROM UserGroupRoleGroup u")
public class UserGroupRoleGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_group_role_group_id")
	private int userGroupRoleGroupId;

	// bi-directional many-to-one association to UserGroup
	@ManyToOne
	@JoinColumn(name = "user_group_id")
	private UserGroup userGroup;

	// bi-directional many-to-one association to RoleGroup
	@ManyToOne
	@JoinColumn(name = "role_group_id")
	private RoleGroup roleGroup;

	public UserGroupRoleGroup() {
	}

	public int getUserGroupRoleGroupId() {
		return this.userGroupRoleGroupId;
	}

	public void setUserGroupRoleGroupId(int userGroupRoleGroupId) {
		this.userGroupRoleGroupId = userGroupRoleGroupId;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public RoleGroup getRoleGroup() {
		return this.roleGroup;
	}

	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup;
	}

}
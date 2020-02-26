/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the role_group_access_role database table.
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
@Table(name = "role_group_access_role")
@NamedQuery(name = "RoleGroupAccessRole.findAll", query = "SELECT r FROM RoleGroupAccessRole r")
public class RoleGroupAccessRole extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_group_access_role_id")
	private int roleGroupAccessRoleId;

	// bi-directional many-to-one association to AccessRole
	@ManyToOne
	@JoinColumn(name = "access_role_id")
	private AccessRole accessRole;

	// bi-directional many-to-one association to RoleGroup
	@ManyToOne
	@JoinColumn(name = "role_group_id")
	private RoleGroup roleGroup;

	public RoleGroupAccessRole() {
	}

	public int getRoleGroupAccessRoleId() {
		return this.roleGroupAccessRoleId;
	}

	public void setRoleGroupAccessRoleId(int roleGroupAccessRoleId) {
		this.roleGroupAccessRoleId = roleGroupAccessRoleId;
	}

	public AccessRole getAccessRole() {
		return this.accessRole;
	}

	public void setAccessRole(AccessRole accessRole) {
		this.accessRole = accessRole;
	}

	public RoleGroup getRoleGroup() {
		return this.roleGroup;
	}

	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup;
	}

}
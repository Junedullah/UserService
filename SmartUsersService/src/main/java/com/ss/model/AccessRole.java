/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the access_role database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
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
@Table(name = "access_role")
@NamedQuery(name = "AccessRole.findAll", query = "SELECT a FROM AccessRole a")
public class AccessRole extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_role_id")
	private int accessRoleId;

	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_decription")
	private String roleDescription;

	@Column(name = "role_name")
	private String roleName;

	// bi-directional many-to-one association to RoleGroupAccessRole
	@OneToMany(mappedBy = "accessRole")
	private List<RoleGroupAccessRole> roleGroupAccessRoles;

	public int getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(int accessRoleId) {
		this.accessRoleId = accessRoleId;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<RoleGroupAccessRole> getRoleGroupAccessRoles() {
		return this.roleGroupAccessRoles;
	}

	public void setRoleGroupAccessRoles(List<RoleGroupAccessRole> roleGroupAccessRoles) {
		this.roleGroupAccessRoles = roleGroupAccessRoles;
	}

	public RoleGroupAccessRole addRoleGroupAccessRole(RoleGroupAccessRole roleGroupAccessRole) {
		getRoleGroupAccessRoles().add(roleGroupAccessRole);
		roleGroupAccessRole.setAccessRole(this);

		return roleGroupAccessRole;
	}

	public RoleGroupAccessRole removeRoleGroupAccessRole(RoleGroupAccessRole roleGroupAccessRole) {
		getRoleGroupAccessRoles().remove(roleGroupAccessRole);
		roleGroupAccessRole.setAccessRole(null);

		return roleGroupAccessRole;
	}

}
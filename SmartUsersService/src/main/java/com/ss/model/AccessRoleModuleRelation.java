/**SmartSoftware User - Service */
/**
 * Description:The persistent class for the access_role_module_relation database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
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
@Table(name = "access_role_module_relations")
@NamedQuery(name = "AccessRoleModuleRelation.findAll", query = "SELECT a FROM AccessRoleModuleRelation a")
public class AccessRoleModuleRelation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_role_module_relation_id")
	private int accessRoleModuleRelationId;

	// bi-directional many-to-one association to Module
	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

	// bi-directional many-to-one association to AccessRole
	@ManyToOne
	@JoinColumn(name = "access_role_id")
	private AccessRole accessRole;
	
	@Column(name = "is_active", columnDefinition = "tinyint(0) default 1")
	protected Boolean isActive;

	public int getAccessRoleModuleRelationId() {
		return this.accessRoleModuleRelationId;
	}

	public void setAccessRoleModuleRelationId(int accessRoleModuleRelationId) {
		this.accessRoleModuleRelationId = accessRoleModuleRelationId;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public AccessRole getAccessRole() {
		return this.accessRole;
	}

	public void setAccessRole(AccessRole accessRole) {
		this.accessRole = accessRole;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	

}
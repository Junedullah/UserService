/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the access_role_module_relation database table.
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
@Table(name = "access_role_screen_relations")
@NamedQuery(name = "AccessRoleScreenRelation.findAll", query = "SELECT a FROM AccessRoleScreenRelation a")
public class AccessRoleScreenRelation extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_role_screen_relation_id")
	private int accessRoleScreenRelationId;

	@Column(name = "delete_access")
	private Boolean deleteAccess;

	@Column(name = "read_access")
	private Boolean readAccess;

	@Column(name = "write_access")
	private Boolean writeAccess;

	// bi-directional many-to-one association to Field
	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	// bi-directional many-to-one association to Module
	@ManyToOne
	@JoinColumn(name = "access_role_module_relation_id")
	private AccessRoleModuleRelation accessRoleModuleRelation;

	// bi-directional many-to-one association to Screen
	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	public Boolean getDeleteAccess() {
		return deleteAccess;
	}

	public void setDeleteAccess(Boolean deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public Boolean getReadAccess() {
		return readAccess;
	}

	public void setReadAccess(Boolean readAccess) {
		this.readAccess = readAccess;
	}

	public Boolean getWriteAccess() {
		return writeAccess;
	}

	public void setWriteAccess(Boolean writeAccess) {
		this.writeAccess = writeAccess;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Screen getScreen() {
		return this.screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public int getAccessRoleScreenRelationId() {
		return accessRoleScreenRelationId;
	}

	public void setAccessRoleScreenRelationId(int accessRoleScreenRelationId) {
		this.accessRoleScreenRelationId = accessRoleScreenRelationId;
	}

	public AccessRoleModuleRelation getAccessRoleModuleRelation() {
		return accessRoleModuleRelation;
	}

	public void setAccessRoleModuleRelation(AccessRoleModuleRelation accessRoleModuleRelation) {
		this.accessRoleModuleRelation = accessRoleModuleRelation;
	}

}
/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the fields database table.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "fields")
@NamedQuery(name = "Field.findAll", query = "SELECT f FROM Field f")
public class Field extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_id")
	private int fieldId;

	private String description;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "grid_field_name")
	private String gridFieldName;

	@Column(name = "help_message")
	private String helpMessage;

	@Column(name = "field_short")
	private String fieldShort;

	@Column(name = "field_code")
	private String fieldCode;

	@Column(name = "is_mandatory")
	private Boolean isMandatory;

	@Column(name = "field_width")
	private Integer fieldWidth;

	// bi-directional many-to-one association to AccessRoleModuleRelation
	@OneToMany(mappedBy = "field")
	private List<AccessRoleScreenRelation> accessRoleModuleRelations;

	// bi-directional many-to-one association to FieldValidation
	@OneToMany(mappedBy = "field")
	private List<FieldValidation> fieldValidations;

	@ManyToMany
	@JoinTable(name = "FIELD_COMPANY", joinColumns = { @JoinColumn(name = "field_id") }, inverseJoinColumns = {
			@JoinColumn(name = "company_id") })
	private List<Company> companyId;


	private Boolean isVisible;

	// bi-directional many-to-one association to Screen
	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

	// bi-directional many-to-one association to Grid
	@ManyToOne
	@JoinColumn(name = "grid_id")
	private Grid grid;

	@Column(name = "col_order")
	private Integer colOrder;

	public Field() {
	}

	public int getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFieldShort() {
		return this.fieldShort;
	}

	public void setFieldShort(String fieldShort) {
		this.fieldShort = fieldShort;
	}

	public List<AccessRoleScreenRelation> getAccessRoleModuleRelations() {
		return this.accessRoleModuleRelations;
	}

	public void setAccessRoleModuleRelations(List<AccessRoleScreenRelation> accessRoleModuleRelations) {
		this.accessRoleModuleRelations = accessRoleModuleRelations;
	}

	public AccessRoleScreenRelation addAccessRoleModuleRelation(AccessRoleScreenRelation accessRoleModuleRelation) {
		getAccessRoleModuleRelations().add(accessRoleModuleRelation);
		accessRoleModuleRelation.setField(this);

		return accessRoleModuleRelation;
	}

	public AccessRoleScreenRelation removeAccessRoleModuleRelation(AccessRoleScreenRelation accessRoleModuleRelation) {
		getAccessRoleModuleRelations().remove(accessRoleModuleRelation);
		accessRoleModuleRelation.setField(null);

		return accessRoleModuleRelation;
	}

	public Screen getScreen() {
		return this.screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public List<FieldValidation> getFieldValidations() {
		return this.fieldValidations;
	}

	public void setFieldValidations(List<FieldValidation> fieldValidations) {
		this.fieldValidations = fieldValidations;
	}

	public FieldValidation addFieldValidation(FieldValidation fieldValidation) {
		getFieldValidations().add(fieldValidation);
		fieldValidation.setField(this);

		return fieldValidation;
	}

	public FieldValidation removeFieldValidation(FieldValidation fieldValidation) {
		getFieldValidations().remove(fieldValidation);
		fieldValidation.setField(null);

		return fieldValidation;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public List<Company> getCompanyId() {
		return companyId;
	}

	public void setCompanyId(List<Company> companyId) {
		this.companyId = companyId;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Integer getColOrder() {
		return colOrder;
	}

	public void setColOrder(Integer colOrder) {
		this.colOrder = colOrder;
	}

	public Integer getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(Integer fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getGridFieldName() {
		return gridFieldName;
	}

	public void setGridFieldName(String gridFieldName) {
		this.gridFieldName = gridFieldName;
	}

}
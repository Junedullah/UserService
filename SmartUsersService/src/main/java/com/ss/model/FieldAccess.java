/**SmartSoftware User - Service */
/**
 * Description: The persistent class Field Level Access.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned,Shahnawaz
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
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "field_access")
@NamedQuery(name = "FieldAccess.findAll", query = "SELECT a FROM FieldAccess a")
public class FieldAccess extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_access_id")
	private int fieldAccessId;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@Column(name = "mandatory")
	private Boolean mandatory;

	@ManyToOne
	@JoinColumn(name = "lang_id")
	private Language language;

	public int getFieldAccessId() {
		return fieldAccessId;
	}

	public void setFieldAccessId(int fieldAccessId) {
		this.fieldAccessId = fieldAccessId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

}

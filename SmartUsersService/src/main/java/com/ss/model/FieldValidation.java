/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the field_validation database table.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
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
@Table(name = "field_validations")
@NamedQuery(name = "FieldValidation.findAll", query = "SELECT f FROM FieldValidation f")
public class FieldValidation extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_validation_id")
	private int fieldValidationId;

	// bi-directional many-to-one association to Field
	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field field;

	// bi-directional many-to-one association to ValidationMessage
	@ManyToOne
	@JoinColumn(name = "validation_message_id")
	private ValidationMessage validationMessage;

	public int getFieldValidationId() {
		return this.fieldValidationId;
	}

	public void setFieldValidationId(int fieldValidationId) {
		this.fieldValidationId = fieldValidationId;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ValidationMessage getValidationMessage() {
		return this.validationMessage;
	}

	public void setValidationMessage(ValidationMessage validationMessage) {
		this.validationMessage = validationMessage;
	}

}
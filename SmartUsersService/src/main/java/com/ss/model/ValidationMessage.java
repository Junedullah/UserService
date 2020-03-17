/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the validation_message database table.
 * Name of Project: SmartSoftware
 * Created on: March 15, 2020
 * Modified on: March 15, 2020 11:19:38 AM
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "validation_message")
@NamedQuery(name = "ValidationMessage.findAll", query = "SELECT v FROM ValidationMessage v")
public class ValidationMessage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "validation_message_id")
	private int validationMessageId;

	@Column(name = "message")
	private String message;

	@Column(name = "message_short")
	private String messageShort;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;
	
	/*@Column(name = "message_primary")
	private String messageP;
	
	@Column(name = "message_secondary")
	private String messageS;*/
	

	// bi-directional many-to-one association to FieldValidation
	@OneToMany(mappedBy = "validationMessage")
	private List<FieldValidation> fieldValidations;

	public ValidationMessage() {
	}

	public int getValidationMessageId() {
		return this.validationMessageId;
	}

	public void setValidationMessageId(int validationMessageId) {
		this.validationMessageId = validationMessageId;
	}

	public String getMessageShort() {
		return this.messageShort;
	}

	public void setMessageShort(String messageShort) {
		this.messageShort = messageShort;
	}

	public List<FieldValidation> getFieldValidations() {
		return this.fieldValidations;
	}

	public void setFieldValidations(List<FieldValidation> fieldValidations) {
		this.fieldValidations = fieldValidations;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	/*public String getMessageP() {
		return messageP;
	}

	public void setMessageP(String messageP) {
		this.messageP = messageP;
	}

	public String getMessageS() {
		return messageS;
	}

	public void setMessageS(String messageS) {
		this.messageS = messageS;
	}*/
	
	
	
}
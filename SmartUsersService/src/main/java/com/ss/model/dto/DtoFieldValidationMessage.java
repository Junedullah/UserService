/**SmartSoftware User - Service */
/**
 * Description: Dto Field Validation Message class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.ValidationMessage;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoFieldValidationMessage {

	private int fieldId;
	private String validationName;
	private String validationMessagePrimary;
	private String validationMessageSecondary;
	private String validationMessage;
	
	public DtoFieldValidationMessage() {

	}

	/**
	 * @param fieldValidation
	 */
	/*public DtoFieldValidationMessage(FieldValidation fieldValidation,int fie) 
	{
		
		this.fieldId = fieldValidation.getField().getFieldId();
		if (UtilRandomKey.isNotBlank(fieldValidation.getValidationMessage().getMessageShort())) {
			this.validationName = fieldValidation.getValidationMessage().getMessageShort();
		} else {
			this.validationName = "";
		}

		if (UtilRandomKey.isNotBlank(fieldValidation.getValidationMessage().getMessage())) {
			this.validationMessagePrimary = fieldValidation.getValidationMessage().getMessage();
		} else {
			this.validationMessagePrimary = "";
		}
		
	}*/

	/**
	 * @param fieldValidation
	 * @param langId
	 */
	public DtoFieldValidationMessage(ValidationMessage validationMessage, String langId) 
	{
		if (UtilRandomKey.isNotBlank(validationMessage.getMessageShort())) {
			this.validationName = validationMessage.getMessageShort();
		} else {
			this.validationName = "";
		}

		if (UtilRandomKey.isNotBlank(validationMessage.getMessage())) {
			this.validationMessage = validationMessage.getMessage();
		} else {
			this.validationMessage = "";
		}
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getValidationName() {
		return validationName;
	}

	public void setValidationName(String validationName) {
		this.validationName = validationName;
	}

	public String getValidationMessagePrimary() {
		return validationMessagePrimary;
	}

	public void setValidationMessagePrimary(String validationMessagePrimary) {
		this.validationMessagePrimary = validationMessagePrimary;
	}

	public String getValidationMessageSecondary() {
		return validationMessageSecondary;
	}

	public void setValidationMessageSecondary(String validationMessageSecondary) {
		this.validationMessageSecondary = validationMessageSecondary;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

}

/**SmartSoftware User - Service */
/**
 * Description: DTO Field Detail class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Field;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoFieldDetail {

	private Integer fieldId;
	private String fieldName;
	private String fieldValuePrimary;
	private String fieldValueSecondary;
	private String description;
	private String helpMessagePrimary;
	private List<Integer> ids;
	private String helpMessageSecondary;
	private List<DtoFieldValidationMessage> listDtoFieldValidationMessage;
	private String fieldValue;
	private String helpMessage;
	private Boolean deleteAccess;
	private Boolean readAccess;
	private Boolean writeAccess;
	private List<DtoCompany> dtoCompanies;
	private List<Field> status;
	private List<DtoFieldAccessDeatils> languageIds;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean isMandatory;
	private Boolean isVisible;
	private Integer companyId;
	private Integer languageId;
	private Integer gridId;
	private Integer colOrder;
	private String gridFieldName;
	private Integer fieldWidth;
	private String fieldShort;
	private String fieldCode;
	private Integer screenId;
	private String messageType;




	public DtoFieldDetail() {

	}

	/**
	 * @param field
	 */
	public DtoFieldDetail(Field field) {
		if (UtilRandomKey.isNotBlank(field.getFieldShort())) {
			this.fieldName = field.getFieldShort();
		} else {
			this.fieldName = "";
		}

		if (UtilRandomKey.isNotBlank(field.getFieldName())) {
			this.fieldValuePrimary = field.getFieldName();
		} else {
			this.fieldValuePrimary = "";
		}

		if (UtilRandomKey.isNotBlank(field.getDescription())) {
			this.description = field.getDescription();
		} else {
			this.description = "";
		}

		if (UtilRandomKey.isNotBlank(field.getHelpMessage())) {
			this.helpMessagePrimary = field.getHelpMessage();
		} else {
			this.helpMessagePrimary = "";
		}
	}

	/**
	 * @param field
	 * @param langId
	 */
	public DtoFieldDetail(Field field, String langId) {

		if (UtilRandomKey.isNotBlank(field.getFieldShort())) {
			this.fieldName = field.getFieldShort();
		} else {
			this.fieldName = "";
		}
		if (UtilRandomKey.isNotBlank(field.getDescription())) {
			this.description = field.getDescription();
		} else {
			this.description = "";
		}

		    this.fieldId = field.getFieldId();
			if (UtilRandomKey.isNotBlank(field.getHelpMessage())) {
				this.helpMessage = field.getHelpMessage();
			} else {
				this.helpMessage = "N/A";
			}

			if (UtilRandomKey.isNotBlank(field.getFieldName())) {
				this.fieldValue = field.getFieldName();
			} else {
				this.fieldValue = "N/A";
			}
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValuePrimary() {
		return fieldValuePrimary;
	}

	public void setFieldValuePrimary(String fieldValuePrimary) {
		this.fieldValuePrimary = fieldValuePrimary;
	}

	public String getFieldValueSecondary() {
		return fieldValueSecondary;
	}

	public void setFieldValueSecondary(String fieldValueSecondary) {
		this.fieldValueSecondary = fieldValueSecondary;
	}

	public String getHelpMessagePrimary() {
		return helpMessagePrimary;
	}

	public void setHelpMessagePrimary(String helpMessagePrimary) {
		this.helpMessagePrimary = helpMessagePrimary;
	}

	public String getHelpMessageSecondary() {
		return helpMessageSecondary;
	}

	public void setHelpMessageSecondary(String helpMessageSecondary) {
		this.helpMessageSecondary = helpMessageSecondary;
	}

	public List<DtoFieldValidationMessage> getListDtoFieldValidationMessage() {
		return listDtoFieldValidationMessage;
	}

	public void setListDtoFieldValidationMessage(List<DtoFieldValidationMessage> listDtoFieldValidationMessage) {
		this.listDtoFieldValidationMessage = listDtoFieldValidationMessage;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

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

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public List<DtoCompany> getDtoCompanies() {
		return dtoCompanies;
	}

	public void setDtoCompanies(List<DtoCompany> dtoCompanies) {
		this.dtoCompanies = dtoCompanies;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<Field> getStatus() {
		return status;
	}

	public void setStatus(List<Field> status) {
		this.status = status;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public List<DtoFieldAccessDeatils> getLanguageIds() {
		return languageIds;
	}

	public void setLanguageIds(List<DtoFieldAccessDeatils> languageIds) {
		this.languageIds = languageIds;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Integer getColOrder() {
		return colOrder;
	}

	public void setColOrder(Integer colOrder) {
		this.colOrder = colOrder;
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

	public Integer getFieldWidth() {
		return fieldWidth;
	}

	public void setFieldWidth(Integer fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	public String getFieldShort() {
		return fieldShort;
	}

	public void setFieldShort(String fieldShort) {
		this.fieldShort = fieldShort;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
	

}

/**SmartSoftware User - Service */
/**
 * Description: Dto Field Access class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 19, 2020
 * Modified on: March 19, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.ss.model.FieldAccess;
import com.ss.util.UtilRandomKey;

public class DtoFieldAccess {

	private Integer fieldAccessId;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer companyId;
	private Integer moduleId;
	private String moduleName;
	private String fieldName;
	private Integer fieldId;
	private Integer languageId;
	private Boolean isMandatory;
	private Integer screenId;
	private String screenName;
	private List<DtoFieldAccess> accessIds;
	private List<DtoFieldAccessDeatils> ids;

	public DtoFieldAccess(FieldAccess field) {
		if (UtilRandomKey.isNotNull(field.getField().getFieldId())) {
			this.fieldName = field.getField().getFieldName();
		} else {
			this.fieldName = "";
		}
	}

	/**
	 * @param field
	 * @param langId
	 */
	public DtoFieldAccess(FieldAccess field, String langId) {

		if (UtilRandomKey.isNotNull(field.getField().getFieldId())) {
			this.fieldName = field.getField().getFieldName();
		} else {
			this.fieldName = "";
		}
	}

	public DtoFieldAccess() {

	}

	public Integer getFieldAccessId() {
		return fieldAccessId;
	}

	public void setFieldAccessId(Integer fieldAccessId) {
		this.fieldAccessId = fieldAccessId;
	}

	public List<DtoFieldAccessDeatils> getIds() {
		return ids;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setIds(List<DtoFieldAccessDeatils> ids) {
		this.ids = ids;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public List<DtoFieldAccess> getAccessIds() {
		return accessIds;
	}

	public void setAccessIds(List<DtoFieldAccess> accessIds) {
		this.accessIds = accessIds;
	}

}
/**SmartSoftware User - Service */
/**
 * Description: DTO Screen Menu class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoScreenMenu {

	private Integer screenMenuId;
	private List<Integer> ids;
	private String moduleNamePrimary;
	private String moduleNameSecondary;
	private Integer moduleId;
	private String description;
	private String screenNamePrimary;
	private String screenNameSecondary;
	private Integer screenCategoryId;
	private String sideMenuURL;
	private String moduleCode;
	private String screenCode;
	private String sideMenuCode;
	private String helpMessagePrimary;
	private String helpMessageSecondary;
	private String moduleName;
	private String screenName;
	private String helpMessage;
	private String messageType;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean readAccess;
	private Boolean writeAccess;
	private Boolean deleteAccess;
	private Integer languageid;
	private Integer companyId;

	public Integer getScreenMenuId() {
		return screenMenuId;
	}

	public void setScreenMenuId(Integer screenMenuId) {
		this.screenMenuId = screenMenuId;
	}

	public String getModuleNamePrimary() {
		return moduleNamePrimary;
	}

	public void setModuleNamePrimary(String moduleNamePrimary) {
		this.moduleNamePrimary = moduleNamePrimary;
	}

	public String getModuleNameSecondary() {
		return moduleNameSecondary;
	}

	public void setModuleNameSecondary(String moduleNameSecondary) {
		this.moduleNameSecondary = moduleNameSecondary;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getScreenNamePrimary() {
		return screenNamePrimary;
	}

	public void setScreenNamePrimary(String screenNamePrimary) {
		this.screenNamePrimary = screenNamePrimary;
	}

	public String getScreenNameSecondary() {
		return screenNameSecondary;
	}

	public void setScreenNameSecondary(String screenNameSecondary) {
		this.screenNameSecondary = screenNameSecondary;
	}

	public Integer getScreenCategoryId() {
		return screenCategoryId;
	}

	public void setScreenCategoryId(Integer screenCategoryId) {
		this.screenCategoryId = screenCategoryId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getSideMenuCode() {
		return sideMenuCode;
	}

	public void setSideMenuCode(String sideMenuCode) {
		this.sideMenuCode = sideMenuCode;
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

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
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

	public Boolean getDeleteAccess() {
		return deleteAccess;
	}

	public void setDeleteAccess(Boolean deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public Integer getLanguageid() {
		return languageid;
	}

	public void setLanguageid(Integer languageid) {
		this.languageid = languageid;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSideMenuURL() {
		return sideMenuURL;
	}

	public void setSideMenuURL(String sideMenuURL) {
		this.sideMenuURL = sideMenuURL;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	

	
	

}

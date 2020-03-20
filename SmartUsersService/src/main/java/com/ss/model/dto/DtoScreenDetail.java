/**SmartSoftware User - Service */
/**
 * Description: DTO Screen Detail class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Screen;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoScreenDetail {
	private String moduleNamePrimary;
	private String moduleNameSecondary;
	private Integer moduleId;
	private String screenNamePrimary;
	private String screenNameSecondary;
	private Integer screenId;
	private String description;
	private List<DtoFieldDetail> fieldList;
	private List<DtoFieldAccess> fieldAccessList;
	private List<DtoGrid> girdList;
	private List<DtoGridData> gridDataList;
	private String moduleCode;
	private String screenCode;
	private String helpMessagePrimary;
	private String helpMessageSecondary;
	private String moduleName;
	private String screenName;
	private String helpMessage;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean readAccess;
	private Boolean writeAccess;
	private Boolean deleteAccess;
	private Integer accessRoleId;
	private Integer languageId;
	private Boolean isMandatory;
	private Integer fieldAccessId;
	private Integer companyId;
	private int userId;
	private String sideMenuCode;
	private String sideMenuUrl;

	public DtoScreenDetail() {

	}
	
	/**
	 * @param screen
	 * @param langId
	 */
	public DtoScreenDetail(Screen screen, String langId) {
		this.screenId = screen.getScreenId();
		this.screenCode = screen.getScreenCode();
		
			this.helpMessage = screen.getHelpMessage();
			this.screenName = screen.getScreenName();
		 
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
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

	public List<DtoFieldDetail> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DtoFieldDetail> fieldList) {
		this.fieldList = fieldList;
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

	public Integer getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(Integer accessRoleId) {
		this.accessRoleId = accessRoleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public List<DtoFieldAccess> getFieldAccessList() {
		return fieldAccessList;
	}

	public void setFieldAccessList(List<DtoFieldAccess> fieldAccessList) {
		this.fieldAccessList = fieldAccessList;
	}

	
	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getFieldAccessId() {
		return fieldAccessId;
	}

	public void setFieldAccessId(Integer fieldAccessId) {
		this.fieldAccessId = fieldAccessId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<DtoGrid> getGirdList() {
		return girdList;
	}

	public void setGirdList(List<DtoGrid> girdList) {
		this.girdList = girdList;
	}

	public List<DtoGridData> getGridDataList() {
		return gridDataList;
	}

	public void setGridDataList(List<DtoGridData> gridDataList) {
		this.gridDataList = gridDataList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public String getSideMenuCode() {
		return sideMenuCode;
	}

	public void setSideMenuCode(String sideMenuCode) {
		this.sideMenuCode = sideMenuCode;
	}

	public String getSideMenuUrl() {
		return sideMenuUrl;
	}

	public void setSideMenuUrl(String sideMenuUrl) {
		this.sideMenuUrl = sideMenuUrl;
	}

	
}

/**SmartSoftware User - Service */
/**
 * Description: DTO Module class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Module;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoModule {
	private String moduleNamePrimary;
	private String moduleNameSecondary;
	private Integer moduleId;
	private String moduleCode;
	private List<DtoScreenDetail> screensList;
	private List<DtoTransactionType> transactionTypeList;
	private List<DtoReportCategory> reportCategoryList;
	private DtoScreenDetail dtoScreenDetail;
	private DtoGrid dtoGridDetail;
	private String helpMessagePrimary;
	private String helpMessageSecondary;

	private Boolean viewAccess;
	private Boolean emailAccess;
	private Boolean exportAccess;
	private Boolean postAccess;
	private Boolean deleteAccess;
	private Boolean readAccess;
	private Boolean writeAccess;

	private Boolean isMandatory;

	private String moduleName;
	private String helpMessage;

	private String messageType;
	
	public DtoModule() {

	}

	public DtoModule(Module module) {
		this.moduleId = module.getModuleId();
		this.moduleCode = module.getModuleCode();
		if (UtilRandomKey.isNotBlank(module.getHelpMessage())) {
			this.helpMessage = module.getHelpMessage();
		} else {
			this.helpMessage = "";
		}
		if (UtilRandomKey.isNotBlank(module.getName())) {
			this.moduleName = module.getName();
		} else {
			this.moduleName = "";
		}
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public List<DtoScreenDetail> getScreensList() {
		return screensList;
	}

	public void setScreensList(List<DtoScreenDetail> screensList) {
		this.screensList = screensList;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
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

	public DtoScreenDetail getDtoScreenDetail() {
		return dtoScreenDetail;
	}

	public void setDtoScreenDetail(DtoScreenDetail dtoScreenDetail) {
		this.dtoScreenDetail = dtoScreenDetail;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public List<DtoTransactionType> getTransactionTypeList() {
		return transactionTypeList;
	}

	public void setTransactionTypeList(List<DtoTransactionType> transactionTypeList) {
		this.transactionTypeList = transactionTypeList;
	}

	public List<DtoReportCategory> getReportCategoryList() {
		return reportCategoryList;
	}

	public void setReportCategoryList(List<DtoReportCategory> reportCategoryList) {
		this.reportCategoryList = reportCategoryList;
	}

	public Boolean getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(Boolean viewAccess) {
		this.viewAccess = viewAccess;
	}

	public Boolean getEmailAccess() {
		return emailAccess;
	}

	public void setEmailAccess(Boolean emailAccess) {
		this.emailAccess = emailAccess;
	}

	public Boolean getExportAccess() {
		return exportAccess;
	}

	public void setExportAccess(Boolean exportAccess) {
		this.exportAccess = exportAccess;
	}

	public Boolean getPostAccess() {
		return postAccess;
	}

	public void setPostAccess(Boolean postAccess) {
		this.postAccess = postAccess;
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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
		
	public DtoGrid getDtoGridDetail() {
		return dtoGridDetail;
	}

	public void setDtoGridDetail(DtoGrid dtoGridDetail) {
		this.dtoGridDetail = dtoGridDetail;
	}

}

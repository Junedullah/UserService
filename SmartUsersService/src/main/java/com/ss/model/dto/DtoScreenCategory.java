/**SmartSoftware User - Service */
/**
 * Description: DTO Screen Category  class having getter and setter for fields (POJO) Name
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
public class DtoScreenCategory {

	private Integer screenCategoryId;
	private String screenCategoryName;
	private String screenCategoryCode;
	private Integer moduleId;
	private String helpMessagePrimary;
	private String helpMessageSecondary;
	private String helpMessage;
	private Integer pageNumber;
	private Integer pageSize;
	private List<DtoSideMenu> sideMenuList;

	public DtoScreenCategory() {
		
	}
	
	public Integer getScreenCategoryId() {
		return screenCategoryId;
	}

	public void setScreenCategoryId(Integer screenCategoryId) {
		this.screenCategoryId = screenCategoryId;
	}

	public String getScreenCategoryName() {
		return screenCategoryName;
	}

	public void setScreenCategoryName(String screenCategoryName) {
		this.screenCategoryName = screenCategoryName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
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

	public List<DtoSideMenu> getSideMenuList() {
		return sideMenuList;
	}

	public void setSideMenuList(List<DtoSideMenu> sideMenuList) {
		this.sideMenuList = sideMenuList;
	}

	public String getScreenCategoryCode() {
		return screenCategoryCode;
	}

	public void setScreenCategoryCode(String screenCategoryCode) {
		this.screenCategoryCode = screenCategoryCode;
	}

}

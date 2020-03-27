/**SmartSoftware User - Service */
/**
 * Description: Dto Grid class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Shahnawaz
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;
import java.util.Set;

public class DtoGrid {

	private Integer id;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer gridId;
	private String helpMessage;
	private List<DtoGrid> delete;
	private Set<DtoFieldDetail> fieldDetailList;
	private Integer moduleId;
	private Integer screenId;
	private List<Integer> ids;
	private String messageType;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getGridId() {
		return gridId;
	}
	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}
	public String getHelpMessage() {
		return helpMessage;
	}
	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}
	public List<DtoGrid> getDelete() {
		return delete;
	}
	public void setDelete(List<DtoGrid> delete) {
		this.delete = delete;
	}
	public Set<DtoFieldDetail> getFieldDetailList() {
		return fieldDetailList;
	}
	public void setFieldDetailList(Set<DtoFieldDetail> fieldDetailList) {
		this.fieldDetailList = fieldDetailList;
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

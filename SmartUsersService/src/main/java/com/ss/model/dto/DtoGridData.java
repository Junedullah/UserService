/**SmartSoftware User - Service */
/**
 * Description: Dto Grid Data class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.ss.model.GridData;

public class DtoGridData {

	private Integer pageNumber;
	private Integer pageSize;
	private Integer gridDataId;
	private Integer screenId;
	private List<DtoGrid> screen;
	private Integer moduleId;
	private Integer userId;
	private Integer fieldId;
	private Integer gridId;
	private Integer colOrder;
	private Boolean isVisible;
	private Boolean isReset;
	private String fieldName;
	private List<GridData> visible;
	private List<DtoGridData> columnList;
	private List<DtoGrid> resetGrid;
	private String messageType;

	
	private List<Integer> ids;

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

	public Integer getGridDataId() {
		return gridDataId;
	}

	public void setGridDataId(Integer gridDataId) {
		this.gridDataId = gridDataId;
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public List<GridData> getVisible() {
		return visible;
	}

	public void setVisible(List<GridData> visible) {
		this.visible = visible;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Boolean getIsReset() {
		return isReset;
	}

	public void setIsReset(Boolean isReset) {
		this.isReset = isReset;
	}

	public List<DtoGridData> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DtoGridData> columnList) {
		this.columnList = columnList;
	}

	public List<DtoGrid> getResetGrid() {
		return resetGrid;
	}

	public void setResetGrid(List<DtoGrid> resetGrid) {
		this.resetGrid = resetGrid;
	}

	public List<DtoGrid> getScreen() {
		return screen;
	}

	public void setScreen(List<DtoGrid> screen) {
		this.screen = screen;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	

}

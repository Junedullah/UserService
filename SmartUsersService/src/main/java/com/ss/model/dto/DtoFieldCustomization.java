/**SmartSoftware User - Service */
/**
 * Description: Dto Field Access Deatils class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Shahnawaz
 * Version: 
 */
package com.ss.model.dto;

public class DtoFieldCustomization {

	private int id;
	private int userId;
	private String code;
	private String fieldsToShow;
	private Integer pageNumber;
	private Integer pageSize;
	private String messageType;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFieldsToShow() {
		return fieldsToShow;
	}

	public void setFieldsToShow(String fieldsToShow) {
		this.fieldsToShow = fieldsToShow;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
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

	public void setId(int id) {
		this.id = id;
	}

	
}

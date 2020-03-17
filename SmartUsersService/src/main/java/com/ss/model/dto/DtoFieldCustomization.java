/**SmartSoftware User - Service */
/**
 * Description: Dto Field Access Deatils class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

public class DtoFieldCustomization {

	private int id;
	private int userId;
	private String code;
	private String fieldsToShow;

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

}

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 2:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoUserDetail {

	public Integer udId;
	private String address;
	private String countryCode;
	private Date dob;
	private String email;
	private String employeeCode;
	private String firstName;
	private String lastName;
	private String middleName;
	private String secondaryFirstName;
	private String secondaryLastName;
	private String secondaryMiddleName;
	private String phone;
	private String sessionDuration;
	private String zipcode;
	private String username;
	private String stateName;
	private String stateCode;
	private String countryName;
	private String cityName;
	private String cityCode;
	private String messageType;
	private String message;
	private Integer pageNumber;
	private Integer pageSize;

	public DtoUserDetail() {

	}

	public Integer getUdId() {
		return udId;
	}

	public void setUdId(Integer udId) {
		this.udId = udId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSecondaryFirstName() {
		return secondaryFirstName;
	}

	public void setSecondaryFirstName(String secondaryFirstName) {
		this.secondaryFirstName = secondaryFirstName;
	}

	public String getSecondaryLastName() {
		return secondaryLastName;
	}

	public void setSecondaryLastName(String secondaryLastName) {
		this.secondaryLastName = secondaryLastName;
	}

	public String getSecondaryMiddleName() {
		return secondaryMiddleName;
	}

	public void setSecondaryMiddleName(String secondaryMiddleName) {
		this.secondaryMiddleName = secondaryMiddleName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(String sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

}

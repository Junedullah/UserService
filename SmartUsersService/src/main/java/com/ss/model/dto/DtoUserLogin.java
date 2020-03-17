/**
 * SST - SMART SOFTWARE TECH.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Description: DTO User Login class having getter and setter for fields (POJO) Name 
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoUserLogin {

	private Integer userId;
	private String otp;
	private String phone;
	private Integer wrongAttempts;
	private Boolean deleted;
	private Boolean otpExpired;
	private Boolean otpMatched;
	private Boolean otpMaxLimitReached;
	private String smsAuthentication;
	private String isResetPassword;
	private String messageType;
	private String role;
	private String ipAddress;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean isOtpExpired() {
		return otpExpired;
	}

	public void setOtpExpired(Boolean otpExpired) {
		this.otpExpired = otpExpired;
	}

	public Boolean isOtpMatched() {
		return otpMatched;
	}

	public void setOtpMatched(Boolean otpMatched) {
		this.otpMatched = otpMatched;
	}

	public Boolean isOtpMaxLimitReached() {
		return otpMaxLimitReached;
	}

	public void setOtpMaxLimitReached(Boolean otpMaxLimitReached) {
		this.otpMaxLimitReached = otpMaxLimitReached;
	}

	public Integer getWrongAttempts() {
		return wrongAttempts;
	}

	public void setWrongAttempts(Integer wrongAttempts) {
		this.wrongAttempts = wrongAttempts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsAuthentication() {
		return smsAuthentication;
	}

	public void setSmsAuthentication(String smsAuthentication) {
		this.smsAuthentication = smsAuthentication;
	}

	public String getIsResetPassword() {
		return isResetPassword;
	}

	public void setIsResetPassword(String isResetPassword) {
		this.isResetPassword = isResetPassword;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	

}

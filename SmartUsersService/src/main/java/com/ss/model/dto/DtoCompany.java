/**
 * BTI - BAAN for Technology And Trade IntL. 
 * Copyright @ 2017 BTI. 
 * 
 * All rights reserved.
 * 
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF BTI. 
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE 
 * PRIOR EXPRESS WRITTEN PERMISSION OF BTI.
 */
package com.ss.model.dto;

import java.util.List;

//import com.bti.model.Company;
//import com.bti.util.UtilRandomKey;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Company;
import com.ss.util.UtilRandomKey;

/*SmartSoftware User - Service */
/**
 * Description: The persistent class for the country_master database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoCompany {

	private Integer id;
	private String name;
	private String description;
	private String address;
	private Boolean isActive;
	private List<Integer> ids;
	private DtoUserGroup userGroup;
	private String state;
	private String city;
	private String country;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private String fax;
	private String email;
	private String phone;
	private String zipCode;
	private String companyCode;
	private String tenantId;
	private String countryCode;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer userGroupId;
	private Integer companyId;
	private String totalUsers;
	private String messageType;
	private String message;
	private List<DtoCompany> associateCompanies;
	private List<DtoCompany> deleteCompanies;
	private String associateMessage;
	private String deleteMessage;
	private String companyStatus;
	private String value;
	private String webAddress;
	private Double latitude;
	private Double longitude;
	private String databaseIP;
	private String port;
	private String username;
	private String password;
	private Integer reportMasterId;
	private String reportName;
	private String reportLink;
	//private List<DtoReportMaster> dtoReportMaster;
	private String sortOn;
	private String sortBy;
	private Integer totalCount;
	private Object records;
	private List<Integer> reportIds;
	
	public DtoCompany() {

	}

	
	  public DtoCompany(Company company) { this.id = company.getCompanyId();
	  this.name = UtilRandomKey.isNotBlank(company.getName())?company.getName():"";
	  this.address =
	  UtilRandomKey.isNotBlank(company.getAddress())?company.getAddress():"";
	  this.fax = UtilRandomKey.isNotBlank(company.getFax())?company.getFax():"";
	  this.phone =
	  UtilRandomKey.isNotBlank(company.getPhone())?company.getPhone():"";
	  this.email =
	  UtilRandomKey.isNotBlank(company.getEmail())?company.getEmail():"";
	  this.countryCode =
	  UtilRandomKey.isNotBlank(company.getCountryCode())?company.getCountryCode():
	  ""; this.zipCode =
	  UtilRandomKey.isNotBlank(company.getZipcode())?company.getZipcode():"";
	  this.state = company.getStateMaster() !=
	  null?company.getStateMaster().getStateName():""; this.stateId =
	  company.getStateMaster() != null?company.getStateMaster().getStateId():null;
	  this.city = company.getCityMaster() !=
	  null?company.getCityMaster().getCityName():""; this.cityId =
	  company.getCityMaster() != null?company.getCityMaster().getCityId():null;
	  this.country = company.getCountryMaster() !=
	  null?company.getCountryMaster().getCountryName():""; this.countryId =
	  company.getCountryMaster() !=
	  null?company.getCountryMaster().getCountryId():null; this.webAddress =
	  UtilRandomKey.isNotBlank(company.getWebAddress())?company.getWebAddress():"";
	  this.latitude = company.getLatitude() != null?company.getLatitude():0.0;
	  this.longitude = company.getLongitude() != null?company.getLongitude():0.0;
	  this.tenantId =
	  UtilRandomKey.isNotBlank(company.getTenantId())?company.getTenantId():"";
	  this.isActive = company.getIsActive(); this.companyCode =
	  company.getCompanyCode(); }
	 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public DtoUserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(DtoUserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(String totalUsers) {
		this.totalUsers = totalUsers;
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

	public List<DtoCompany> getAssociateCompanies() {
		return associateCompanies;
	}

	public void setAssociateCompanies(List<DtoCompany> associateCompanies) {
		this.associateCompanies = associateCompanies;
	}

	public List<DtoCompany> getDeleteCompanies() {
		return deleteCompanies;
	}

	public void setDeleteCompanies(List<DtoCompany> deleteCompanies) {
		this.deleteCompanies = deleteCompanies;
	}

	public String getAssociateMessage() {
		return associateMessage;
	}

	public void setAssociateMessage(String associateMessage) {
		this.associateMessage = associateMessage;
	}

	public String getDeleteMessage() {
		return deleteMessage;
	}

	public void setDeleteMessage(String deleteMessage) {
		this.deleteMessage = deleteMessage;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDatabaseIP() {
		return databaseIP;
	}

	public void setDatabaseIP(String databaseIP) {
		this.databaseIP = databaseIP;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getReportMasterId() {
		return reportMasterId;
	}

	public void setReportMasterId(Integer reportMasterId) {
		this.reportMasterId = reportMasterId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportLink() {
		return reportLink;
	}

	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}

//	public List<DtoReportMaster> getDtoReportMaster() {
//		return dtoReportMaster;
//	}
//
//	public void setDtoReportMaster(List<DtoReportMaster> dtoReportMaster) {
//		this.dtoReportMaster = dtoReportMaster;
//	}

	public String getSortOn() {
		return sortOn;
	}

	public void setSortOn(String sortOn) {
		this.sortOn = sortOn;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Object getRecords() {
		return records;
	}

	public void setRecords(Object records) {
		this.records = records;
	}

	public List<Integer> getReportIds() {
		return reportIds;
	}

	public void setReportIds(List<Integer> reportIds) {
		this.reportIds = reportIds;
	}
	
}

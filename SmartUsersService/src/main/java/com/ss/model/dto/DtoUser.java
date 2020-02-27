package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.User;
import com.ss.model.UserDetail;
import com.ss.util.UtilDateAndTime;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoUser {
	
	private Integer id;
	private Integer userId;
	private Integer userDetailId;
	private String userGroupId;
	private String firstName;
	private String lastName;
	private String phone;
	private String userName;
	private String email;
	private String employeeCode;
	private String role;
	private String roleId;
	private String selectRoleGroup;
	private String selectUserGroup;
	private String selectCompanies;
	private String password;
	private String middleName;
	/*private List<DtoRoleGroup> listOfRoleGroup;
	private List<DtoUserGroup> listOfUserGroup;
	private List<DtoCompany> listOfCompanies;
	private List<DtoReportMaster> dtoReportMaster;
	private DtoUserGroup userGroup;	*/
	private Boolean deleted;
	private String session;
	private String country;
	private String dob;
	private String city;
	private String state;
	private String zipCode;
	private String address;
	private List<Integer> userIds;
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
	private String countryCode;
	private Boolean isActive;
	private String otp;
	private Integer wrongAttempts;
	private Boolean otpExpired;
	private Boolean otpMatched;
	private Boolean otpMaxLimitReached;
	private String smsAuthentication;
	private String isResetPassword;
	private String oldPassword;
	private String secondaryFirstName;
	private String secondaryMiddleName;
	private String secondaryLastName;
	private List<Integer> companyIds;
	private List<String> companyNames;
	private String secondaryFullName;
	private String primaryFullName;
	private String userGroupName;
	private String messageType;
	private String userStatus;
	private List<String> macAddressList;
	private String macAddress;
	private String deviceType;
	private String deviceDescription;
	private String fullName;
	private Boolean ipChecked;
	private Integer companyCount;
	private Integer companyId;
	private String companyTenantId;
	private String sessionExpireDate;
	private String ipAddress;
	private Integer reportMasterId;
	private String reportName;
	private String reportLink;
	private String sortOn;
	private String sortBy;
	private Integer totalCount;
	private Object records;
	private List<Integer> reportIds;
	
	
	public DtoUser() {
		super();
	}
	/**
	 * @param user
	 * @param userDetail
	 * @param langId
	 */
	public DtoUser(User user, UserDetail userDetail, String langId) {
		this.userId = user.getUserId();
		this.userName = user.getUsername();
		this.email = user.getEmail();
		if (user.isResetPassword()) {
			this.isResetPassword = "Y";
		} else {
			this.isResetPassword = "N";
		}

		this.isActive = user.isActive();

		if (userDetail != null) {
			this.userDetailId = userDetail.getUdId();

			if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
				this.primaryFullName = userDetail.getFirstName();

				if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
					this.primaryFullName = userDetail.getFirstName() + " " + userDetail.getMiddleName();
				}

				if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
					this.primaryFullName += " " + userDetail.getLastName();
				}
			} else {
				this.primaryFullName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
				this.secondaryFullName = userDetail.getSecondaryFirstName();

				if (UtilRandomKey.isNotBlank(userDetail.getSecondaryMiddleName())) {
					this.secondaryFullName += " " + userDetail.getSecondaryMiddleName();
				}

				if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
					this.secondaryFullName += " " + userDetail.getSecondaryLastName();
				}
			} else {
				this.secondaryFullName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
				this.firstName = userDetail.getFirstName();

			} else {
				this.firstName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
				this.lastName = userDetail.getLastName();

			} else {
				this.lastName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
				this.middleName = userDetail.getMiddleName();

			} else {
				this.middleName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
				this.secondaryFirstName = userDetail.getSecondaryFirstName();

			} else {
				this.secondaryFirstName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
				this.secondaryLastName = userDetail.getSecondaryLastName();

			} else {
				this.secondaryLastName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryMiddleName())) {
				this.secondaryMiddleName = userDetail.getSecondaryMiddleName();

			} else {
				this.secondaryMiddleName = "";
			}

			if (userDetail.getDob() != null) {
				this.dob = UtilDateAndTime.dateToStringddmmyyyy(userDetail.getDob());

			} else {
				this.dob = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getZipcode())) {
				this.zipCode = userDetail.getZipcode();

			} else {
				this.zipCode = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getAddress())) {
				this.address = userDetail.getAddress();

			} else {
				this.address = "";
			}

			this.phone = userDetail.getPhone();

			if (UtilRandomKey.isNotBlank(userDetail.getCountryCode())) {
				this.countryCode = userDetail.getCountryCode();
			} else {
				this.countryCode = "";
			}

			/*if (userDetail.getStateMaster() != null) {
				this.state = userDetail.getStateMaster().getStateName();
				this.stateId = userDetail.getStateMaster().getStateId();
			} else {
				this.state = "";
			}

			if (userDetail.getCityMaster() != null) {
				this.city = userDetail.getCityMaster().getCityName();
				this.cityId = userDetail.getCityMaster().getCityId();
			} else {
				this.city = "";
			}

			if (userDetail.getCountryMaster() != null) {
				this.country = userDetail.getCountryMaster().getCountryName();
				this.countryId = userDetail.getCountryMaster().getCountryId();
			} else {
				this.country = "";
			}*/
		}
	}
	/**
	 * @param userDetail
	 * @param user
	 * @param langId
	 */
	public DtoUser(UserDetail userDetail, User user, String langId) {
		this.userId = user.getUserId();
		this.email = user.getEmail();
		if (userDetail != null) {
			this.userDetailId = userDetail.getUdId();

			if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
				this.primaryFullName = userDetail.getFirstName();

				if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
					this.primaryFullName = userDetail.getFirstName() + " " + userDetail.getMiddleName();
				}

				if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
					this.primaryFullName += " " + userDetail.getLastName();
				}

			} else {
				this.primaryFullName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
				this.secondaryFullName = userDetail.getSecondaryFirstName();

				if (UtilRandomKey.isNotBlank(userDetail.getSecondaryMiddleName())) {
					this.secondaryFullName = userDetail.getSecondaryMiddleName() + " "
							+ userDetail.getSecondaryMiddleName();
				}

				if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
					this.secondaryFullName += " " + userDetail.getSecondaryLastName();
				}
			} else {
				this.secondaryFullName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
				this.firstName = userDetail.getFirstName();

			} else {
				this.firstName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
				this.lastName = userDetail.getLastName();

			} else {
				this.lastName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
				this.middleName = userDetail.getMiddleName();

			} else {
				this.middleName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
				this.secondaryFirstName = userDetail.getSecondaryFirstName();

			} else {
				this.secondaryFirstName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
				this.secondaryLastName = userDetail.getSecondaryLastName();

			} else {
				this.secondaryLastName = "";
			}

			if (UtilRandomKey.isNotBlank(userDetail.getSecondaryMiddleName())) {
				this.secondaryMiddleName = userDetail.getSecondaryMiddleName();

			} else {
				this.secondaryMiddleName = "";
			}

		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserDetailId() {
		return userDetailId;
	}
	public void setUserDetailId(Integer userDetailId) {
		this.userDetailId = userDetailId;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getSelectRoleGroup() {
		return selectRoleGroup;
	}
	public void setSelectRoleGroup(String selectRoleGroup) {
		this.selectRoleGroup = selectRoleGroup;
	}
	public String getSelectUserGroup() {
		return selectUserGroup;
	}
	public void setSelectUserGroup(String selectUserGroup) {
		this.selectUserGroup = selectUserGroup;
	}
	public String getSelectCompanies() {
		return selectCompanies;
	}
	public void setSelectCompanies(String selectCompanies) {
		this.selectCompanies = selectCompanies;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Integer> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Integer getWrongAttempts() {
		return wrongAttempts;
	}
	public void setWrongAttempts(Integer wrongAttempts) {
		this.wrongAttempts = wrongAttempts;
	}
	public Boolean getOtpExpired() {
		return otpExpired;
	}
	public void setOtpExpired(Boolean otpExpired) {
		this.otpExpired = otpExpired;
	}
	public Boolean getOtpMatched() {
		return otpMatched;
	}
	public void setOtpMatched(Boolean otpMatched) {
		this.otpMatched = otpMatched;
	}
	public Boolean getOtpMaxLimitReached() {
		return otpMaxLimitReached;
	}
	public void setOtpMaxLimitReached(Boolean otpMaxLimitReached) {
		this.otpMaxLimitReached = otpMaxLimitReached;
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
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getSecondaryFirstName() {
		return secondaryFirstName;
	}
	public void setSecondaryFirstName(String secondaryFirstName) {
		this.secondaryFirstName = secondaryFirstName;
	}
	public String getSecondaryMiddleName() {
		return secondaryMiddleName;
	}
	public void setSecondaryMiddleName(String secondaryMiddleName) {
		this.secondaryMiddleName = secondaryMiddleName;
	}
	public String getSecondaryLastName() {
		return secondaryLastName;
	}
	public void setSecondaryLastName(String secondaryLastName) {
		this.secondaryLastName = secondaryLastName;
	}
	public List<Integer> getCompanyIds() {
		return companyIds;
	}
	public void setCompanyIds(List<Integer> companyIds) {
		this.companyIds = companyIds;
	}
	public List<String> getCompanyNames() {
		return companyNames;
	}
	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}
	public String getSecondaryFullName() {
		return secondaryFullName;
	}
	public void setSecondaryFullName(String secondaryFullName) {
		this.secondaryFullName = secondaryFullName;
	}
	public String getPrimaryFullName() {
		return primaryFullName;
	}
	public void setPrimaryFullName(String primaryFullName) {
		this.primaryFullName = primaryFullName;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public List<String> getMacAddressList() {
		return macAddressList;
	}
	public void setMacAddressList(List<String> macAddressList) {
		this.macAddressList = macAddressList;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceDescription() {
		return deviceDescription;
	}
	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Boolean getIpChecked() {
		return ipChecked;
	}
	public void setIpChecked(Boolean ipChecked) {
		this.ipChecked = ipChecked;
	}
	public Integer getCompanyCount() {
		return companyCount;
	}
	public void setCompanyCount(Integer companyCount) {
		this.companyCount = companyCount;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyTenantId() {
		return companyTenantId;
	}
	public void setCompanyTenantId(String companyTenantId) {
		this.companyTenantId = companyTenantId;
	}
	public String getSessionExpireDate() {
		return sessionExpireDate;
	}
	public void setSessionExpireDate(String sessionExpireDate) {
		this.sessionExpireDate = sessionExpireDate;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

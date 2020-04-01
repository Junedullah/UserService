/**SmartSoftware User - Service */
package com.ss.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ss.constant.SmartCodeType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.authetication.SessionManager;
import com.ss.constant.ConfigSetting;
import com.ss.constant.SmartRoles;
import com.ss.model.AppConfigSetting;
import com.ss.model.AuthorizationSetting;
import com.ss.model.Company;
import com.ss.model.LoginOtp;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserDetail;
import com.ss.model.UserGroup;
import com.ss.model.UserMacAddress;
import com.ss.model.UserSession;
import com.ss.model.WhitelistIp;
import com.ss.model.dto.DtoAuthorizationDetail;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.model.dto.DtoUserDetail;
import com.ss.model.dto.DtoUserGroup;
import com.ss.model.dto.DtoUserIp;
import com.ss.repository.RepositoryAccessRoleModuleRelation;
import com.ss.repository.RepositoryAppConfigSetting;
import com.ss.repository.RepositoryAuthorizationSetting;
import com.ss.repository.RepositoryCityMaster;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryCountryMaster;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryLoginOtp;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryRole;
import com.ss.repository.RepositoryRoleGroup;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryScreenCategory;
import com.ss.repository.RepositoryStateMaster;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserDetailPagination;
import com.ss.repository.RepositoryUserGroup;
import com.ss.repository.RepositoryUserMacAddress;
import com.ss.repository.RepositoryUserSession;
import com.ss.repository.RepositoryWhiteListIp;
import com.ss.util.CLXSMSUtility;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilDateAndTime;
import com.ss.util.UtilRandomKey;





@Service("serviceUser")
public class ServiceUser {

	static Logger log = Logger.getLogger(ServiceUser.class.getName());
	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	RepositoryRole repositoryRole;

	@Autowired
	RepositoryUserGroup repositoryUserGroup;

	@Autowired
	RepositoryUserDetailPagination repositoryUserDetailPagination;

	@Autowired
	RepositoryRoleGroup repositoryRoleGroup;

	/*@Autowired 	
	RepositoryScreenMenu repositoryScreenMenu;*/
	 

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired(required = false)
	HttpSession httpSession;

	@Autowired
	PasswordEncoder passwordEncoder;


	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;

	@Autowired
	RepositoryCountryMaster repositoryCountryMaster;

	@Autowired
	RepositoryCityMaster repositoryCityMaster;

	@Autowired
	RepositoryStateMaster repositoryStateMaster;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	RepositoryUserSession repositoryUserSession;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanies;


	@Autowired
	ServiceEmailHandler serviceEmailHandler;


	@Autowired
	RepositoryCompany repositoryCompany;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired
	RepositoryAuthorizationSetting repositoryAuthorizationSetting;

	@Autowired
	RepositoryException repositoryException;

	@Autowired 
	RepositoryAppConfigSetting repositoryAppConfigSetting;

	@Autowired 
	RepositoryWhiteListIp repositoryWhiteListIp;


	@Autowired
	SessionManager sessionManager;

	
	/*@Autowired
	CLXSMSUtility cLXSMSUtility;
*/
	@Autowired
	RepositoryUserMacAddress repositoryUserMacAddress;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;

	@Autowired
	RepositoryAccessRoleModuleRelation repositoryAccessRoleModuleRelation;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryModule repositoryModule;	


	@Autowired
	RepositoryScreenCategory repositoryScreenCategory;

	private static final String LANG_ID = "langid";
	private static final String USER_ID ="userid";
	private static final String COMPANY_TENANT_ID = "tenantid";
	/**
	 * Description: Save User Company Relation List 
	 * @param userCompaniesList
	 * @param dtoUser
	 * @return
	 */
	public DtoUser setUserCompanyRelationListInDto(List<UserCompanyRelation> userCompaniesList, DtoUser dtoUser) {
		String userGroupName = "";
		List<DtoCompany> listOfCompanies = new ArrayList<>();
		List<Integer> companyIds = new ArrayList<>();
		List<String> companyNames = new ArrayList<>();
		if (userCompaniesList != null && !userCompaniesList.isEmpty()) {
			for (UserCompanyRelation userCompaniesRelation : userCompaniesList) {
				Company company = userCompaniesRelation.getCompany();
				if (company != null) {
					companyIds.add(company.getCompanyId());
					companyNames.add(company.getName());
					DtoCompany dtoCompany = new DtoCompany();
					dtoCompany.setId(company.getCompanyId());
					dtoCompany.setName(company.getName());
					dtoCompany.setIsActive(company.getIsActive());
					DtoUserGroup userGroup = new DtoUserGroup();
					if (userCompaniesRelation.getUserGroup() != null) {
						UserGroup userGroup2 = userCompaniesRelation.getUserGroup();
						if (userGroup2 != null) {
							userGroup.setId(userGroup2.getUserGroupId());
							userGroup.setGroupCode(userGroup2.getGroupCode());
							if (UtilRandomKey.isNotBlank(userGroup2.getGroupDesc())) {
								userGroup.setGroupDesc(userGroup2.getGroupDesc());
							} else {
								userGroup.setGroupDesc("");
							}

							if (UtilRandomKey.isNotBlank(userGroup2.getGroupName())) {
								userGroup.setGroupName(userGroup2.getGroupName());
								userGroupName = userGroup2.getGroupName();
							} else {
								userGroup.setGroupName("");
							}

						}
					}
					dtoCompany.setUserGroup(userGroup);
					listOfCompanies.add(dtoCompany);
				}
			}
		}
		dtoUser.setListOfCompanies(listOfCompanies);
		dtoUser.setCompanyIds(companyIds);
		dtoUser.setCompanyNames(companyNames);
		dtoUser.setUserGroupName(userGroupName);
		return dtoUser;
	}



	/**
	 * Description: get user List 
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getUsersList(DtoSearch dtoSearch) {
		String langId = httpServletRequest.getHeader(LANG_ID);
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryUser.getCountOfTotalUsers());
		List<User> usersList = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC,
					"createdDate");
			usersList = repositoryUser.findByRoleRoleIdAndIsDeleted(SmartRoles.USER.getIndex(), false, pageable);
		} else {
			usersList = repositoryUser.findByRoleRoleIdAndIsDeletedOrderByCreatedDateDesc(SmartRoles.USER.getIndex(),
					false);
		}

		List<DtoUser> dtoList = new ArrayList<>();
		if (usersList != null && !usersList.isEmpty()) {
			for (User user : usersList) {
				UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				if (userDetail != null) {
					List<UserCompanyRelation> userCompanyList =repositoryUserCompanies.findByUserUserIdAndIsDeleted(user.getUserId(), false);
					DtoUser dtoUser = new DtoUser(user, userDetail, langId);
					dtoUser = setUserCompanyRelationListInDto(userCompanyList, dtoUser);
					if (dtoUser.getIsActive()) {
						dtoUser.setUserStatus(
								serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
					} else {
						dtoUser.setUserStatus(
								serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
					}
					dtoUser.setMobile(user.getUserDetails().get(0).getMobile());
					dtoList.add(dtoUser);
				}
			}

		}

		dtoSearch2.setRecords(dtoList);
		return dtoSearch2;
	}

	/**
	 * Description: get user list for drop down
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getUsersListForDropDown(DtoSearch dtoSearch) {
		String langId = httpServletRequest.getHeader(LANG_ID);
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryUser.getCountOfTotalUsers());
		List<User> usersList = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			usersList = repositoryUser.findByRoleRoleIdAndIsDeletedAndIsActive(SmartRoles.USER.getIndex(), false,
					pageable, true);
		} else {
			usersList = repositoryUser.findByRoleRoleIdAndIsDeletedAndIsActive(SmartRoles.USER.getIndex(), false, true);
		}

		List<DtoUser> dtoList = new ArrayList<>();
		if (usersList != null && !usersList.isEmpty()) {
			for (User user : usersList) {
				DtoUser dtoUser = new DtoUser();
				dtoUser.setId(user.getUserId());
				UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				if (userDetail != null) {
					if (ConfigSetting.PRIMARY.getValue().equalsIgnoreCase(langId)) {
						if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
							dtoUser.setUserName(userDetail.getFirstName());
							if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
								dtoUser.setUserName(userDetail.getFirstName() + " " + userDetail.getLastName());
							}
						} else {
							dtoUser.setUserName("N/A");
						}
					}/* else if (ConfigSetting.SECONDARY.getValue().equalsIgnoreCase(langId)) {
						if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
							dtoUser.setUserName(userDetail.getSecondaryFirstName());
							if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
								dtoUser.setUserName(
										userDetail.getSecondaryFirstName() + " " + userDetail.getSecondaryLastName());
							}
						} else {
							dtoUser.setUserName("N/A");
						}
					}*/
					dtoList.add(dtoUser);
				}
			}
		}
		dtoSearch2.setRecords(dtoList);
		return dtoSearch2;
	}

	public DtoSearch getAllUsersList(DtoSearch dtoSearch) 
	{
		String langId = httpServletRequest.getHeader(LANG_ID);
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryUser.getCountOfTotalUsers());
		List<User> usersList = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			usersList = repositoryUser.findByIsDeleted(false,
					pageable);
		} else {
			usersList = repositoryUser.findByIsDeleted(false);
		}

		List<DtoUser> dtoList = new ArrayList<>();
		if (usersList != null && !usersList.isEmpty()) {
			for (User user : usersList) {
				DtoUser dtoUser = new DtoUser();
				dtoUser.setId(user.getUserId());
				UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				if (userDetail != null) {
					if (ConfigSetting.PRIMARY.getValue().equalsIgnoreCase(langId)) {
						if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
							dtoUser.setUserName(userDetail.getFirstName());
							if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
								dtoUser.setUserName(userDetail.getFirstName() + " " + userDetail.getLastName());
							}
						} else {
							dtoUser.setUserName("N/A");
						}
					} /*else if (ConfigSetting.SECONDARY.getValue().equalsIgnoreCase(langId)) {
						if (UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())) {
							dtoUser.setUserName(userDetail.getSecondaryFirstName());
							if (UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())) {
								dtoUser.setUserName(
										userDetail.getSecondaryFirstName() + " " + userDetail.getSecondaryLastName());
							}
						} else {
							dtoUser.setUserName("N/A");
						}
					}*/
					dtoList.add(dtoUser);
				}
			}
		}
		dtoSearch2.setRecords(dtoList);
		return dtoSearch2;
	}

	/**
	 * Description: get User detail by user id 
	 * @param dtoUser2
	 * @return
	 */
	public DtoUser getUsersDetailByUserId(DtoUser dtoUser2) {
		String langId = httpServletRequest.getHeader(LANG_ID);
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser2.getUserId(), false);
		if (user != null) {
			UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
			if (userDetail != null) {
				List<UserCompanyRelation> userCompanyList = repositoryUserCompanies
						.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				DtoUser dtoUser = new DtoUser(user, userDetail, langId);
				dtoUser.setMobile(user.getUserDetails().get(0).getMobile());
				dtoUser = setUserCompanyRelationListInDto(userCompanyList, dtoUser);
				return dtoUser;
			}

		}
		return null;
	}

	/**
	 * Description: get User detail by user id 
	 * @param userId
	 * @return
	 */
	public DtoUser getUserDetailByid(int userId) {
		String langId = httpServletRequest.getHeader(LANG_ID);
		User user = repositoryUser.findByUserIdAndIsDeleted(userId, false);
		DtoUser dtoUser = new DtoUser();
		if (user != null) {
			UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
			if (userDetail != null) {
				List<UserCompanyRelation> userCompaniesList = repositoryUserCompanies
						.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				dtoUser = new DtoUser(user, userDetail, langId);
				dtoUser = setUserCompanyRelationListInDto(userCompaniesList, dtoUser);
			}
		}
		return dtoUser;
	}

	/**
	 * @return
	 *//*
	public List<UserGroup> getUserGroupsList() {
		return repositoryUserGroup.findByIsDeleted(false);
	}

	  *//**
	  * @return
	  */
	public List<DtoUserDetail> getUserDetailList() {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoUserDetail> dtoUserDetails = new ArrayList<>();
		try {
			List<UserDetail> userDetailList = repositoryUserDetail.findByIsDeleted(false);
			if (userDetailList != null && userDetailList.size() > 0) {
				for (UserDetail userDetail : userDetailList) {
					DtoUserDetail dtoUserDetail = new DtoUserDetail();
					dtoUserDetail.setFirstName(userDetail.getFirstName());
					dtoUserDetail.setLastName(userDetail.getLastName());
					dtoUserDetail.setDob(userDetail.getDob());
					dtoUserDetail.setAddress(userDetail.getAddress());
					dtoUserDetail.setUdId(userDetail.getUdId());
					dtoUserDetail.setCountryCode(userDetail.getCountryCode());
					dtoUserDetail.setCountryName(userDetail.getCountryMaster().getCountryName());
					dtoUserDetail.setStateCode(userDetail.getStateMaster().getStateCode());
					dtoUserDetail.setStateName(userDetail.getStateMaster().getStateName());
					dtoUserDetail.setCityCode(userDetail.getCityMaster().getCityCode());
					dtoUserDetail.setCityName(userDetail.getCityMaster().getCityName());
					dtoUserDetail.setEmail(userDetail.getEmail());
					dtoUserDetail.setEmployeeCode(userDetail.getEmployeeCode());
					dtoUserDetails.add(dtoUserDetail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoUserDetails;
	}



	public DtoSearch getActiveSessionUsersList(DtoSearch dtoSearch, UserSession currenUserSession) 
	{
		String langId = httpServletRequest.getHeader(LANG_ID);
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		List<UserSession> userSessionList = new ArrayList<>();
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			Page<UserSession> pageSession  = repositoryUserSession.findAll(pageable);
			if(pageSession!=null && !pageSession.getContent().isEmpty()){
				userSessionList=pageSession.getContent();
			}
		} else {
			userSessionList =repositoryUserSession.findAll();

		}

		List<DtoUser> dtoList = new ArrayList<>();
		if (userSessionList != null && !userSessionList.isEmpty()) {
			for (UserSession userSession : userSessionList) 
			{
				if(currenUserSession.getSessionId()!=userSession.getSessionId())
				{
					UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(userSession.getUser().getUserId(), false);
					if (userDetail != null) 
					{
						DtoUser dtoUser = new DtoUser(userSession.getUser(), userDetail, langId);
						if (dtoUser.getIsActive()) {
							dtoUser.setUserStatus(
									serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
						} else {
							dtoUser.setUserStatus(
									serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
						}
						dtoUser.setSession(userSession.getSession());
						dtoUser.setSessionExpireDate("");
						if(userSession.getExpireDate()!=null){
							dtoUser.setSessionExpireDate(UtilDateAndTime.dateToStringddmmyyyy(userSession.getExpireDate()));
						}
						dtoUser.setCompanyTenantId("");
						if(UtilRandomKey.isNotBlank(userSession.getCompnayTenantId())){
							dtoUser.setCompanyTenantId(userSession.getCompnayTenantId());
						}
						dtoList.add(dtoUser);
					}
				}
			}
		}
		dtoSearch2.setTotalCount(dtoList.size());
		dtoSearch2.setRecords(dtoList);
		return dtoSearch2;
	}

	/**
	 * This methods blocks/unblocks user
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoUser blockUnblockUser(DtoUser dtoUser) {
		User user = this.repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
		if (user != null) {
			user.setActive(dtoUser.getIsActive());
			this.repositoryUser.saveAndFlush(user);
		}
		return dtoUser;
	}


	/**
	 * @param userm
	 */
	public void resetPassword(User user) {
		String randomPassword = UtilRandomKey.getRandomOrderNumber();
		user.setPassword(passwordEncoder.encode(randomPassword));
		//user.setResetPassword(true);
		repositoryUser.saveAndFlush(user);
		serviceEmailHandler.sendResetPasswordMailByAdmin(user.getEmail(), randomPassword, user.getUsername());
	}

	/**
	 * @param password
	 * @param userExist
	 * @return
	 */
	public boolean changeUserPasswordAndSendEmail(String password, User userExist) {
		if (userExist != null && userExist.getEmail() != null) {
			userExist.setPassword(passwordEncoder.encode(password));

			repositoryUser.saveAndFlush(userExist);
			try {
				serviceEmailHandler.sendChangeUserPasswordEmail(userExist.getEmail(), password, userExist.getUsername());
			} catch (Exception ex) {
				ex.printStackTrace();
				log.debug(ex);
			}
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Description: get admin profile detail
	 * @param dtoUser2
	 * @return
	 */
	public DtoUser getAdminProfileDetail(DtoUser dtoUser2) 
	{
		String langId = httpServletRequest.getHeader(LANG_ID);
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser2.getUserId(), false);
		if (user != null) {
			UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
			if (userDetail != null) {
				return new DtoUser(userDetail, user, langId);
			}
		}
		return null;
	}


	/**
	 * Description: update admin profile
	 * @param dtoUser
	 * @return
	 */
	public String[] updateAdminProfile(DtoUser dtoUser) 
	{
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		User user = null;
		try 
		{
			user = repositoryUser.findByUserId(dtoUser.getUserId());
			UserDetail userDetail = null;
			if (user != null) 
			{
				if (UtilRandomKey.isNotBlank(dtoUser.getPassword())) 
				{
					user.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
				}
				user.setUpdatedBy(loggedInUserId);
				user = repositoryUser.saveAndFlush(user);
				userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				if (userDetail != null) 
				{
					userDetail.setUpdatedBy(loggedInUserId);
					userDetail.setFirstName(dtoUser.getFirstName());
					userDetail.setLastName(dtoUser.getLastName());
					userDetail.setMiddleName(dtoUser.getMiddleName());
					/*userDetail.setSecondaryFirstName(dtoUser.getSecondaryFirstName());
					userDetail.setSecondaryLastName(dtoUser.getSecondaryLastName());
					userDetail.setSecondaryMiddleName(dtoUser.getSecondaryMiddleName());*/
					userDetail.setUser(user);
					repositoryUserDetail.saveAndFlush(userDetail);
				}
				return new String[] { "success", String.valueOf(user.getUserId()) };
			}
			return new String[] { "error", String.valueOf(dtoUser.getUserId()) };
		}
		catch (Exception e) 
		{
			log.info(Arrays.toString(e.getStackTrace()));
			return new String[] { e.getMessage(), String.valueOf(dtoUser.getUserId()) };
		}
	}





	/**
	 * Description: get User detail by user id 
	 * @param dtoUser2
	 * @return
	 *//**
	 * Description: get User detail by user id 
	 * @param userId
	 * @return
	 *//*
	public List<RoleGroup> getRoleGroupsList() {
		return repositoryRoleGroup.findByIsDeleted(false);
	}

	  *//**
	  * Description: save and update user 
	  * @param dtoUser
	  * @return
	  */
	public String[] saveorUpdateUser(DtoUser dtoUser) 
	{
		boolean newUser=false;
		String randomPasssword = UtilRandomKey.getRandomOrderNumber();
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		User user = null;
		UserDetail userDetail = null;

		//String strDate = dateFormat.format(date);
		try {

			if (dtoUser.getUserId() != null) {
				user = repositoryUser.findByUserId(dtoUser.getUserId());
			}

			if (user == null) 
			{
				newUser=true;
				user = new User();
				user.setUsername(dtoUser.getEmail());
				user.setActive(true);
				//user.setIpChecked(true);
				user.setCreatedBy(loggedInUserId);
				user.setRole(repositoryRole.findByRoleId(SmartRoles.USER.getIndex()));
				user.setEmployeeCode(codeGenerator.getGeneratedCode(SmartCodeType.EMPLOYEECODE.name()));
				user.setPassword(passwordEncoder.encode(randomPasssword));
				user = repositoryUser.saveAndFlush(user);
				userDetail = new UserDetail();
				userDetail.setCreatedBy(loggedInUserId);
			} 
			else 
			{
				user.setUpdatedBy(loggedInUserId);
				userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				if (userDetail == null) {
					userDetail = new UserDetail();
					userDetail.setCreatedBy(loggedInUserId);
				} else {
					userDetail.setUpdatedBy(loggedInUserId);
				}
			}

			String dob = dtoUser.getDob();
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			user.setEmail(dtoUser.getEmail());
			user.setEmployeeCode(dtoUser.getEmployeeCode());
			user = repositoryUser.saveAndFlush(user);
			userDetail.setFirstName(dtoUser.getFirstName());
			userDetail.setLastName(dtoUser.getLastName());
			userDetail.setMiddleName(dtoUser.getMiddleName());
			userDetail.setPhone(dtoUser.getPhone());
			userDetail.setMobile(dtoUser.getMobile());
			userDetail.setUser(user);
			userDetail.setDob(formatter.parse(dob));
			userDetail.setStateMaster(repositoryStateMaster.findOne(dtoUser.getStateId()));
			userDetail.setCityMaster(repositoryCityMaster.findOne(dtoUser.getCityId()));
			userDetail.setCountryMaster(repositoryCountryMaster
					.findByCountryIdAndIsDeletedAndIsActive(dtoUser.getCountryId(), false, true));
			userDetail.setCountryCode(dtoUser.getCountryCode());
			userDetail.setZipcode(dtoUser.getZipCode());
			userDetail.setAddress(dtoUser.getAddress());
			userDetail.setEmail(dtoUser.getEmail());
			userDetail.setEmployeeCode(dtoUser.getEmployeeCode());
			repositoryUserDetail.saveAndFlush(userDetail);

			if(newUser){
				serviceEmailHandler.sendWelcomeEmail(user.getEmail(), randomPasssword, user.getUsername());
			}
			return new String[] { "success", String.valueOf(user.getUserId()) };

		} catch (Exception e) {
			log.info(Arrays.toString(e.getStackTrace()));
			return new String[] { e.getMessage(), String.valueOf(user.getUserId()) };
		}
	}


	/**
	 * @param index
	 * @return
	 *//*
	public List<User> getAllUsersByRole(int index) {
		return repositoryUser.findByRoleRoleIdAndIsDeleted(index, false);
	}

	  *//**
	  * Description: delete user (one or more) 
	  * 
	  * @param userIds
	  */
	public void deleteMultiPleUsers(List<Integer> userIds) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		if (userIds != null && !userIds.isEmpty()) {
			for (Integer id : userIds) {
				User user = repositoryUser.findByUserId(id);
				if (user != null) 
				{
					UserSession session = repositoryUserSession.findByUserUserIdAndIsDeleted(user.getUserId(), false);
					if(session!=null){
						repositoryUserSession.delete(session);
					}
					UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
					if (userDetail != null) {
						userDetail.setIsDeleted(true);
						userDetail.setUpdatedBy(loggedInUserId);
						repositoryUserDetail.saveAndFlush(userDetail);
					}
					user.setIsDeleted(true);
					user.setUpdatedBy(loggedInUserId);
					repositoryUser.saveAndFlush(user);
				}
			}
		}
	}

	/**
	 * @param dtoUser
	 * @param userDb
	 * @return
	 *//*
	public DtoUser validateUserPassword(DtoUser dtoUser, User userDb) {
		if (passwordEncoder.matches(dtoUser.getPassword(), userDb.getPassword())) {
			dtoUser.setUserId(userDb.getUserId());
		} else {
			dtoUser = null;
		}
		return dtoUser;
	}




	/** Description: send otp to user 
	  * @param dtoUserData
	  * @return
	  *//*
	public DtoUser sendOTPtoUser(DtoUser dtoUserData) {
		DtoUser dtoUserLogin = new DtoUser();
		User user3 = repositoryUser.findByusernameAndIsDeleted(dtoUserData.getUserName(), false);
		String langId = httpServletRequest.getHeader(LANG_ID);
		if (user3 != null) 
		{
			if (passwordEncoder.matches(dtoUserData.getPassword(), user3.getPassword())) {
				if (user3.isResetPassword()) {
					dtoUserLogin.setIsResetPassword("Y");
				} else {
					dtoUserLogin.setIsResetPassword("N");
				}

				AppConfigSetting appConfigSetting = repositoryAppConfigSetting
						.findByConfigNameAndIsDeleted(ConfigSetting.SMS_AUTHENTICATION.getValue(), false);
				if (appConfigSetting != null) {
					if (appConfigSetting.getConfigValue().equalsIgnoreCase("N")) {
						dtoUserLogin.setSmsAuthentication("N");

					} else {
						dtoUserLogin.setSmsAuthentication("Y");
					}
				} else {
					dtoUserLogin.setSmsAuthentication("Y");
				}

				if (dtoUserLogin.getSmsAuthentication().equalsIgnoreCase("Y")) {
					if (!user3.getRole().getRoleName().equalsIgnoreCase("Admin")) {
						UserDetail otpUserDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user3.getUserId(),
								false);
						if (otpUserDetail != null) {
							if (otpUserDetail.getPhone() != null) {
								LoginOtp loginSavedOtp = repositoryLoginOtp.findByUserUserId(user3.getUserId());
								if (loginSavedOtp == null) {
									loginSavedOtp = new LoginOtp();
								}

								String otp = UtilRandomKey.getRandomOrderNumber();
								loginSavedOtp.setCode(otp);
								loginSavedOtp.setUpdatedDate(new Date());
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.HOUR, 1);
								loginSavedOtp.setExpireDate(new Timestamp(cal.getTime().getTime()));
								loginSavedOtp.setIsDeleted(false);
								loginSavedOtp.setIsUsed("N");
								loginSavedOtp.setAttempts(0);
								loginSavedOtp.setUser(repositoryUser.findByUserId(user3.getUserId()));
								repositoryLoginOtp.save(loginSavedOtp);
								dtoUserLogin.setOtp(loginSavedOtp.getCode());
								dtoUserLogin.setUserId(user3.getUserId());
								if(otpUserDetail.getPhone() != null && ! otpUserDetail.getPhone().trim().isEmpty()){
									List<String> celNumberList = new ArrayList<>();
									celNumberList.add(otpUserDetail.getCountryCode()+otpUserDetail.getPhone());

									try {
										String message = serviceResponse.getStringMessageByShortAndIsDeleted("OTP_MESSAGE", false);
										this.cLXSMSUtility.sendSMS("BTI Team", message+" "+otp, celNumberList);
									} catch (Exception e) {
										log.info(Arrays.toString(e.getStackTrace()));
									}

									String phone = otpUserDetail.getPhone();
									phone = phone.substring(0, 3) + "****"
											+ phone.substring(phone.length() - 3, phone.length());

									dtoUserLogin.setPhone(phone);
								}


							} else {
								dtoUserLogin.setMessageType("USER_MOBILE_NOT_FOUND");

							}
						} else {
							dtoUserLogin.setMessageType("USER_DETAIL_NOT_FOUND");

						}
					}
				} else {
					String sessionId = sessionManager.getUniqueSessionId(user3.getUserId(),dtoUserData.getIpAddress());
					UserDetail userDetail = repositoryUserDetail.findByUserUserId(user3.getUserId());
					dtoUserLogin = new DtoUser(user3, userDetail, langId);
					dtoUserLogin.setSmsAuthentication("N");
					dtoUserLogin.setSession(sessionId);
					httpSession.setAttribute("loggedInUserId", user3.getUserId());
				}
			} else {
				dtoUserLogin.setMessageType("INVALID_USERNAME_AND_PASSWORD");

			}
		} else {
			dtoUserLogin.setMessageType("INVALID_USERNAME_AND_PASSWORD");

		}
		return dtoUserLogin;
	}
	   *//**
	   * Description: save Authorization setting
	   * @param dtoAuthorization
	   * @return
	   * @throws ParseException
	   */
	/*
	public DtoAuthorizationDetail saveAuthorizationDetail(DtoAuthorizationDetail dtoAuthorization)
			throws ParseException {
		AuthorizationSetting authorizationDetail = new AuthorizationSetting();

		if (!dtoAuthorization.getStartDate().isEmpty()) {
			authorizationDetail.setStartDate(UtilDateAndTime.stringToDate(dtoAuthorization.getStartDate()));
		}

		if (!dtoAuthorization.getEndDate().isEmpty()) {
			authorizationDetail.setEndDate(UtilDateAndTime.stringToDate(dtoAuthorization.getEndDate()));
		}

		authorizationDetail
				.setStartTime(UtilDateAndTime.getTimeFromStringFrom12Formats(dtoAuthorization.getStartTime()));
		authorizationDetail.setEndTime(UtilDateAndTime.getTimeFromStringFrom12Formats(dtoAuthorization.getEndTime()));
		authorizationDetail.setCreatedDate(new Date());
		authorizationDetail.setUpdatedDate(new Date());

		authorizationDetail = repositoryAuthorizationSetting.save(authorizationDetail);
		dtoAuthorization.setId(authorizationDetail.getAuthSettingId());
		return dtoAuthorization;
	}
	 */
	/**
	 * @param dtoUserIp
	 * @return
	 *//*
	public DtoUserIp setUserIPForAuthentication(DtoUserIp dtoUserIp) {

		WhitelistIp whitelistIp = new WhitelistIp();
		if (dtoUserIp.getIpAddress() != null) {
			WhitelistIp ipAvialable = repositoryWhiteListIp.findByIpAddressAndIsDeleted(dtoUserIp.getIpAddress(),
					false);
			if (ipAvialable != null) {
				dtoUserIp.setMessageType("IP_ALREADY_ADDED");

			} else {
				try {
					whitelistIp.setIpAddress(dtoUserIp.getIpAddress());
					whitelistIp.setIsActive(true);
					repositoryWhiteListIp.saveAndFlush(whitelistIp);
				} catch (Exception e) {
					log.info(Arrays.toString(e.getStackTrace()));
				}
			}
		} else {
			dtoUserIp.setMessageType("IP_REQUIRED");

		}
		return dtoUserIp;
	}


	  *//**
	  * @param dtoUserIp
	  * @return
	  *//*

	public Boolean updateUserIPForAuthentication(DtoUserIp dtoUserIp) {
		Boolean isUpdate = false;
		WhitelistIp whitelistIp = repositoryWhiteListIp.findOne(dtoUserIp.getId());
			if (whitelistIp != null && dtoUserIp.getIpAddress() != null) {
				try {
					whitelistIp.setIpAddress(dtoUserIp.getIpAddress());
					if (dtoUserIp.getIsActive().equalsIgnoreCase("Y")) {
						whitelistIp.setIsActive(true);
					} else {
						whitelistIp.setIsActive(false);
					}
					repositoryWhiteListIp.saveAndFlush(whitelistIp);
					isUpdate = true;
				} catch (Exception e) {
					log.info(Arrays.toString(e.getStackTrace()));
				}
			}  

		return isUpdate;
	}

	   *//**
	   ** @param userName
	   * @return
	   *//*
	public User getUserDetailByUserName(String userName) {
		return this.repositoryUser.findByusernameAndIsDeleted(userName, false);
	}	    */
	/**
	 * Description: search user by search keyword 
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchUsers(DtoSearch dtoSearch) {
		String langId = httpServletRequest.getHeader(LANG_ID);
		if (dtoSearch != null) {
			dtoSearch.setTotalCount(this.repositoryUserDetail
					.predictiveUserSearchTotalCount(dtoSearch.getSearchKeyword(), SmartRoles.USER.getIndex()));
			List<UserDetail> userDetailList = this.repositoryUserDetail.predictiveUserSearchWithPagination(
					dtoSearch.getSearchKeyword(),
					new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"),
					SmartRoles.USER.getIndex());
			if (userDetailList != null && !userDetailList.isEmpty()) {
				List<DtoUser> dtoUserList = new ArrayList<>();
				DtoUser dtoUser = null;
				User user = null;
				for (UserDetail userDetail : userDetailList) {
					user = userDetail.getUser();
					dtoUser = new DtoUser(user, userDetail, langId);
					// get company list
					dtoUser = setUserCompanyRelationListInDto(this.repositoryUserCompanies
							.findByUserUserIdAndIsDeleted(user != null ? user.getUserId() : -1, false), dtoUser);
					dtoUserList.add(dtoUser);
				}
				dtoSearch.setRecords(dtoUserList);
			}
		}
		return dtoSearch;
	}	

	/**
	 * @param dtoUserIp
	 * @return
	 */
	public DtoUserIp setUserIPForAuthentication(DtoUserIp dtoUserIp) {

		WhitelistIp whitelistIp = new WhitelistIp();
		if (dtoUserIp.getIpAddress() != null) {
			WhitelistIp ipAvialable = repositoryWhiteListIp.findByIpAddressAndIsDeleted(dtoUserIp.getIpAddress(),
					false);
			if (ipAvialable != null) {
				dtoUserIp.setMessageType("IP_ALREADY_ADDED");

			} else {
				try {
					whitelistIp.setIpAddress(dtoUserIp.getIpAddress());
					whitelistIp.setIsActive(true);
					repositoryWhiteListIp.saveAndFlush(whitelistIp);
				} catch (Exception e) {
					log.info(Arrays.toString(e.getStackTrace()));
				}
			}
		} else {
			dtoUserIp.setMessageType("IP_REQUIRED");

		}
		return dtoUserIp;
	}

	/**
	 * @param dtoUserIp
	 * @return
	 */
	public Boolean updateUserIPForAuthentication(DtoUserIp dtoUserIp) {
		Boolean isUpdate = false;
		WhitelistIp whitelistIp = repositoryWhiteListIp.findOne(dtoUserIp.getId());
		if (whitelistIp != null && dtoUserIp.getIpAddress() != null) {
			try {
				whitelistIp.setIpAddress(dtoUserIp.getIpAddress());
				if (dtoUserIp.getIsActive().equalsIgnoreCase("Y")) {
					whitelistIp.setIsActive(true);
				} else {
					whitelistIp.setIsActive(false);
				}
				repositoryWhiteListIp.saveAndFlush(whitelistIp);
				isUpdate = true;
			} catch (Exception e) {
				log.info(Arrays.toString(e.getStackTrace()));
			}
		}  

		return isUpdate;
	}

	/**
	 * Description: save Authorization setting
	 * @param dtoAuthorization
	 * @return
	 * @throws ParseException
	 */
	public DtoAuthorizationDetail saveAuthorizationDetail(DtoAuthorizationDetail dtoAuthorization)
			throws ParseException {
		AuthorizationSetting authorizationDetail = new AuthorizationSetting();

		if (!dtoAuthorization.getStartDate().isEmpty()) {
			authorizationDetail.setStartDate(UtilDateAndTime.stringToDate(dtoAuthorization.getStartDate()));
		}

		if (!dtoAuthorization.getEndDate().isEmpty()) {
			authorizationDetail.setEndDate(UtilDateAndTime.stringToDate(dtoAuthorization.getEndDate()));
		}

		authorizationDetail
		.setStartTime(UtilDateAndTime.getTimeFromStringFrom12Formats(dtoAuthorization.getStartTime()));
		authorizationDetail.setEndTime(UtilDateAndTime.getTimeFromStringFrom12Formats(dtoAuthorization.getEndTime()));
		authorizationDetail.setCreatedDate(new Date());
		authorizationDetail.setUpdatedDate(new Date());

		authorizationDetail = repositoryAuthorizationSetting.save(authorizationDetail);
		dtoAuthorization.setId(authorizationDetail.getAuthSettingId());
		return dtoAuthorization;
	}

	/**
	 * Description: send otp to user 
	 * @param dtoUserData
	 * @return
	 */
	public DtoUser sendOTPtoUser(DtoUser dtoUserData) {
		DtoUser dtoUserLogin = new DtoUser();
		User user3 = repositoryUser.findByusernameAndIsDeleted(dtoUserData.getUserName(), false);
		String langId = httpServletRequest.getHeader(LANG_ID);
	//	System.out.println(passwordEncoder.encode(dtoUserData.getPassword()) +"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		if (user3 != null) 
		{
			if (passwordEncoder.matches(dtoUserData.getPassword(), user3.getPassword())) {
				/*if (user3.isResetPassword()) {
					dtoUserLogin.setIsResetPassword("Y");
				} else {
					dtoUserLogin.setIsResetPassword("N");
				}
*/
				AppConfigSetting appConfigSetting = repositoryAppConfigSetting
						.findByConfigNameAndIsDeleted(ConfigSetting.SMS_AUTHENTICATION.getValue(), false);
				if (appConfigSetting != null) {
					if (appConfigSetting.getConfigValue().equalsIgnoreCase("N")) {
						dtoUserLogin.setSmsAuthentication("N");

					} else {
						dtoUserLogin.setSmsAuthentication("Y");
					}
				} else {
					dtoUserLogin.setSmsAuthentication("Y");
				}

				if (dtoUserLogin.getSmsAuthentication().equalsIgnoreCase("Y")) {
					if (!user3.getRole().getRoleName().equalsIgnoreCase("Admin")) {
						UserDetail otpUserDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user3.getUserId(),
								false);
						if (otpUserDetail != null) {
							if (otpUserDetail.getPhone() != null) {
								LoginOtp loginSavedOtp = repositoryLoginOtp.findByUserUserId(user3.getUserId());
								if (loginSavedOtp == null) {
									loginSavedOtp = new LoginOtp();
								}

								String otp = UtilRandomKey.getRandomOrderNumber();
								loginSavedOtp.setCode(otp);
								loginSavedOtp.setUpdatedDate(new Date());
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.HOUR, 1);
								loginSavedOtp.setExpireDate(new Timestamp(cal.getTime().getTime()));
								loginSavedOtp.setIsDeleted(false);
								loginSavedOtp.setIsUsed("N");
								loginSavedOtp.setAttempts(0);
								loginSavedOtp.setUser(repositoryUser.findByUserId(user3.getUserId()));
								repositoryLoginOtp.save(loginSavedOtp);
								dtoUserLogin.setOtp(loginSavedOtp.getCode());
								dtoUserLogin.setUserId(user3.getUserId());
								if(otpUserDetail.getPhone() != null && ! otpUserDetail.getPhone().trim().isEmpty()){
									List<String> celNumberList = new ArrayList<>();
									celNumberList.add(otpUserDetail.getCountryCode()+otpUserDetail.getPhone());

									try {
										String message = serviceResponse.getStringMessageByShortAndIsDeleted("OTP_MESSAGE", false);
									//	this.cLXSMSUtility.sendSMS("Smart Team", message+" "+otp, celNumberList);
										serviceEmailHandler.sendEmail(otpUserDetail.getEmail(), message +" "+otp, "OTP");;
									} catch (Exception e) {
										log.info(Arrays.toString(e.getStackTrace()));
									}

									String phone = otpUserDetail.getPhone();
									phone = phone.substring(0, 3) + "****"
											+ phone.substring(phone.length() - 3, phone.length());

									dtoUserLogin.setPhone(phone);

									//dtoUserLogin.setMobile(phone);

									dtoUserLogin.setEmail(otpUserDetail.getEmail());

								}


							} else {
								dtoUserLogin.setMessageType("USER_MOBILE_NOT_FOUND");

							}
						} else {
							dtoUserLogin.setMessageType("USER_DETAIL_NOT_FOUND");

						}
					}
				} else {
					String sessionId = sessionManager.getUniqueSessionId(user3.getUserId(),dtoUserData.getIpAddress());
					UserDetail userDetail = repositoryUserDetail.findByUserUserId(user3.getUserId());
					dtoUserLogin = new DtoUser(user3, userDetail, langId);
					dtoUserLogin.setSmsAuthentication("N");
					dtoUserLogin.setSession(sessionId);
					httpSession.setAttribute("loggedInUserId", user3.getUserId());
				}
			} else {
				dtoUserLogin.setMessageType("INVALID_USERNAME_AND_PASSWORD");

			}
		} else {
			dtoUserLogin.setMessageType("INVALID_USERNAME_AND_PASSWORD");

		}
		return dtoUserLogin;
	}

	/**
	 * Description: save mac address
	 * @param macAddress
	 * @param user
	 * @return
	 */
	public boolean saveMacAddress(DtoUser dtoUser, User user) 
	{
		UserMacAddress userMacAddress=null;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		if(UtilRandomKey.isNotBlank(dtoUser.getMacAddress()))
		{
			userMacAddress= repositoryUserMacAddress.findByMacAddressAndUserUserId(dtoUser.getMacAddress(), user.getUserId());
			if(userMacAddress==null)
			{
				userMacAddress= new UserMacAddress();
			}
			userMacAddress.setCreatedBy(loggedInUserId);
			userMacAddress.setUser(user);
			userMacAddress.setMacAddress(dtoUser.getMacAddress());
			userMacAddress.setIsActive(true);
			userMacAddress.setDeviceType(dtoUser.getDeviceType());
			userMacAddress.setDeviceDescription(dtoUser.getDeviceDescription());
			repositoryUserMacAddress.save(userMacAddress);
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Description: delete mac address 
	 * @param dtoUser
	 * @return
	 */
	public boolean deleteMacAddressOfUser(DtoUser dtoUser) 
	{
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		UserMacAddress userMacAddress = repositoryUserMacAddress.findOne(dtoUser.getId());
		if(userMacAddress!=null)
		{
			userMacAddress.setIsDeleted(true);
			userMacAddress.setIsActive(false);
			userMacAddress.setUpdatedBy(loggedInUserId);
			repositoryUserMacAddress.saveAndFlush(userMacAddress);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean updateIpCheckStatusOfUser(DtoUser dtoUser) 
	{
		boolean result=false;
		for (Integer userId : dtoUser.getUserIds()) {
			User user = repositoryUser.findByUserId(userId);
			if(user!=null){
				//user.setIpChecked(dtoUser.getIpChecked());
				repositoryUser.saveAndFlush(user);
			}
		}
		result=true;
		return result;
	}


}

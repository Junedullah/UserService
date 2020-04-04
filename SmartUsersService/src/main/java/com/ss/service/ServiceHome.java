/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Mar 30, 2020
 * Modified on: Mar 30, 2020 11:19:38 AM
 * @author Juned Baig
 * Version: 
 */
package com.ss.service;

import com.ss.model.User;

import com.ss.repository.RepositoryUser;
import com.ss.model.UserDetail;
import com.ss.model.dto.DtoUser;
import com.ss.constant.Constant;
import com.ss.constant.MessageLabel;
import com.ss.constant.SmartRoles;
import com.ss.model.*;
import com.ss.model.dto.*;
import com.ss.repository.*;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;


/**
 * Description: Service Home
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: MARCH 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Service("serviceHome")
@Transactional
@EnableScheduling
public class ServiceHome {

	private static final Logger LOGGER = Logger.getLogger(ServiceHome.class);

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

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;

	@Autowired
	ServiceEmailHandler serviceEmailHandler;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanies;

	@Autowired
	RepositoryCompany repositoryCompany;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired
	RepositoryAuthorizationSetting repositoryAuthorizationSetting;

	@Autowired
	RepositoryCountryMaster repositoryCountryMaster;

	@Autowired
	RepositoryStateMaster repositoryStateMaster;

	@Autowired
	RepositoryCityMaster repositoryCityMaster;

	@Autowired
	RepositoryLanguage repositoryLanguage;	

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryFields repositoryFields;

	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;

	@Autowired
	RepositoryFieldValidation repositoryFieldValidation;

	@Autowired
	ServiceResponse serviceResponse;

	/*	@Autowired
	RepositoryCommonConstant repositoryCommonConstant;*/

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;

	/*	@Autowired
	RepositorySstMessage repositorySstMessage;*/

	@Autowired
	RepositoryWeekDay repositoryWeekDay;

	@Autowired
	RepositoryUserSession repositoryUserSession;

	@Autowired
	RepositoryUserDraft repositoryUserDraft;

	@Value("${script.accessfinancialpath}")
	private String accessfinancialpath;

	private final Integer ENG_LANG_ID=1;


	/**
	 * Description: get country list for drop down
	 * @return
	 */
	public List<DtoCountry> getCountryList() {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> countryList = new ArrayList<>();
		List<CountryMaster> list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));

		if(list.isEmpty()){
			list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, 1);
		}

		if (list != null && !list.isEmpty()) {
			for (CountryMaster countryMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCountryId(countryMaster.getCountryId());
				dtoCountry.setCountryName(countryMaster.getCountryName());
				dtoCountry.setShortName(countryMaster.getShortName());
				dtoCountry.setCountryCode(countryMaster.getCountryCode());
				countryList.add(dtoCountry);
			}
		}
		return countryList;
	}

	/**
	 * Description: get state list by country id for drop down
	 * @param countryId
	 * @return
	 */
	public List<DtoCountry> getStateListByCountryId(int countryId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> stateList = new ArrayList<>();
		List<StateMaster> list = repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, Integer.parseInt(langId));
		if(list.isEmpty()){
			list=repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, 1);
		}
		if (list != null && !list.isEmpty()) {
			for (StateMaster stateMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setStateId(stateMaster.getStateId());
				dtoCountry.setStateName(stateMaster.getStateName());
				stateList.add(dtoCountry);
			}
		}
		return stateList;
	}

	/**
	 * Description: get city list by state id for drop down
	 * @param stateId
	 * @return
	 */
	public List<DtoCountry> getCityListByStateId(int stateId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> cityList = new ArrayList<>();
		List<CityMaster> list = repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,Integer.parseInt(langId));

		if(list.isEmpty()){
			list=repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,1);
		}

		if (list != null && !list.isEmpty()) {
			for (CityMaster cityMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCityId(cityMaster.getCityId());
				dtoCountry.setCityName(cityMaster.getCityName());
				cityList.add(dtoCountry);
			}
		}
		return cityList;
	}

	/**
	 * Description: get country code by country id
	 * @param countryId
	 * @return
	 */
	public DtoCountry getCountryCodeByCountryId(int countryId) {
		DtoCountry dtoCountry = new DtoCountry();
		CountryMaster countryMaster = repositoryCountryMaster.findByCountryIdAndIsDeletedAndIsActive(countryId, false,
				true);
		if (countryMaster != null) {

			dtoCountry.setCountryId(countryMaster.getCountryId());
			dtoCountry.setCountryCode("+" + countryMaster.getCountryCode());
		}
		return dtoCountry;
	}


	public boolean updateActiveSession(DtoUser dtoUser){
		UserSession userSession = repositoryUserSession.findByUserUserIdAndSessionAndIsDeleted(dtoUser.getUserId(), dtoUser.getSession(), false);
		if(userSession!=null){
			userSession.setUpdatedDate(new Date());
			repositoryUserSession.saveAndFlush(userSession);
			return true;
		}
		return false;
	}
	/**
	 * Description: reset password for user by super admin
	 * @param user
	 * @param newPassword
	 */
	public void resetPasswordOfUser(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPassword(false);
		repositoryUser.saveAndFlush(user);

	}

	/**
	 * 
	 * @param user
	 * @param password
	 */
	public void changePasswordOfUser(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		user.setResetPassword(true);
		repositoryUser.saveAndFlush(user);
	}

	/**
	 * @param dtoUser
	 * @return
	 */
	public boolean matchOldPassword(DtoUser dtoUser) {
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
		return user != null && passwordEncoder.matches(dtoUser.getPassword(), user.getPassword());
	}

	/**
	 * @param user
	 * @return
	 */
	public Boolean sendForgotPasswordEmail(User user) 
	{
		Boolean status = false;
		try {
			if (user != null) {
				// send forgot password email
				String newPassword = new UtilRandomKey().nextRandomKey();
				user.setPassword(this.passwordEncoder.encode(newPassword));
				user.setResetPassword(true);
				user = this.repositoryUser.saveAndFlush(user);
				UserDetail userDetail = this.repositoryUserDetail.findByUserUserId(user.getUserId());
				String firstName = null;
				if (userDetail != null && userDetail.getFirstName() != null) {
					firstName = userDetail.getFirstName();
				} else {
					firstName = "";
				}
				serviceEmailHandler.sendForgotPasswordEmail(user.getEmail(), newPassword, firstName);
				status = true;
			}
		} catch (Exception e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}

		return status;

	}
}

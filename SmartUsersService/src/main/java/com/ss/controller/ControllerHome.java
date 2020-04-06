/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Mar 30, 2020
 * Modified on: Mar 30, 2020 11:19:38 AM
 * @author Juned Baig
 * Version: 
 */
package com.ss.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.AccessRole;
import com.ss.model.Language;
import com.ss.model.User;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoCountry;
import com.ss.model.dto.DtoLanguage;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.repository.RepositoryAccessRole;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserDraft;
import com.ss.repository.RepositoryUserSession;
import com.ss.repository.RepositoryValidationMessages;
import com.ss.service.ServiceAccessRole;
import com.ss.service.ServiceHome;
import com.ss.service.ServiceLogin;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUser;
import com.ss.util.UtilFindIPAddress;
import com.ss.util.UtilRandomKey;


@RestController
@RequestMapping("/")
public class ControllerHome {

	private static final Logger LOGGER = Logger.getLogger(ControllerHome.class);

	@Autowired
	ServiceHome serviceHome;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ServiceAccessRole serviceAccessRole;

	@Autowired
	RepositoryAccessRole repositoryAccessRole;
	
	@Autowired
	RepositoryUserSession repositoryUserSession;
	
	@Autowired
	RepositoryLanguage repositoryLanguage;
	
	@Autowired
	RepositoryException repositoryException;
	
	@Autowired
	RepositoryScreen repositoryScreen;
	
	@Autowired
	RepositoryModule repositoryModule;
	
	@Autowired
	RepositoryFields repositoryFields;
	
	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;
	
	@Autowired
	RepositoryUserDraft repositoryUserDraft;
	
	/**
	 * @description : It will return all the labels related to screen and module
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/screenDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage loginScreen(@RequestBody DtoScreenDetail dtoScreenDetail, HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;
		DtoModule dtoModule = serviceLogin.getScreenDetail(dtoScreenDetail);
		if (dtoModule != null) {
			if (dtoModule.getMessageType() == null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_SUCCESS, false), dtoModule);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(dtoModule.getMessageType(), false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.DO_NOT_ACCESS_OF_SCREEN, false));
		}
		return responseMessage;
	}


	/**
	 * @description : Get Country List
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCountryList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getCountryList(HttpServletRequest request) {
		LOGGER.info("Get Country List");
		ResponseMessage responseMessage = null;
		List<DtoCountry> countryList = serviceHome.getCountryList();
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_COUNTRY_LIST, false), countryList);
		return responseMessage;
	}

	/**
	 * @description : Get State List By Country Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getStateListByCountryId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getStateListByCountryId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) {
		LOGGER.info("Get State List By Country Id");
		ResponseMessage responseMessage = null;
		List<DtoCountry> stateList = serviceHome.getStateListByCountryId(dtoCountry.getCountryId());
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_STATE_LIST, false), stateList);

		return responseMessage;
	}

	/**
	 * @description : Get City List By State Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getCityListByStateId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getCityListByStateId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) 
	{
		LOGGER.info("Get City List By State Id");
		ResponseMessage responseMessage = null;
		List<DtoCountry> cityList = serviceHome.getCityListByStateId(dtoCountry.getStateId());
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_CITY_LIST, false), cityList);
		return responseMessage;
	}

	/**
	 * @description : Get Country Code By Country Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getCountryCodeByCountryId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getCountryCodeByCountryId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) {
		LOGGER.info("Get Country Code By Country  Id");
		ResponseMessage responseMessage = null;
		DtoCountry countryCode = serviceHome.getCountryCodeByCountryId(dtoCountry.getCountryId());

		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_COUNTRY_CODE, false), countryCode);

		return responseMessage;
	}

	/**
	 * @description : Reset Password
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage resetPassword(@RequestBody DtoUser dtoUser) 
	{
		LOGGER.info("Reset Password of user");
		ResponseMessage responseMessage = null;
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
		if (user != null) {

			serviceHome.resetPasswordOfUser(user, dtoUser.getPassword());
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_PASSWORD_RESET, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
		}
		return responseMessage;
	}

	/**
	 * @description : Get user IP address
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/myIP", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getMyIP(HttpServletRequest request) {
		LOGGER.info("Getting my IP address");
		ResponseMessage responseMessage = null;
		String userIp = UtilFindIPAddress.getUserIp(request);
		if (userIp != null) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_FETCHED, false), userIp);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_NOT_FETCHED, false), userIp);
		}
		return responseMessage;
	}

	/**
	 * @description : Change Password
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage changePassword(@RequestBody DtoUser dtoUser) {
		LOGGER.info("Change Password of user");
		ResponseMessage responseMessage = null;
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
		if (user != null) {
			if (UtilRandomKey.isNotBlank(dtoUser.getPassword())) {
				serviceHome.changePasswordOfUser(user, dtoUser.getPassword());
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.CHANGE_PASSWORD, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.PASSWORD_IS_BLANK, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Match Old Password
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/matchOldPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage matchOldPassword(@RequestBody DtoUser dtoUser) {
		LOGGER.info("Match Old Password of user");
		ResponseMessage responseMessage = null;
		boolean result = serviceHome.matchOldPassword(dtoUser);
		if (result) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.OLD_PASSWORD_MATCH, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.OLD_PASSWORD_DO_NOT_MATCH, false));
		}
		return responseMessage;
	}

	/**
	 * @description : Forgot Password
	 * @param dtoUser
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseMessage forgotPassword(@RequestBody DtoUser dtoUser, HttpServletRequest request) {
		LOGGER.info("Forgot Password of user");
		ResponseMessage responseMessage = null;
		User user = this.serviceUser.getUserDetailByUserName(dtoUser.getEmail());
		if (user != null) {
			if (this.serviceHome.sendForgotPasswordEmail(user)) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORGOT_PASSWORD_EMAIL_SUCCESS, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false), null);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
		}
		return responseMessage;
	}

	/**
	 * @description : List Of Screen Modules
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listOfScreenModules", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfScreenModules(@RequestBody DtoScreenDetail dtoScreenDetail,
			HttpServletRequest request) {
		LOGGER.info("Get List of screen Modules");
		ResponseMessage responseMessage = null;
		List<DtoModule> dtoScreenDetailList = serviceAccessRole.getAllScreenDetailList(dtoScreenDetail);
		if (dtoScreenDetailList != null) {
			if (!dtoScreenDetailList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_SUCCESS, false),
						dtoScreenDetailList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAIL_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_FAIL, false));
		}
		return responseMessage;
	}

	/**
	 * @description : List Of Screen Modules By Access Role
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listOfScreenModulesByAccessRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfScreenModulesByAccessRole(@RequestBody DtoScreenDetail dtoScreenDetail,
			HttpServletRequest request) {
		LOGGER.info("inside getListOfScreenModulesByAccessRole method");
		ResponseMessage responseMessage = null;
		AccessRole accessRole = repositoryAccessRole.findByAccessRoleIdAndIsDeleted(dtoScreenDetail.getAccessRoleId(),
				false);
		if (accessRole != null) {
			List<DtoModule> dtoScreenDetailList = serviceAccessRole.getAllScreenDetailListByAccessRoleId(accessRole,
					dtoScreenDetail);
			if (dtoScreenDetailList != null) {
				if (!dtoScreenDetailList.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_SUCCESS, false),
							dtoScreenDetailList);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAIL_NOT_FOUND, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_FAIL, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ACCESS_ROLE_NOT_FOUND, false));
		}
		return responseMessage;
	}

	/**
	 * @description : It will return all the labels related to screen and module
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getScreenDetailOfUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getScreenDetailOfUser(@RequestBody DtoScreenDetail dtoScreenDetail, HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;
		DtoModule dtoModule = serviceLogin.getScreenDetailByUserId(dtoScreenDetail);
		if (dtoModule != null) {
			if (dtoModule.getMessageType() == null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_SUCCESS, false), dtoModule);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(dtoModule.getMessageType(), false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_FAIL, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/deleteSession", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteSession(@RequestBody DtoUser dtoUser, HttpServletRequest request) {
		LOGGER.info("hard delete user session teporary web service");
		ResponseMessage responseMessage = null;
		 UserSession userSession=	repositoryUserSession.findByUserUserIdAndIsDeleted(dtoUser.getUserId(), false);
		 if(userSession!=null){
			 repositoryUserSession.delete(userSession);
		 }
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						"User session deleted successfully");
		return responseMessage;
	}
	/**
	 * @description : It will return all the List Of Language
	 * @param dtoSearch
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLanguageList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getLanguageList(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		LOGGER.info("Get Language List");
		ResponseMessage responseMessage = null;
	   dtoSearch = serviceHome.getLanguageList(dtoSearch);
	   if(dtoSearch.getRecords()!=null)
	   {
		   List<DtoLanguage> list = (List<DtoLanguage>) dtoSearch.getRecords();
		   if(list !=null && !list.isEmpty())
		   {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), dtoSearch);
			
		   }
		   else
		   {
			   responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
		   }
	   }
	   else
	   {
		   responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
	   }
		return responseMessage;
	}
	
	@RequestMapping(value = "/language/add", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseMessage addNewLanguageWithExcel( @PathVariable("file") MultipartFile file,
	      String languageName , String languageOrientation ,  HttpServletRequest request) throws IOException 
	{
		LOGGER.info("Add New Language");
		ResponseMessage responseMessage = null;
		if(UtilRandomKey.isNotBlank(languageName) && UtilRandomKey.isNotBlank(languageOrientation)
			&& file!=null && file.getBytes().length>0)
		{
			DtoLanguage dtoLanguage = new DtoLanguage();
			dtoLanguage.setLanguageName(languageName);
			dtoLanguage.setLanguageOrientation(languageOrientation);
			dtoLanguage.setFile(file);
			dtoLanguage = serviceHome.addNewLanguage(dtoLanguage);
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.NEW_LANGUAGE_ADDED, false), dtoLanguage);
		}
		else
		{
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/language/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateLanguage(HttpServletRequest request, @RequestBody DtoLanguage dtoLanguage) 
	{
		LOGGER.info("Update Language");
		ResponseMessage responseMessage = null;
		if(UtilRandomKey.isNotBlank(dtoLanguage.getLanguageName()) && UtilRandomKey.isNotBlank(dtoLanguage.getLanguageOrientation()))
		{
			Language language = repositoryLanguage.findOne(dtoLanguage.getLanguageId());
			if(language!=null)
			{
				dtoLanguage = serviceHome.updateLanguage(dtoLanguage);
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LANGUAGE_UPDATED, false), dtoLanguage);
			}
			else{
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		}
		else
		{
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/language/getById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getLanguageById(HttpServletRequest request, @RequestBody DtoLanguage dtoLanguage) 
	{
		    LOGGER.info(" Get Language By Id ");
		    ResponseMessage responseMessage = null;
			Language language = repositoryLanguage.findByLanguageIdAndIsDeleted(dtoLanguage.getLanguageId(), false);
			if(language!=null)
			{
				dtoLanguage = serviceHome.getLanguageByLangId(dtoLanguage);
				
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), dtoLanguage);
			}
			else
			{
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		return responseMessage;
	}
	
	@RequestMapping(value = "/language/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteLanguage(HttpServletRequest request, @RequestBody DtoLanguage dtoLanguage) 
	{
		    LOGGER.info("Language delete ");
		    ResponseMessage responseMessage = null;
			Language language = repositoryLanguage.findOne(dtoLanguage.getLanguageId());
			if(language!=null)
			{
				 serviceHome.deleteLanguage(language);
				 responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_DELETE_SUCCESSFULLY, false));
			}
			else
			{
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		return responseMessage;
	}
	
	@RequestMapping(value = "/language/activeInactive", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage activeInactiveLanguage(HttpServletRequest request, @RequestBody DtoLanguage dtoLanguage) 
	{
		ResponseMessage responseMessage = null;
		Language language = this.repositoryLanguage.findByLanguageIdAndIsDeleted(dtoLanguage.getLanguageId(), false);
		if(language!=null){
			dtoLanguage = this.serviceHome.blockUnblockLanguage(dtoLanguage);
			if (dtoLanguage != null && dtoLanguage.getIsActive()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LANGUAGE_ACTIVATED_SUCCESS, false),
						dtoLanguage);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LANGUAGE_BLOCKED_SUCCESS, false),
						dtoLanguage);
			}
		}
		else
		{
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
		}
		
		return responseMessage;
	}
	
	@RequestMapping(value = "/getLanguageListForDropDown", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getLanguageListForDropDown(HttpServletRequest request) {
	   LOGGER.info("Get Language List");
	   ResponseMessage responseMessage = null;
	   List<DtoLanguage> list = serviceHome.getLanguageListForDropDown();
	   if(list!=null)
	   {
		   if(!list.isEmpty())
		   {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), list);
			
		   }
		   else
		   {
			   responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
		   }
	   }
	   else
	   {
		   responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
	   }
		return responseMessage;
	}
	
	/**
	 * @description : It will return all the labels related to screen and module
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/screenGridDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage loginScreenGrid(@RequestBody DtoScreenDetail dtoScreenDetail, HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;
		DtoModule dtoModule = serviceLogin.getScreenGridDetail(dtoScreenDetail);
		if (dtoModule != null) {
			if (dtoModule.getMessageType() == null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAILS_SUCCESS", false), dtoModule);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(dtoModule.getMessageType(), false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted("DO_NOT_ACCESS_OF_SCREEN", false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : List Of Modules
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listOfModulesByLanguageAndIsActive", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getListOfModulesByLanguageAndIsActive(HttpServletRequest request) {
		LOGGER.info("Get List of Modules");
		ResponseMessage responseMessage = null;
		List<DtoModule> dtoModuleList = serviceHome.getAllModuleByLanguageAndIsActive();
		if (dtoModuleList != null && dtoModuleList.size() > 0) {
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted("MODULE_DETAILS_SUCCESS", false), dtoModuleList);
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted("MODULE_DETAILS_FAIL", false));
		}
		return responseMessage;
	}
	

	@RequestMapping(value = "/getCommononConstantList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getCommononConstantList(HttpServletRequest request) 
	{
		LOGGER.info("Get Common Constant List");
		ResponseMessage responseMessage = null;
		Map<String, String> map = serviceHome.getCommonConstantList();
		
		
		responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), map);
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/companyListByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage companyListByUserId(HttpServletRequest request,@RequestBody DtoUser dtoUser) 
	{
		    ResponseMessage responseMessage = null;
			List<DtoCompany> list = this.serviceHome.getCompaniesListByUserId(dtoUser.getUserId());
			if (list!=null && !list.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), list);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		
		return responseMessage;
	}
	
	@RequestMapping(value = "/getCompanyTenant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getCompanyTenant(HttpServletRequest request,@RequestBody DtoUser dtoUser) 
	{
		    ResponseMessage responseMessage = null;
			String tenantId = this.serviceHome.getCompanyTenant(dtoUser.getCompanyId());
			if (tenantId!=null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), tenantId);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		
		return responseMessage;
	}
	
	@RequestMapping(value = "/getCompanyIdFromTenant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getCompanyIdFromTenant(HttpServletRequest request,@RequestBody DtoUser dtoUser) 
	{
		    ResponseMessage responseMessage = null;
			int companyId = this.serviceHome.getCompanyIdFromTenant(dtoUser.getCompanyTenantId());
			if (companyId>0) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), companyId);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		
		return responseMessage;
	}
	
	@RequestMapping(value = "/getCompanyName", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getCompanyName(HttpServletRequest request,@RequestBody DtoUser dtoUser) 
	{
		    ResponseMessage responseMessage = null;
			String companyName = this.serviceHome.getCompanyName(dtoUser.getCompanyId());
			if (companyName!=null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), companyName);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/updateActiveSession", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage updateActiveSessiong(HttpServletRequest request,@RequestBody DtoUser dtoUser) 
	{
		ResponseMessage responseMessage = null;
			Boolean response = this.serviceHome.updateActiveSession(dtoUser);
		if (response) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_UPDATED_SUCCESS, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getDueTypes", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getDueTypes(HttpServletRequest request) {
		LOGGER.info("inside getDueTypes method");
		ResponseMessage responseMessage = null;
		List<DtoCountry> countryList = serviceHome.getCountryList();
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_COUNTRY_LIST, false), countryList);
		return responseMessage;
	}
	
	@RequestMapping(value = "/deleteAllSession", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage deleteAllSession(HttpServletRequest request) {
		LOGGER.info("Get Country List");
		ResponseMessage responseMessage = null;
		
		 repositoryUserSession.deleteAllInBatch();
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						"User session deleted successfully");
		return responseMessage;
	}
	
	
	/**
	 * @description : List Of Transaction Modules
	 * @param dtoTransactionType
	 * @param request
	 * @return
	 */
/*	@RequestMapping(value = "/listOfTransactionModules", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfTransactionModules(@RequestBody DtoTransactionType dtoTransactionType,
			HttpServletRequest request) {
		LOGGER.info("Get List of Transaction Modules");
		ResponseMessage responseMessage = null;
		List<DtoModule> dtoScreenDetailList = serviceAccessRole.getAllAccessRolesTrasationList(dtoTransactionType);
		if (dtoScreenDetailList != null) {
			if (!dtoScreenDetailList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED, serviceResponse
						.getMessageByShortAndIsDeleted(MessageLabel.TRANSACTION_LIST_OF_ROLE_ACCESS_GET_SUCCESS, false),
						dtoScreenDetailList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.TRANSACTION_LIST_OF_ROLE_ACCESS_NOT_FOUND,
								false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : List Of Report Modules
	 * @param dtoReportCategory
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/listOfReportModules", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfReportModules(@RequestBody DtoReportCategory dtoReportCategory,
			HttpServletRequest request) {
		LOGGER.info("Get List of report Modules");
		ResponseMessage responseMessage = null;
		List<DtoModule> dtoScreenDetailList = serviceAccessRole.getAllAccessRolesReportsList(dtoReportCategory);
		if (dtoScreenDetailList != null) {
			if (!dtoScreenDetailList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.REPORT_LIST_OF_ROLE_ACCESS_GET_SUCCESS, false),
						dtoScreenDetailList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.REPORT_LIST_OF_ROLE_ACCESS_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : List Of Report Modules By Access Role
	 * @param dtoReportCategory
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/listOfReportModulesByAccessRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfReportModulesByAccessRole(@RequestBody DtoReportCategory dtoReportCategory,
			HttpServletRequest request) {
		LOGGER.info("Get List of Modules");
		ResponseMessage responseMessage = null;
		AccessRole accessRole = repositoryAccessRole.findByAccessRoleIdAndIsDeleted(dtoReportCategory.getAccessRoleId(),
				false);
		if (accessRole != null) {
			List<DtoModule> dtoScreenDetailList = serviceAccessRole
					.getAllAccessRolesReportsListByAccessRoleId(dtoReportCategory, accessRole);
			if (dtoScreenDetailList != null) {
				if (!dtoScreenDetailList.isEmpty()) {
					responseMessage = new ResponseMessage(
							HttpStatus.CREATED.value(), HttpStatus.CREATED, serviceResponse
									.getMessageByShortAndIsDeleted(MessageLabel.REPORT_LIST_OF_ROLE_ACCESS_GET_SUCCESS, false),
							dtoScreenDetailList);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.REPORT_LIST_OF_ROLE_ACCESS_NOT_FOUND,
									false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ACCESS_ROLE_NOT_FOUND, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : List Of Transaction Modules By Access Role
	 * @param dtoTransactionType
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/listOfTransactionModulesByAccessRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfTransactionModulesByAccessRole(@RequestBody DtoTransactionType dtoTransactionType,
			HttpServletRequest request) {
		LOGGER.info("Get List of Modules");
		ResponseMessage responseMessage = null;
		AccessRole accessRole = repositoryAccessRole
				.findByAccessRoleIdAndIsDeleted(dtoTransactionType.getAccessRoleId(), false);
		if (accessRole != null) {
			List<DtoModule> dtoScreenDetailList = serviceAccessRole
					.getAllAccessRolesTrasationListByAccessRoleId(dtoTransactionType, accessRole);
			if (dtoScreenDetailList != null) {
				if (!dtoScreenDetailList.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.TRANSACTION_LIST_OF_ROLE_ACCESS_GET_SUCCESS,
									false),
							dtoScreenDetailList);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.TRANSACTION_LIST_OF_ROLE_ACCESS_NOT_FOUND,
									false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ACCESS_ROLE_NOT_FOUND, false));
		}
		return responseMessage;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/exportMasterData", method = RequestMethod.GET)
	public void exportMasterData(HttpServletRequest request, HttpServletResponse response) {
		serviceHome.exportMasterData(request, response);
	}
	
	@RequestMapping(value = "/importMasterData",consumes = { "multipart/mixed", "multipart/form-data" }, method = RequestMethod.POST)
	public @ResponseBody ResponseMessage importMasterData(@RequestParam(value = "languageOrientation", required = true) String languageOrientation, 
		   @RequestParam(value = "languageName", required = true) String languageName, 
			@RequestParam(value = "file", required = true) MultipartFile file) {
		ResponseMessage responseMessage = null;
		boolean response = serviceHome.importMasterData(languageName, languageOrientation, file);
		if (response) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_FILE_IMPORT_SUCCESSFULLY, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_UPLOAD_OPERTATION_FAILED, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/importCompanyMasterData",consumes = { "multipart/mixed", "multipart/form-data" }, method = RequestMethod.POST)
	public @ResponseBody ResponseMessage importCompanyMasterData(@RequestParam(value = "languageOrientation", required = true) String languageOrientation, 
		   @RequestParam(value = "languageName", required = true) String languageName, 
		   @RequestParam(value = "file", required = true) MultipartFile file,@RequestParam(value = "dbNames", required = true) String[] dbNames) {
		ResponseMessage responseMessage = null;
		Map<String, String> response = serviceHome.importCompanyMasterData(languageName, languageOrientation, file, dbNames);
		if (response!=null && !response.isEmpty()) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FINANCIAL_FILE_IMPORT_SUCCESSFULLY, false),response);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FINANCIAL_UPLOAD_OPERTATION_FAILED, false));
		}
		return responseMessage;
	}
	
	
	
	@RequestMapping(value = "/importUserMasterDataForUpdateLanguage",consumes = { "multipart/mixed", "multipart/form-data" }, method = RequestMethod.POST)
	public @ResponseBody ResponseMessage importMasterDataForUpdateLanguage(
			@RequestParam(value = "languageId", required = true) int languageId, 
		    @RequestParam(value = "languageOrientation", required = true) String languageOrientation, 
			@RequestParam(value = "languageName", required = true) String languageName, 
			@RequestParam(value = "file", required = false) MultipartFile file) {
		ResponseMessage responseMessage = null;
		boolean response = serviceHome.updateUserLanguage(languageId,file,languageName,languageOrientation);
		if (response) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_FILE_IMPORT_SUCCESSFULLY, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_UPLOAD_OPERTATION_FAILED, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/exportUserMasterDataForUpdateLanguage", method = RequestMethod.POST)
	public void exportMasterDataForUpdateLanguage(HttpServletRequest request, HttpServletResponse response,@RequestBody DtoLanguage dtoLanguage) {
		serviceHome.exportMasterDataForUpdateLanguage(request, response,dtoLanguage.getLanguageId());
	}
	
	@RequestMapping(value = "/importCompanyMasterDataForUpdateLanguage",consumes = { "multipart/mixed", "multipart/form-data" }, method = RequestMethod.POST)
	public @ResponseBody ResponseMessage importCompanyMasterDataForUpdateLanguage(@RequestParam(value = "languageOrientation", required = true) String languageOrientation, 
		   @RequestParam(value = "languageName", required = true) String languageName,@RequestParam(value = "languageId", required = true) Integer languageId, 
		   @RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "dbNames", required = true) String[] dbNames) {
		System.out.println("hit on moduleeee");
		ResponseMessage responseMessage = null;
		Map<String, String> response = serviceHome.importCompanyMasterDataForUpdateLanguage(languageName, languageOrientation, file, dbNames,languageId);
		if (response!=null && !response.isEmpty()) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FINANCIAL_FILE_IMPORT_SUCCESSFULLY, false),response);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FINANCIAL_UPLOAD_OPERTATION_FAILED, false));
		}
		return responseMessage;
	}

	
 
}

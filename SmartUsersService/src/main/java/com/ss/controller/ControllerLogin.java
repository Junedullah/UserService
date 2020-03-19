/**SmartSoftware User - Service */
/**
 * Description: ControllerLogin
 * Name of Project: SmartSoftware
 * Created on: March 14, 2020
 * Modified on: March 16, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserDetail;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoUser;
import com.ss.model.dto.DtoUserLogin;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserSession;
import com.ss.service.ServiceForgotPassword;
import com.ss.service.ServiceLogin;
import com.ss.service.ServiceLoginOtp;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUser;
import com.ss.service.ServiceUserMacAddress;
import com.ss.util.UtilFindIPAddress;


@RestController
@RequestMapping("/login")
public class ControllerLogin {

	private static final Logger LOGGER = Logger.getLogger(ControllerLogin.class);

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	RepositoryUserSession repositoryUserSession;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceLoginOtp serviceLoginOtp;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	ServiceUserMacAddress serviceUserIp;

	@Autowired
	ServiceForgotPassword serviceForgotPassword;

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	RepositoryException repositoryException;
	
	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;
	
	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	private static final String USER_ID = "userid";
	
	/**
	 * @description : Tt checks if ip exist in user ip table
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkIpBeforeLogin", method = RequestMethod.GET)
	public ResponseMessage test(HttpServletRequest request) 
	{
		ResponseMessage responseMessage = null;
		LOGGER.info("Application's check Ip Before Login service");
		String userIp = UtilFindIPAddress.getUserIp(request);
		if (userIp != null) 
		{
			LOGGER.info("User ip address " + userIp);
			boolean result = serviceUserIp.allowedIpRequest(userIp);
			Map<String, Boolean> map = new HashMap<>();
			map.put("validIpAddress", result);
			if (result) 
			{
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.VALID_USER_IP, false), map);
			}
			else 
			{
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.INVALID_USER_IP, false), map);
			}
		}
		else 
		{
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_IP_NOT_FOUND, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : It sends otp to user if credentials are valid
	 * @param dtoUser
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginUserForOtp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage loginUser(@RequestBody DtoUser dtoUser, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		User user = repositoryUser.findByusernameAndIsDeleted(dtoUser.getUserName(), false);
		if (user != null && user.isActive()) {
			
			boolean allowedIp=true;
			/*if(user.getIpChecked()){
            	allowedIp= serviceUserIp.checkAllowedUserIpRequest(request, dtoUser.getUserName());
            }
			*/
			if (!allowedIp) {
				responseMessage = new ResponseMessage(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.INVALID_USER_IP_OR_BLOCKED, false));
			}
			else {
			Boolean session = sessionManager.validateUserSessionExistOrNotByIp(dtoUser);
			if(session){
				DtoUser dtoUserLogin = serviceUser.sendOTPtoUser(dtoUser);
				if (dtoUserLogin != null) {
						dtoUserLogin.setRole(user.getRole().getRoleName());
					if (dtoUserLogin.getMessageType() == null) {
						if (dtoUserLogin.getSmsAuthentication().equalsIgnoreCase("Y")) {
							responseMessage = new ResponseMessage(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED,
									serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_OTP_SENT, false),
									dtoUserLogin);
						} else if (dtoUserLogin.getSmsAuthentication().equalsIgnoreCase("N")) {
							
							//set company count for user
							List<UserCompanyRelation> listUserCompanyRelation=
									repositoryUserCompanyRelation.findByUserUserIdAndIsDeleted(dtoUserLogin.getUserId(), false);
							dtoUserLogin.setCompanyCount(listUserCompanyRelation.size());
							
							responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
									serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LOGGED_IN_SUCCESS, false),
									dtoUserLogin);
						}
					} else {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted(dtoUserLogin.getMessageType(), false));
					}

				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
				}		
			}
			else{
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SESSION_ALREADY_EXIST, false));
				  }
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.INVALID_USERNAME_AND_PASSWORD, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Verify user otp for login 
	 * //@param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/verifyOtpAuthentication", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage verifyOtpAuthentication(@RequestBody DtoUserLogin dtoUserLogin, HttpServletRequest request,
			HttpSession httpSession) {
		String langId = request.getHeader("langId");
		LOGGER.info(" verify Otp Authentication service :::::: ");
		ResponseMessage responseMessage = null;
		DtoUser dtoUser = null;
		User userExist = repositoryUser.findByUserIdAndIsDeleted(dtoUserLogin.getUserId(), false);
		if (userExist != null && userExist.isActive()) {
			
			dtoUserLogin = serviceLoginOtp.validateUserOTP(dtoUserLogin);
			
			if (dtoUserLogin.getMessageType() == null) {
				if (dtoUserLogin.isOtpMatched()!=null && dtoUserLogin.isOtpMatched()) 
				{
					if (userExist.getIsDeleted()) {
						responseMessage = new ResponseMessage(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
								serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
					} else {
						String sessionId = sessionManager.getUniqueSessionId(dtoUserLogin.getUserId(),dtoUserLogin.getIpAddress());
						UserDetail userDetail = repositoryUserDetail.findByUserUserId(dtoUserLogin.getUserId());
						dtoUser = new DtoUser(userExist, userDetail, langId);
						dtoUser.setSession(sessionId);
						dtoUser.setRole(userExist.getRole().getRoleName());
						httpSession.setAttribute("loggedInUserId", dtoUserLogin.getUserId());
						
						//set company count for user
						List<UserCompanyRelation> listUserCompanyRelation=
								repositoryUserCompanyRelation.findByUserUserIdAndIsDeleted(dtoUser.getUserId(), false);
						dtoUser.setCompanyCount(listUserCompanyRelation.size());
						
						responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
								serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_OTP_VERIFIED, false), dtoUser);
					}
				} 
				else 
				{
					if (dtoUserLogin.isOtpMaxLimitReached() !=null && dtoUserLogin.isOtpMaxLimitReached()) 
					{
						responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
								serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_OTP_MAX_LIMIT_REACHED, false),
								dtoUserLogin);
					} 
					else {
						responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
								serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_OTP_FAILED, false), dtoUserLogin);
					}
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(dtoUserLogin.getMessageType(), false),
						dtoUserLogin);
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));

		}
		return responseMessage;
	}
	
	/**
	 * @description : Reset password and send email 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap forgotPassword(@RequestBody DtoUser user, HttpServletRequest request) {
		LOGGER.info(" forgot password service :::::: ");
		ResponseMessage responseMessage = null;
		User userExist = repositoryUser.findByEmailAndIsDeleted(user.getEmail(), false);
		if (userExist != null) {
			boolean result = serviceForgotPassword.resetPasswordAndSendEmail(userExist);
			if (result) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORGOT_PASSWORD_EMAIL_SENT, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORGOT_PASSWORD_EMAIL_NOT_SENT, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
		}
		return new ModelMap("response", responseMessage);
	}
	
	@RequestMapping(value = "/checkCompanyAccess", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage checkCompanyAccess(@RequestBody DtoUser dtoUser, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		dtoUser = serviceLogin.checkValidCompanyAceess(dtoUser);
		if (dtoUser.getCompanyTenantId()!=null) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LOGGED_IN_SUCCESS, false), dtoUser);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.DO_NOT_HAVE_COMPANY_ACCESS, false), false);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/checkCompanyAccessForOtherModule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage checkCompanyAccessForOtherModule(HttpServletRequest request) 
	{
		ResponseMessage responseMessage = null;
		String sessionId = httpServletRequest.getHeader("session");
		int userId=Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		UserSession session = repositoryUserSession.findByUserUserIdAndSessionAndIsDeleted(userId, sessionId,
				false);
		LOGGER.debug("Session: " + session);
		if (session != null) 
		{
			boolean flag = serviceLogin.checkCompanyAccessForOtherModule();
			if (flag) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LOGGED_IN_SUCCESS, false), flag);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.DO_NOT_HAVE_COMPANY_ACCESS, false), false);
			}
		}
		else
		{
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false), false);
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getCompanyDatabaseCredential", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getCompanyDatabaseCredential(HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		DtoCompany dtoCompany = serviceLogin.getCompanyDatabaseCredential();
		if (dtoCompany != null) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_GET_SUCCESSFULLY, false), dtoCompany);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.DO_NOT_HAVE_COMPANY_ACCESS, false), false);

		}
		return responseMessage;
	}
 }

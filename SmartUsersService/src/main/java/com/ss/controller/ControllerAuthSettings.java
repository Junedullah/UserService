/**SmartSoftware User - Service */
/**
 * Description: ControllerAuthSettings
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 27, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoAuthorizationDetail;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.model.dto.DtoUserGroup;
import com.ss.model.dto.DtoWeekDay;
import com.ss.service.ServiceAuthSettings;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUserGroup;

@RestController
@RequestMapping("/authSettings")
public class ControllerAuthSettings {
		
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ControllerAuthSettings.class);
	
	@Autowired
	ServiceUserGroup serviceUserGroup;
	
	@Autowired
	SessionManager sessionManager;
	
	@Autowired
	ServiceResponse serviceResponse;
	
	@Autowired
	ServiceAuthSettings serviceAuthSettings;
	
	
	
	/**
	 * @description : Save user Auth Setting
	 * @param request
	 * @param dtoAuthorizationDetail
	 * @return
	 */
	@RequestMapping(value = "/saveAuthSetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage saveAuthSetting(HttpServletRequest request,
			@RequestBody DtoAuthorizationDetail dtoAuthorizationDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			serviceAuthSettings.saveAuthSettings(dtoAuthorizationDetail);
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
					.getMessageByShortAndIsDeleted(MessageLabel.AUTHORIZATION_SETTING_DETAIL_SAVED, false));

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Update user Auth Setting
	 * @param request
	 * @param dtoAuthorizationDetail
	 * @return
	 */
	@RequestMapping(value = "/updateAuthSetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage updateAuthSetting(HttpServletRequest request,
			@RequestBody DtoAuthorizationDetail dtoAuthorizationDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			serviceAuthSettings.updateAuthSettings(dtoAuthorizationDetail);
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
					.getMessageByShortAndIsDeleted(MessageLabel.AUTHORIZATION_SETTING_DETAIL_UPDATED, false));

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	/**
	 * @param request
	 * @param dtoAuthorizationDetail
	 * @return
	 */
	@RequestMapping(value = "/deleteAuthSetting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage deleteAuthSetting(HttpServletRequest request,
			@RequestBody DtoAuthorizationDetail dtoAuthorizationDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			if(dtoAuthorizationDetail.getIds()!=null && !dtoAuthorizationDetail.getIds().isEmpty() )
			{
				boolean result = serviceAuthSettings.deleteAuthSettings(dtoAuthorizationDetail);
				if(result)
				{
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
							.getMessageByShortAndIsDeleted(MessageLabel.AUTH_SETTING_DELETED_SUCCESSFULLY, false));
				}
				else
				{
					responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
							HttpStatus.INTERNAL_SERVER_ERROR,
							this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false), null);
				}
			}
			else
			{
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LIST_IS_EMPTY, false));
			}
			
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	/**
	 * @description : Get User Group List By Company Id
	 * @param request
	 * @param dtoCompany
	 * @return
	 */
	@RequestMapping(value = "/getUserGroupListByCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getUserGroupListByCompanyId(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			List<DtoUserGroup> dtoUserGroupList = this.serviceUserGroup.getUserGroupListByCompanyId(dtoCompany.getId());
			if (dtoUserGroupList != null && !dtoUserGroupList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_GROUP_FATCHED, false), dtoUserGroupList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	/**
	 * @description : Get week days
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDays", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getDays(HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			List<DtoWeekDay> dtoWeekDayList = this.serviceAuthSettings.getWeekDays();
			if (dtoWeekDayList != null && !dtoWeekDayList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WEEK_DAYS_LIST_SUCCESS, false),
						dtoWeekDayList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false), null);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Get User List By User Group Id
	 * @param request
	 * @param dtoUserGroup
	 * @return
	 */
	@RequestMapping(value = "/getUserListByUserGroupId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getUserListByUserGroupId(HttpServletRequest request,
			@RequestBody DtoUserGroup dtoUserGroup) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			List<DtoUser> dtoUserList = this.serviceUserGroup.getUserListByUserGroupId(dtoUserGroup.getId());
			if (dtoUserList != null && !dtoUserList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoUserList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	
	
	/**
	 * @description : Get User List By Company And User Group
	 * @param request
	 * @param dtoUserGroup
	 * @return
	 */
	@RequestMapping(value = "/getUserListByCompanyAndUserGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getUserListByCompanyAndUserGroup(HttpServletRequest request,
			@RequestBody DtoUserGroup dtoUserGroup) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			List<DtoUser> dtoUserList = this.serviceUserGroup.getUserListByCompanyAndUserGroup(dtoUserGroup);
			if (dtoUserList != null && !dtoUserList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoUserList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/getAuthSettingById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getAuthSettingById(HttpServletRequest request,
			@RequestBody DtoAuthorizationDetail dtoAuthorizationDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) {
			    dtoAuthorizationDetail = serviceAuthSettings.getAuthSettingsById(dtoAuthorizationDetail.getId());
				if(dtoAuthorizationDetail!=null)
				{
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
							.getMessageByShortAndIsDeleted(MessageLabel.AUTH_SETTING_GET_SUCCESSFULLY, false),dtoAuthorizationDetail);
				}
				else
				{
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(),
							HttpStatus.NOT_FOUND,
							this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
				}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllAuthSettings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getAllAuthSettings(HttpServletRequest request,
			@RequestBody DtoAuthorizationDetail dtoAuthorizationDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = this.sessionManager.validateUserSessionId(request);
		if (session != null) 
		{
			    DtoSearch dtoSearch = serviceAuthSettings.getAllAuthSettings(dtoAuthorizationDetail);
			    @SuppressWarnings("unchecked")
				List<DtoAuthorizationDetail> list = (List<DtoAuthorizationDetail>) dtoSearch.getRecords();
				if(list!=null && !list.isEmpty())
				{
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
							.getMessageByShortAndIsDeleted(MessageLabel.AUTH_SETTING_LIST_GET_SUCCESSFULLY, false),dtoSearch);
				}
				else
				{
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(),
							HttpStatus.NOT_FOUND,
							this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
				}
		} 
		else 
		{
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
}

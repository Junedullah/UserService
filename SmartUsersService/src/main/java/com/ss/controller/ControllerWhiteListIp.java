/**SmartSoftware User - Service */
/**
 * Description: ControllerWhiteListIp
 * Name of Project: SmartSoftware
 * Created on: March 14, 2020
 * Modified on: March 14, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.controller;

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
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoWhiteListIp;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceWhiteListIp;


@RestController
@RequestMapping("/ip")
public class ControllerWhiteListIp {

	private static final Logger LOGGER = Logger.getLogger(ControllerWhiteListIp.class);

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceWhiteListIp serviceWhiteListIp;
	
	/**
	 * @description : Add White List IP
	 * @param dtoWhiteListIp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addWhiteListIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage addWhiteListIP(@RequestBody DtoWhiteListIp dtoWhiteListIp, HttpServletRequest request) {
		LOGGER.info("inside addWhiteListIP method");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoWhiteListIp = this.serviceWhiteListIp.addWhiteListIP(dtoWhiteListIp);
			if (dtoWhiteListIp != null) {
				if (dtoWhiteListIp.getMessageType() == null) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_SUCCESS, false),
							dtoWhiteListIp);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
							this.serviceResponse.getMessageByShortAndIsDeleted(dtoWhiteListIp.getMessageType(), false),
							dtoWhiteListIp);
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}
	
	/**
	 * @description : Search White List IPs
	 * @param dtoSearch
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchWhiteListIPs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage searchWhiteListIPs(@RequestBody DtoSearch dtoSearch, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoSearch = this.serviceWhiteListIp.searchWhiteListIPs(dtoSearch);
			if (dtoSearch != null && dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_FETCH_SUCCESS, false),
						dtoSearch);
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
	 * @description : Get White List IP By Id
	 * @param dtoWhiteListIp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWhiteListIPById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getWhiteListIPById(@RequestBody DtoWhiteListIp dtoWhiteListIp, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoWhiteListIp = this.serviceWhiteListIp.getWhiteListIPById(dtoWhiteListIp.getWhitelistIpId());
			if (dtoWhiteListIp != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_FETCH_SUCCESS, false),
						dtoWhiteListIp);
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
	 * @description : Update White List IP
	 * @param dtoWhiteListIp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateWhiteListIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json; charset=UTF-8")
	public ResponseMessage updateWhiteListIP(@RequestBody DtoWhiteListIp dtoWhiteListIp, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoWhiteListIp = this.serviceWhiteListIp.updateWhiteListIPById(dtoWhiteListIp);
			if (dtoWhiteListIp != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_UPDATE_SUCCESS, false),
						dtoWhiteListIp);
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
	 * @description : Delete White List IP
	 * @param dtoWhiteListIp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteWhiteListIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage deleteWhiteListIP(@RequestBody DtoWhiteListIp dtoWhiteListIp, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			if (dtoWhiteListIp.getDeleteIds() != null && !dtoWhiteListIp.getDeleteIds().isEmpty()) {
				boolean status = this.serviceWhiteListIp.deleteWhiteListIPById(dtoWhiteListIp.getDeleteIds());
				if (status) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_DELETE_SUCCESS, false));
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_MODIFIED.value(), HttpStatus.NOT_MODIFIED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
				}
			} else {
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
	 * @description : Block Unblock White List IP
	 * @param dtoWhiteListIp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/blockUnblockWhiteListIP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage blockUnblockWhiteListIP(@RequestBody DtoWhiteListIp dtoWhiteListIp,
			HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoWhiteListIp = this.serviceWhiteListIp.blockUnblockWhiteListIP(dtoWhiteListIp);
			if (dtoWhiteListIp != null && dtoWhiteListIp.getIsActive()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_ACTIVATED_SUCCESS, false),
						dtoWhiteListIp);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.WHITE_LIST_IP_BLOCKED_SUCCESS, false),
						dtoWhiteListIp);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}
}

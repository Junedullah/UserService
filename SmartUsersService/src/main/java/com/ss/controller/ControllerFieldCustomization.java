/**SmartSoftware User - Service */
/**
 * Description: ontrollerFieldCustomization
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoFieldCustomization;
import com.ss.service.ServiceFieldCustomization;
import com.ss.service.ServiceResponse;

@RestController
@RequestMapping("/field-customization")
public class ControllerFieldCustomization {

	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerFieldCustomization.class);

	@Autowired
	SessionManager sessionManager;

	@Autowired
	private ServiceFieldCustomization serviceFieldCustomization;

	@Autowired
	private ServiceResponse serviceResponse;

	/**
	 * @description : It will return all the Header and their side bar
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseMessage save(@RequestBody DtoFieldCustomization dtoFieldCustomization, HttpServletRequest request) {

		LOGGER.info("Save Field Type");

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoFieldCustomization responseDtoFieldCustomization = serviceFieldCustomization
					.saveOrUpdateFieldCustomization(dtoFieldCustomization);
			if (responseDtoFieldCustomization != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED, serviceResponse
						.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_SAVE_SUCCESS, false),
						responseDtoFieldCustomization);

			} else {
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse
								.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_SAVE_ERROR, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}

	/**
	 * @description : Update FieldCustomization
	 * @param request
	 * @param dtoFieldCustomization
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseMessage update(HttpServletRequest request,
			@RequestBody DtoFieldCustomization dtoFieldCustomization) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoFieldCustomization = serviceFieldCustomization.saveOrUpdateFieldCustomization(dtoFieldCustomization);
			if (dtoFieldCustomization != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED, serviceResponse
						.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_UPDATE_SUCCESS, false),
						dtoFieldCustomization);
			} else {
				responseMessage = new ResponseMessage(
						HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse
								.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_UPDATE_ERROR, false),
						dtoFieldCustomization);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}

	/**
	 * @description : Delete company
	 * @param request
	 * @param dtoCompany
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseMessage deleteCompany(HttpServletRequest request,
			@RequestBody DtoFieldCustomization dtoFieldCustomization) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			if (dtoFieldCustomization != null && dtoFieldCustomization.getId() > 0) {
				DtoFieldCustomization dtoFieldCustomization2 = serviceFieldCustomization
						.deleteFieldCustomization(dtoFieldCustomization);
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED, serviceResponse
						.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_DELETE_SUCCESS, false),
						dtoFieldCustomization2);

			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_DELETE_ERROR,
								false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}

	/**
	 * get lead by phone
	 * 
	 * @param request
	 * @param phone
	 * @return
	 */

	@RequestMapping(value = "/getByCodeAndUser", method = RequestMethod.POST)
	public ResponseMessage getByCode(HttpServletRequest request, @RequestBody DtoFieldCustomization dtoFieldCustomization) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {

			dtoFieldCustomization = serviceFieldCustomization.getByCodeAndUserId(dtoFieldCustomization.getCode(), dtoFieldCustomization.getUserId());

			if (dtoFieldCustomization != null)
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK, serviceResponse
						.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_GET_SUCCESS, false),
						dtoFieldCustomization);
			else
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FIELD_CUSTOMIZATION_GET_ERROR,
								false));

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}
}

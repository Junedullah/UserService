
package com.ss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoGrid;
import com.ss.model.dto.DtoSearch;
import com.ss.service.ServiceField;
import com.ss.service.ServiceGrid;
import com.ss.service.ServiceResponse;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Field database table.
 * Name of Project: SmartUserSoftware
 * Created on: march 11, 2020
 * Modified on: march 11, 2020 11:19:38 AM
 * @author shahnawaz Ahmad
 * Version: 
 */
@RestController
@RequestMapping("/fields")
public class ControllerField {

	@Autowired
	ServiceField serviceField;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	
	/**
	 * @description : Create Grid
	 * @param request
	 * @param dtomodule
	 * @return
	 */
	@RequestMapping(value = "/createfields", method = RequestMethod.POST)
	public ResponseMessage createfields(HttpServletRequest request, @RequestBody DtoFieldDetail dtoFieldDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoFieldDetail = serviceField.saveOrUpdate(dtoFieldDetail);
			if (dtoFieldDetail != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoFieldDetail);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoFieldDetail);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllField", method = RequestMethod.PUT)
	public ResponseMessage getAllField(HttpServletRequest request, @RequestBody DtoFieldDetail dtoFieldDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceField.getAllField(dtoFieldDetail);
			if (dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_ALL, false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_LIST_NOT_GETTING, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseMessage delete(HttpServletRequest request, @RequestBody DtoFieldDetail dtoFieldDetail) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoFieldDetail.getIds());
		if (session != null) {
				if (dtoFieldDetail.getIds() != null && !dtoFieldDetail.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceField.delete(dtoFieldDetail.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoFieldDetail);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoFieldDetail);
					} else {
						responseMessage = new ResponseMessage(HttpStatus.NOT_ACCEPTABLE.value(),
								HttpStatus.NOT_ACCEPTABLE,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_ACCEPTABLE", false));
					}

				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted("EMPTY", false));
				}
		}
		return responseMessage;

	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoFieldDetail dtoFieldDetail) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoFieldDetail = serviceField.getById(dtoFieldDetail.getFieldId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoFieldDetail);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}


		}  

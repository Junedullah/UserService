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
import com.ss.model.dto.DtoScreenCategory;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoScreenMenu;
import com.ss.model.dto.DtoSearch;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceScreen;
import com.ss.service.ServiceScreenCategory;

@RestController
@RequestMapping("/screenCategory")
public class ControllerScreenCategory {
	
	@Autowired
	SessionManager sessionManager;
	
	@Autowired
	ServiceResponse serviceResponse;
	
	@Autowired
	ServiceScreenCategory serviceScreenCategory;
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseMessage createScreenCategory(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenCategory = serviceScreenCategory.saveOrUpdate(dtoScreenCategory);
			if (dtoScreenCategory != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoScreenCategory);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoScreenCategory);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllScreenCategory", method = RequestMethod.PUT)
	public ResponseMessage getAllScreenCategory(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceScreenCategory.getAllScreenCategory(dtoScreenCategory);
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
	public ResponseMessage delete(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoScreenCategory.getIds());
		if (session != null) {
				if (dtoScreenCategory.getIds() != null && !dtoScreenCategory.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceScreenCategory.delete(dtoScreenCategory.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoScreenCategory);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoScreenCategory);
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
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoScreenCategory = serviceScreenCategory .getById(dtoScreenCategory.getScreenCategoryId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoScreenCategory);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

	
}

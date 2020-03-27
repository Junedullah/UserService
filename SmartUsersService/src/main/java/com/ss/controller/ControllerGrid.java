
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
import com.ss.model.dto.DtoGrid;
import com.ss.model.dto.DtoSearch;
import com.ss.service.ServiceGrid;
import com.ss.service.ServiceResponse;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Module database table.
 * Name of Project: SmartUserSoftware
 * Created on: march 11, 2020
 * Modified on: march 11, 2020 11:19:38 AM
 * @author shahnawaz Ahmad
 * Version: 
 */
@RestController
@RequestMapping("/grid")
public class ControllerGrid {

	@Autowired
	ServiceGrid serviceGrid;

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
	@RequestMapping(value = "/createGrid", method = RequestMethod.POST)
	public ResponseMessage createGrid(HttpServletRequest request, @RequestBody DtoGrid dtoGrid) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoGrid = serviceGrid.saveOrUpdateGrid(dtoGrid);
			if (dtoGrid != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoGrid);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoGrid);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllGrid", method = RequestMethod.PUT)
	public ResponseMessage getAllGrid(HttpServletRequest request, @RequestBody DtoGrid dtoGrid) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceGrid.getAllGrid(dtoGrid);
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
	public ResponseMessage delete(HttpServletRequest request, @RequestBody DtoGrid dtoGrid) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoGrid.getIds());
		if (session != null) {
				if (dtoGrid.getIds() != null && !dtoGrid.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceGrid.delete(dtoGrid.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoGrid);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoGrid);
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
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoGrid dtoGrid) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoGrid = serviceGrid.getById(dtoGrid.getId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoGrid);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}


		} 

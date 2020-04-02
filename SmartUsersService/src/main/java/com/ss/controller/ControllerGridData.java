
package com.ss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.ss.model.dto.DtoGridData;
import com.ss.model.dto.DtoSearch;
import com.ss.service.ServiceGrid;
import com.ss.service.ServiceGridData;
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
@RequestMapping("/gridData")
public class ControllerGridData {
	
	private static final Logger logger = Logger.getLogger(ControllerField.class);


	@Autowired
	ServiceGridData serviceGridData;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	
	/**
	 * @description : Create GridData
	 * @param request
	 * @param dtoGridData
	 * @return
	 */
	@RequestMapping(value = "/createGridData", method = RequestMethod.POST)
	public ResponseMessage createGridData(HttpServletRequest request, @RequestBody DtoGridData dtoGridData) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoGridData = serviceGridData.saveOrUpdateGridData(dtoGridData);
			if (dtoGridData != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoGridData);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoGridData);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllGridData", method = RequestMethod.PUT)
	public ResponseMessage getAllGridData(HttpServletRequest request, @RequestBody DtoGridData dtoGridData) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceGridData.getAllGridData(dtoGridData);
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
	public ResponseMessage delete(HttpServletRequest request, @RequestBody DtoGridData dtoGridData) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoGridData.getIds());
		if (session != null) {
				if (dtoGridData.getIds() != null && !dtoGridData.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceGridData.delete(dtoGridData.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoGridData);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoGridData);
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
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoGridData dtoGridData) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoGridData = serviceGridData.getById(dtoGridData.getGridDataId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoGridData);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

	
	@RequestMapping(value = "/changeColumnVisibleStatus", method = RequestMethod.PUT)
	public ResponseMessage changeColumnVisibleStatus(HttpServletRequest request, @RequestBody DtoGridData dtoGridData)
			throws Exception {

		logger.info("Change column visible status method called!!");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);

		if (session != null) {
			DtoGridData dtoSearch = serviceGridData.changeVisible(dtoGridData);
			if (dtoSearch != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_UPDATED_SUCCESSFULLY", false),
						dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_NOT_UPDATED", false), dtoSearch);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		}
		logger.debug("Change visible status:" + responseMessage.getMessage());
		return responseMessage;
	}
	@RequestMapping(value = "/hideAllColumns", method = RequestMethod.PUT)
	public ResponseMessage hideAllColumns(HttpServletRequest request, @RequestBody DtoGridData dtoGridData)
			throws Exception {

		logger.info("Hide All Columns method Called!!");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);

		if (session != null) {
			DtoGridData dtoGridDataService = serviceGridData.hideAllColumns(dtoGridData.getGridId());
			if (dtoGridDataService != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_UPDATED_SUCCESSFULLY", false),
						dtoGridDataService);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_NOT_UPDATED", false),
						dtoGridDataService);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		}
		logger.debug("Hide All Columns:" + responseMessage.getMessage());
		return responseMessage;
	}


	@RequestMapping(value = "/showAllColumns", method = RequestMethod.PUT)
	public ResponseMessage showAllColumns(HttpServletRequest request, @RequestBody DtoGridData dtoGridData)
			throws Exception {

		logger.info("Hide All Columns method Called!!");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);

		if (session != null) {
			DtoGridData dtoGridDataService = serviceGridData.showAllColumns(dtoGridData.getGridId());
			if (dtoGridDataService != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_UPDATED_SUCCESSFULLY", false),
						dtoGridDataService);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_NOT_UPDATED", false),
						dtoGridDataService);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		}
		logger.debug("Hide All Columns:" + responseMessage.getMessage());
		return responseMessage;
	}

	@RequestMapping(value = "/resetGrid", method = RequestMethod.PUT)
	public ResponseMessage resetGrid(HttpServletRequest request, @RequestBody DtoGridData dtoGridData)
			throws Exception {

		logger.info("Reset Grid Method Called!!");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);

		if (session != null) {
			DtoGridData dtoGridDataService = serviceGridData.resetGrid(dtoGridData.getGridId());
			if (dtoGridDataService != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_UPDATED_SUCCESSFULLY", false),
						dtoGridDataService);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("GRID_DATA_NOT_UPDATED", false),
						dtoGridDataService);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		}
		logger.debug("Reset Grid:" + responseMessage.getMessage());
		return responseMessage;
	}

		}  

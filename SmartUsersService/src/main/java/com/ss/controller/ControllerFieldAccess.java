
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.FieldAccess;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoFieldAccess;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryFieldAccess;
import com.ss.service.ServiceFieldAccess;
import com.ss.service.ServiceResponse;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the FieldAccess database table.
 * Name of Project: SmartUserSoftware
 * Created on: march 11, 2020
 * Modified on: march 11, 2020 11:19:38 AM
 * @author shahnawaz Ahmad
 * Version: 
 */
@RestController
@RequestMapping("/fieldAccess")
public class ControllerFieldAccess {
	
	private static final Logger LOGGER = Logger.getLogger(ControllerFieldAccess.class);


	@Autowired
	ServiceFieldAccess serviceFieldAccess;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;
	
	@Autowired
	RepositoryFieldAccess repositoryFieldAccess;


	
	/**
	 * @description : Create FieldsAccess
	 * @param request
	 * @param dtoFieldAccess
	 * @return
	 */
	@RequestMapping(value = "/createFieldAccess", method = RequestMethod.POST)
	public ResponseMessage createFieldAccess(HttpServletRequest request, @RequestBody DtoFieldAccess dtoFieldAccess) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoFieldAccess = serviceFieldAccess.saveOrUpdate(dtoFieldAccess);
			if (dtoFieldAccess != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoFieldAccess);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoFieldAccess);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getAllFieldAccess", method = RequestMethod.PUT)
	public ResponseMessage getAllFieldAccess(HttpServletRequest request, @RequestBody DtoFieldAccess dtoFieldAccess) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceFieldAccess.getAllFieldAccess(dtoFieldAccess);
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
	public ResponseMessage delete(HttpServletRequest request, @RequestBody DtoFieldAccess dtoFieldAccess) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoFieldAccess.getIds());
		if (session != null) {
				if (dtoFieldAccess.getIds() != null && !dtoFieldAccess.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceFieldAccess.delete(dtoFieldAccess.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoFieldAccess);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoFieldAccess);
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
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoFieldAccess dtoFieldAccess) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoFieldAccess = serviceFieldAccess.getById(dtoFieldAccess.getFieldAccessId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoFieldAccess);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

	@RequestMapping(value = "/getAllFieldByCompanyId", method = RequestMethod.PUT)
	public ResponseMessage getAllFieldByCompanyId(HttpServletRequest request,
			@RequestBody DtoFieldAccess dtoFieldAccess) throws Exception {
		LOGGER.info("Get All Field By CompanyId Method");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceFieldAccess.getAllFieldsByCompanyId(dtoFieldAccess);
			if (dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("DEPARTMENT_GET_ALL", false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("DEPARTMENT_LIST_NOT_GETTING", false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		}
		LOGGER.debug("Get All Fields Method:" + responseMessage.getMessage());
		return responseMessage;
	}

	@RequestMapping(value = "/listOfFieldsByCompanyScreenModules", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfScreenModules(@RequestBody DtoScreenDetail dtoScreenDetail,
			HttpServletRequest request) {
		LOGGER.info("Get List of Modules");
		ResponseMessage responseMessage = null;
		List<DtoModule> dtoScreenDetailList = serviceFieldAccess.getAllScreenDetailList(dtoScreenDetail);
		if (dtoScreenDetailList != null) {
			if (dtoScreenDetailList.size() > 0) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAILS_SUCCESS", false),
						dtoScreenDetailList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAIL_NOT_FOUND", false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAILS_FAIL", false));
		}
		return responseMessage;
	}

	
	@RequestMapping(value = "/listOfScreenModulesByCompanyId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getListOfScreenModulesByAccessRole(@RequestBody DtoScreenDetail dtoScreenDetail,
			HttpServletRequest request) {
		LOGGER.info("Get List of Modules");
		ResponseMessage responseMessage = null;
		FieldAccess fieldAccess = repositoryFieldAccess
				.findByFieldAccessIdAndIsDeleted(dtoScreenDetail.getFieldAccessId(), false);
		if (fieldAccess != null) {
			List<DtoModule> dtoScreenDetailList = serviceFieldAccess.getAllScreenDetailListByComapnyId(fieldAccess,
					dtoScreenDetail);
			if (dtoScreenDetailList != null) {
				if (dtoScreenDetailList.size() > 0) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAILS_SUCCESS", false),
							dtoScreenDetailList);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAIL_NOT_FOUND", false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted("SCREEN_DETAILS_FAIL", false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted("ACCESS_ROLE_NOT_FOUND", false));
		}
		return responseMessage;
	}


		}  

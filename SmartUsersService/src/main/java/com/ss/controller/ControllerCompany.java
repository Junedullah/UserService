

package com.ss.controller;

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
import com.ss.model.Company;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryCompany;
import com.ss.service.ServiceCompany;
import com.ss.service.ServiceResponse;

/*SmartSoftware User - Service */
/**
 * Description: The persistent class for the country_master database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
@RestController
@RequestMapping("/company")
public class ControllerCompany {

	@Autowired
	ServiceCompany serviceCompnay;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	RepositoryCompany repositoryCompany;

	/**
	 * @description : Create Company
	 * @param request
	 * @param dtoCompany
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseMessage createCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {

			Company company = repositoryCompany.findTop1ByIsDeletedAndIsActiveAndName(false, true,
					dtoCompany.getName());
			if (company == null) {
				dtoCompany = serviceCompnay.saveOrUpdateCompany(dtoCompany);
				if (dtoCompany != null) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_CREATED, false),
							dtoCompany);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_NOT_CREATED, false),
							dtoCompany);
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_ALREADY_EXIST, false),
						dtoCompany);
			}

		
		  { responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(),
		  HttpStatus.UNAUTHORIZED,
		  serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN,
		  false)); }
		 }
		return responseMessage;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseMessage updateCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		/*
		 * UserSession session = sessionManager.validateUserSessionId(request); if
		 * (session != null) {
		 */
			dtoCompany = serviceCompnay.saveOrUpdateCompany(dtoCompany);
			if (dtoCompany != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_UPDATE_SUCCESS, false), dtoCompany);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_LIST_NOT_GETTING, false), dtoCompany);
			}
		/*
		 * } else { responseMessage = new
		 * ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
		 * serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN,
		 * false)); }
		 */
		return responseMessage;
	}
	
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseMessage deleteCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		/*
		 * UserSession session = sessionManager.validateUserSessionId(request); if
		 * (session != null) {
		 */
			if (dtoCompany.getIds() != null && !dtoCompany.getIds().isEmpty()) {
				DtoCompany dtoCompany2 = serviceCompnay.deleteCompany(dtoCompany.getIds());
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_DELETED, false), dtoCompany2);

			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LIST_IS_EMPTY, false));
			}

		/*
		 * } else { responseMessage = new
		 * ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
		 * serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN,
		 * false)); }
		 */
		return responseMessage;
	}
	
	
	/**
	 * @description : Get all companies
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/getAll", method = RequestMethod.PUT)
	public ResponseMessage getAllCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		/*UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {*/
			DtoSearch dtoSearch = serviceCompnay.getAllCompany(dtoCompany);
			if (dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_LIST_NOT_GETTING, false));
			}
		/*
		 * { responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(),
		 * HttpStatus.UNAUTHORIZED,
		 * serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN,
		 * false)); }
		 */
		return responseMessage;
	}
	
	
	
	}

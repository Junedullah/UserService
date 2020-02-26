package com.ss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoSearch;
import com.ss.service.ServiceLanguage;
import com.ss.service.ServiceResponse;

@Controller
@RequestMapping("/common")
public class ControllerCommon {
	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	ServiceLanguage serviceLanguage;

	/*@Autowired
	ServiceFieldAccess serviceFieldAccess;*/

	@RequestMapping(value = "/getAllLanaguage", method = RequestMethod.GET)
	public ResponseMessage getAllLanaguage(HttpServletRequest request) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session = null;//sessionManager.validateUserSessionId(request);
		if (session == null) {
			DtoSearch dtoSearch = serviceLanguage.getAllLanguage();
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
		return responseMessage;
	}
}

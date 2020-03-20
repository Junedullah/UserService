/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Module database table.
 * Name of Project: SmartUserSoftware
 * Created on: march 20, 2020
 * Modified on: march 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
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
import com.ss.model.UserSession;
import com.ss.model.dto.DtoScreenCategory;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoScreenMenu;
import com.ss.model.dto.DtoUser;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceScreen;

@RestController
@RequestMapping("/screen")
public class ControllerScreen {

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;
	
	@Autowired
	ServiceScreen serviceScreen;
	
	/**
	 * @description : Create ScreenCategory
	 * @param request
	 * @param dtoScreenCategory
	 * @return
	 */
	@RequestMapping(value = "/createScreen", method = RequestMethod.POST)
	public ResponseMessage createScreen(HttpServletRequest request, @RequestBody DtoScreenDetail dtoScreenDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenDetail = serviceScreen.saveOrUpdateScreen(dtoScreenDetail);
			if (dtoScreenDetail != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_CREATED, false), dtoScreenDetail);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_NOT_CREATED, false), dtoScreenDetail);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	

	/**
	 * @description : Create ScreenCategory
	 * @param request
	 * @param dtoScreenCategory
	 * @return
	 */
	@RequestMapping(value = "/createScreenCategory", method = RequestMethod.POST)
	public ResponseMessage createScreenCategory(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenCategory = serviceScreen.saveOrUpdateScreenCategory(dtoScreenCategory);
			if (dtoScreenCategory != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_CREATED, false), dtoScreenCategory);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_NOT_CREATED, false), dtoScreenCategory);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Create ScreenMenu
	 * @param request
	 * @param dtoScreenMenu
	 * @return
	 */
	@RequestMapping(value = "/createScreenMenu", method = RequestMethod.POST)
	public ResponseMessage createScreenMenu(HttpServletRequest request, @RequestBody DtoScreenMenu dtoScreenMenu) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenMenu = serviceScreen.saveOrUpdateScreenMenu(dtoScreenMenu);
			if (dtoScreenMenu != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_MENU_CREATED, false), dtoScreenMenu);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_MENU_NOT_CREATED, false), dtoScreenMenu);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Update ScreenCategory
	 * @param request
	 * @param dtoScreenCategory
	 * @return
	 */
	@RequestMapping(value = "/updateScreenCategory", method = RequestMethod.POST)
	public ResponseMessage updateScreenCategory(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenCategory = serviceScreen.saveOrUpdateScreenCategory(dtoScreenCategory);
			if (dtoScreenCategory != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_CREATED, false), dtoScreenCategory);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_CATEGORY_NOT_CREATED, false), dtoScreenCategory);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Create ScreenMenu
	 * @param request
	 * @param dtoScreenMenu
	 * @return
	 */
	@RequestMapping(value = "/updateScreenMenu", method = RequestMethod.POST)
	public ResponseMessage updateScreenMenu(HttpServletRequest request, @RequestBody DtoScreenMenu dtoScreenMenu) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenMenu = serviceScreen.saveOrUpdateScreenMenu(dtoScreenMenu);
			if (dtoScreenMenu != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_MENU_CREATED, false), dtoScreenMenu);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_MENU_NOT_CREATED, false), dtoScreenMenu);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

	/**
	 * @description : delete Screen Category
	 * @param request
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/deleteScreenCategory", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage deleteScreenCategory(HttpServletRequest request, @RequestBody DtoScreenCategory dtoScreenCategory) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			boolean response = serviceScreen.deleteScreenCategory(dtoScreenCategory);
			if (response) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MAC_ADDRESS_DELETED, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
			}
		}
		return responseMessage;
	}
	
	
	/**
	 * @description : delete Screen Category
	 * @param request
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/deleteScreenMenu", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage deleteScreenMenu(HttpServletRequest request, @RequestBody DtoScreenMenu dtoScreenMenu) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			boolean response = serviceScreen.deleteScreenMenu(dtoScreenMenu);
			if (response) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MAC_ADDRESS_DELETED, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
			}
		}
		return responseMessage;
	}
}

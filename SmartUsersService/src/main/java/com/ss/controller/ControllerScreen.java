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
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoScreenMenu;
import com.ss.model.dto.DtoSearch;
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
	 * @description : Create Screen
	 * @param request
	 * @param dtoScreenDetails
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
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoScreenDetail);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoScreenDetail);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Create Screen
	 * @param request
	 * @param dtoScreenDetails
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
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoScreenMenu);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoScreenMenu);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : Update Screen
	 * @param request
	 * @param dtoScreenDetails
	 * @return
	 */

	@RequestMapping(value = "/updateScreen", method = RequestMethod.POST)
	public ResponseMessage updateScreen(HttpServletRequest request, @RequestBody DtoScreenDetail dtoScreenDetail) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			dtoScreenDetail = serviceScreen.saveOrUpdateScreen(dtoScreenDetail);
			if (dtoScreenDetail != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoScreenDetail);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoScreenDetail);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : update Screen Menu
	 * @param request
	 * @param DtoScreenMenu
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
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_CREATED, false), dtoScreenMenu);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_NOT_CREATED, false), dtoScreenMenu);
			}
		
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	/**
	 * @description : get All Screen
	 * @param request
	 * @param DtoSearch
	 * @return
	 */	
	@RequestMapping(value = "/getAllScreen", method = RequestMethod.POST)
	public ResponseMessage getAllScreen(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
		 dtoSearch = serviceScreen.getAllScreen(dtoSearch);
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
	
	/**
	 * @description : get All Screen Menu
	 * @param request
	 * @param DtoSearch
	 * @return
	 */	
	@RequestMapping(value = "/getAllScreenMenu", method = RequestMethod.POST)
	public ResponseMessage getAllScreenMenu(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
		 dtoSearch = serviceScreen.getAllScreenMenu(dtoSearch);
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
	
	
	/**
	 * @description : delete Screen
	 * @param request
	 * @param DtoScreenDetails
	 * @return
	 */		
	@RequestMapping(value = "/deleteScreen", method = RequestMethod.PUT)
	public ResponseMessage deleteScreen(HttpServletRequest request, @RequestBody DtoScreenDetail dtoScreenDetail) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoScreenDetail.getIds());
		if (session != null) {
				if (dtoScreenDetail.getIds() != null && !dtoScreenDetail.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceScreen.deleteScreen(dtoScreenDetail.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoScreenDetail);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoScreenDetail);
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
	
	
	/**
	 * @description : delete Screen Menu
	 * @param request
	 * @param DtoScreenDetails
	 * @return
	 */		
	@RequestMapping(value = "/deleteScreenMenu", method = RequestMethod.PUT)
	public ResponseMessage deleteScreenMenu(HttpServletRequest request, @RequestBody DtoScreenMenu dtoScreenMenu) {

		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		List<Integer> inputIds = new ArrayList<>();
		//Integer pId = null;
		inputIds.addAll(dtoScreenMenu.getIds());
		if (session != null) {
				if (dtoScreenMenu.getIds() != null && !dtoScreenMenu.getIds().isEmpty()) {

					List<Integer> ids = (List<Integer>) serviceScreen.deleteScreenMenu(dtoScreenMenu.getIds());
					if (ids.isEmpty()) {
						responseMessage = new ResponseMessage(HttpStatus.DELETED.value(), HttpStatus.DELETED,
								serviceResponse.getMessageByShortAndIsDeleted("DELETED", false), dtoScreenMenu);
					} else if (ids.size() < inputIds.size()) {
						responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
								serviceResponse.getMessageByShortAndIsDeleted("NOT_FOUND", false), dtoScreenMenu);
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
	
	/**
	 * @description : getByID
	 * @param request
	 * @param DtoScreenDetails
	 * @return
	 */		
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
	public ResponseMessage getById(HttpServletRequest request, @RequestBody DtoScreenDetail dtoScreenDetail) throws Exception {
		ResponseMessage responseMessage = null;
		UserSession session =sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoScreenDetail = serviceScreen.getById(dtoScreenDetail.getScreenId());
			
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MODULE_GET_BY_ID, false), dtoScreenDetail);		
			
			} else {
				
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
}

/**
 * SST - SMART SOFTWARE TECH.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.controller;

import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.dto.DtoCountry;
import com.ss.repository.*;
import com.ss.service.ServiceHome;
import com.ss.service.ServiceLogin;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUser;
import com.ss.config.ResponseMessage;
import com.ss.constant.Constant;
import com.ss.constant.MessageLabel;
import com.ss.model.AccessRole;
import com.ss.model.Language;
import com.ss.model.User;
import com.ss.model.UserSession;
import com.ss.model.dto.*;
import com.ss.repository.*;
import com.ss.service.*;
import com.ss.util.UtilFindIPAddress;
import com.ss.util.UtilRandomKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Description: ControllerHome
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi Version:
 */
@RestController
@RequestMapping("/")
public class ControllerHome {

	private static final Logger LOGGER = Logger.getLogger(ControllerHome.class);

	@Autowired
	ServiceHome serviceHome;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepositoryAccessRole repositoryAccessRole;
	
	@Autowired
	RepositoryUserSession repositoryUserSession;
	
	@Autowired
	RepositoryLanguage repositoryLanguage;
	
	@Autowired
	RepositoryException repositoryException;
	
	@Autowired
	RepositoryScreen repositoryScreen;
	
	@Autowired
	RepositoryModule repositoryModule;
	
	@Autowired
	RepositoryFields repositoryFields;
	
	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;
	
	@Autowired
	RepositoryUserDraft repositoryUserDraft;
	
	/**
	 * @description : It will return all the labels related to screen and module
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 */
/*
	@RequestMapping(value = "/screenDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage loginScreen(@RequestBody DtoScreenDetail dtoScreenDetail, HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;
		DtoModule dtoModule = serviceLogin.getScreenDetail(dtoScreenDetail);
		if (dtoModule != null) {
			if (dtoModule.getMessageType() == null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.SCREEN_DETAILS_SUCCESS, false), dtoModule);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(dtoModule.getMessageType(), false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.DO_NOT_ACCESS_OF_SCREEN, false));
		}
		return responseMessage;
	}
*/

	/**
	 * @description : Get Country List
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCountryList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getCountryList(HttpServletRequest request) {
		LOGGER.info("Get Country List");
		ResponseMessage responseMessage = null;
		List<DtoCountry> countryList = serviceHome.getCountryList();
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_COUNTRY_LIST, false), countryList);
		return responseMessage;
	}

	/**
	 * @description : Get State List By Country Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getStateListByCountryId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getStateListByCountryId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) {
		LOGGER.info("Get State List By Country Id");
		ResponseMessage responseMessage = null;
		List<DtoCountry> stateList = serviceHome.getStateListByCountryId(dtoCountry.getCountryId());
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_STATE_LIST, false), stateList);

		return responseMessage;
	}

	/**
	 * @description : Get City List By State Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getCityListByStateId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getCityListByStateId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) 
	{
		LOGGER.info("Get City List By State Id");
		ResponseMessage responseMessage = null;
		List<DtoCountry> cityList = serviceHome.getCityListByStateId(dtoCountry.getStateId());
		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_CITY_LIST, false), cityList);
		return responseMessage;
	}

	/**
	 * @description : Get Country Code By Country Id
	 * @param request
	 * @param dtoCountry
	 * @return
	 */
	@RequestMapping(value = "/getCountryCodeByCountryId", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getCountryCodeByCountryId(HttpServletRequest request, @RequestBody DtoCountry dtoCountry) {
		LOGGER.info("Get Country Code By Country  Id");
		ResponseMessage responseMessage = null;
		DtoCountry countryCode = serviceHome.getCountryCodeByCountryId(dtoCountry.getCountryId());

		responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
				serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.GET_COUNTRY_CODE, false), countryCode);

		return responseMessage;
	}

	@RequestMapping(value = "/updateActiveSession", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage updateActiveSessiong(HttpServletRequest request,@RequestBody DtoUser dtoUser)
	{
		ResponseMessage responseMessage = null;
		Boolean response = this.serviceHome.updateActiveSession(dtoUser);
		if (response) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_UPDATED_SUCCESS, false));
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

}

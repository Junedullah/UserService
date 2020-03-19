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
package com.ss.service;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.ss.repository.RepositoryUser;
import com.ss.constant.Constant;
import com.ss.constant.MessageLabel;
//import com.ss.constant.SMTRoles;
import com.ss.model.*;
import com.ss.model.dto.*;
import com.ss.repository.*;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;


/**
 * Description: Service Home
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: MARCH 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Service("serviceHome")
@Transactional
@EnableScheduling
public class ServiceHome {

	private static final Logger LOGGER = Logger.getLogger(ServiceHome.class);

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	RepositoryRole repositoryRole;

	@Autowired
	RepositoryUserGroup repositoryUserGroup;

	@Autowired
	RepositoryUserDetailPagination repositoryUserDetailPagination;

	@Autowired
	RepositoryRoleGroup repositoryRoleGroup;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;

	@Autowired
	ServiceEmailHandler serviceEmailHandler;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanies;

	@Autowired
	RepositoryCompany repositoryCompany;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired
	RepositoryAuthorizationSetting repositoryAuthorizationSetting;

	@Autowired
	RepositoryCountryMaster repositoryCountryMaster;

	@Autowired
	RepositoryStateMaster repositoryStateMaster;

	@Autowired
	RepositoryCityMaster repositoryCityMaster;
	
	@Autowired
	RepositoryLanguage repositoryLanguage;	
	
	@Autowired
	RepositoryModule repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;
	
	@Autowired
	RepositoryFields repositoryFields;
	
	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;
	
	@Autowired
	RepositoryFieldValidation repositoryFieldValidation;
	
	@Autowired
	ServiceResponse serviceResponse;
	
/*	@Autowired
	RepositoryCommonConstant repositoryCommonConstant;*/
	
	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;
	
/*	@Autowired
	RepositorySstMessage repositorySstMessage;*/
	
	@Autowired
	RepositoryWeekDay repositoryWeekDay;
	
	@Autowired
	RepositoryUserSession repositoryUserSession;
	
	@Autowired
	RepositoryUserDraft repositoryUserDraft;
	
	@Value("${script.accessfinancialpath}")
	private String accessfinancialpath;
	
	private final Integer ENG_LANG_ID=1;
	
	 
	/**
	 * Description: get country list for drop down
	 * @return
	 */
	public List<DtoCountry> getCountryList() {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> countryList = new ArrayList<>();
		List<CountryMaster> list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));
		
		if(list.isEmpty()){
			list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, 1);
		}
		
		if (list != null && !list.isEmpty()) {
			for (CountryMaster countryMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCountryId(countryMaster.getCountryId());
				dtoCountry.setCountryName(countryMaster.getCountryName());
				dtoCountry.setShortName(countryMaster.getShortName());
				dtoCountry.setCountryCode(countryMaster.getCountryCode());
				countryList.add(dtoCountry);
			}
		}
		return countryList;
	}

	/**
	 * Description: get state list by country id for drop down
	 * @param countryId
	 * @return
	 */
	public List<DtoCountry> getStateListByCountryId(int countryId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> stateList = new ArrayList<>();
		List<StateMaster> list = repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, Integer.parseInt(langId));
		if(list.isEmpty()){
			list=repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, 1);
		}
		if (list != null && !list.isEmpty()) {
			for (StateMaster stateMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setStateId(stateMaster.getStateId());
				dtoCountry.setStateName(stateMaster.getStateName());
				stateList.add(dtoCountry);
			}
		}
		return stateList;
	}

	/**
	 * Description: get city list by state id for drop down
	 * @param stateId
	 * @return
	 */
	public List<DtoCountry> getCityListByStateId(int stateId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> cityList = new ArrayList<>();
		List<CityMaster> list = repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,Integer.parseInt(langId));
		
		if(list.isEmpty()){
			list=repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,1);
		}
		
		if (list != null && !list.isEmpty()) {
			for (CityMaster cityMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCityId(cityMaster.getCityId());
				dtoCountry.setCityName(cityMaster.getCityName());
				cityList.add(dtoCountry);
			}
		}
		return cityList;
	}

	/**
	 * Description: get country code by country id
	 * @param countryId
	 * @return
	 */
	public DtoCountry getCountryCodeByCountryId(int countryId) {
		DtoCountry dtoCountry = new DtoCountry();
		CountryMaster countryMaster = repositoryCountryMaster.findByCountryIdAndIsDeletedAndIsActive(countryId, false,
				true);
		if (countryMaster != null) {

			dtoCountry.setCountryId(countryMaster.getCountryId());
			dtoCountry.setCountryCode("+" + countryMaster.getCountryCode());
		}
		return dtoCountry;
	}

	}

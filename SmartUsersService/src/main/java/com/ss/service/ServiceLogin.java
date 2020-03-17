/**SmartSoftware User - Service */
/**
 * Description: ServiceLogin
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.constant.SmartRoles;
import com.ss.model.AccessRoleModuleRelation;
import com.ss.model.AccessRoleScreenRelation;
import com.ss.model.AuthorizationSetting;
import com.ss.model.Company;
import com.ss.model.Field;
import com.ss.model.FieldAccess;
import com.ss.model.FieldValidation;
import com.ss.model.Grid;
import com.ss.model.GridData;
import com.ss.model.Module;
import com.ss.model.Screen;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserSession;
import com.ss.model.ValidationMessage;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoFieldValidationMessage;
import com.ss.model.dto.DtoGrid;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.repository.RepositoryAccessRoleModuleRelation;
import com.ss.repository.RepositoryAccessRoleScreenRelation;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryFieldAccess;
import com.ss.repository.RepositoryFieldValidation;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryGrid;
import com.ss.repository.RepositoryGridData;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserSession;
import com.ss.repository.RepositoryValidationMessages;
import com.ss.util.UtilDateAndTime;
import com.ss.util.UtilTimeIntervals;




@Service("ServiceLogin")
public class ServiceLogin {

	static Logger log = Logger.getLogger(ServiceLogin.class.getName());
	
	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryFields repositoryFields;

	@Autowired
	RepositoryFieldValidation repositoryFieldValidation;
	
	@Autowired
	RepositoryAccessRoleModuleRelation repositoryAccessRoleModuleRelation;
	
	@Autowired
	RepositoryUserDetail repositoryUserDetail;
	
	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;
	
	@Autowired
	RepositoryAccessRoleScreenRelation repositoryAccessRoleScreenRelation;
	
	@Autowired
	RepositoryUser repositoryUser;
	
	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;
	
	@Autowired
	RepositoryUserSession repositoryUserSession;
	
	@Autowired
	RepositoryCompany repositoryCompany;
	
	@Autowired
	RepositoryGrid repositoryGrid;

	@Autowired
	RepositoryGridData repositoryGridData;
	
	@Autowired
	RepositoryFieldAccess repositoryFieldAccess;

	private static final String USER_ID = "userid";
	private static final String COMPANY_TENANT_ID = "tenantid";
	
	/**
	 * Description: get screen detail for all module and screen (Fields name, validation message, help message for all fields and screen as per language)
	 * @param dtoScreenDetail
	 * @return
	 */
	public DtoModule getScreenDetail(DtoScreenDetail dtoScreenDetail) {
		DtoFieldDetail dtoFieldDetail;
		DtoModule dtoModule = new DtoModule();
		String langId = httpServletRequest.getHeader("langId");
		
		if (langId != null) 
		{
			List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
			if (dtoScreenDetail.getModuleCode() != null && !dtoScreenDetail.getModuleCode().isEmpty()) 
			{
				Module module = repositoryModule.findByModuleCodeAndIsDeletedAndLanguageLanguageId(dtoScreenDetail.getModuleCode(), false,Integer.parseInt(langId));
				if(module.getIsActive())
				{
					if (dtoScreenDetail.getScreenCode() != null && !dtoScreenDetail.getScreenCode().isEmpty()) 
					{
						if (module != null) 
						{
							dtoModule = new DtoModule(module);
							Screen screen = repositoryScreen.findByScreenCodeAndIsDeletedAndModuleModuleId(
									dtoScreenDetail.getScreenCode(), false, module.getModuleId());
							if (screen != null) 
							{
								if(screen.getSideMenu()!=null){
									dtoModule.setModuleName(screen.getSideMenu());
								}
								else
								{
									dtoModule.setModuleName(screen.getScreenName());
								}
								
								dtoScreenDetail = new DtoScreenDetail(screen, langId);
								List<Field> fieldList = repositoryFields.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(
										screen.getScreenId(), false, false);
								if (fieldList != null && !fieldList.isEmpty()) {
									for (Field field : fieldList) {
										dtoFieldDetail = new DtoFieldDetail(field, langId);
										dtoFieldDetail.setReadAccess(true);
										dtoFieldDetail.setWriteAccess(true);
										dtoFieldDetail.setDeleteAccess(true);
										dtoFieldDetail.setIsMandatory(field.getIsMandatory());
										dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
										DtoFieldValidationMessage dtoFieldValidationMessage;
										List<DtoFieldValidationMessage> listDtoFieldValidationMessage = new ArrayList<>();

										List<FieldValidation> fieldValidationList = repositoryFieldValidation
												.findByFieldFieldCodeAndIsDeleted(field.getFieldCode(), false);
										if (fieldValidationList != null && !fieldValidationList.isEmpty()) 
										{
											for (FieldValidation fieldValidation : fieldValidationList) {
												ValidationMessage validationMessage = fieldValidation.getValidationMessage();
												validationMessage=repositoryValidationMessages.findByMessageShortAndLanguageLanguageId(validationMessage.getMessageShort(),Integer.parseInt(langId));
												if(validationMessage!=null){
													dtoFieldValidationMessage = new DtoFieldValidationMessage(validationMessage,
															langId);
													dtoFieldValidationMessage.setFieldId(fieldValidation.getField().getFieldId());
													listDtoFieldValidationMessage.add(dtoFieldValidationMessage);
												}
												
											}
										}
										dtoFieldDetail.setListDtoFieldValidationMessage(listDtoFieldValidationMessage);
										dtoFieldDetails.add(dtoFieldDetail);
									}
								}
							}
						}
						
						dtoScreenDetail.setDeleteAccess(true);
						dtoScreenDetail.setReadAccess(true);
						dtoScreenDetail.setWriteAccess(true);
						dtoScreenDetail.setFieldList(dtoFieldDetails);
						
						dtoModule.setDtoScreenDetail(dtoScreenDetail);
						dtoModule.setReadAccess(true);
						dtoModule.setWriteAccess(true);
						dtoModule.setDeleteAccess(true);
					} 
					else 
					{
						dtoModule.setMessageType("PROVIDE_SCREEN_ID");
					}
				}
				else
				{
					dtoModule.setMessageType("MODULE_INACTIVE");
				}
			}
			else 
			{
				dtoModule.setMessageType("PROVIDE_MODULE_ID");
			}
		} 
		else 
		{
			dtoModule.setMessageType("PROVIDE_LANGUAGE_ID");
		}
		return dtoModule;
	}
	
	/**
	 * @param dtoScreenDetail
	 * @param loggedInUserId
	 * @param userGroupId
	 * @return
	 */
	public DtoModule getScreenDetailByUserAccessRole(DtoScreenDetail dtoScreenDetail,int loggedInUserId,int userGroupId, int companyId) 
	{
		boolean readAccess=false;
		boolean writeAccess=false;
		boolean deleteAccess=false;
		DtoFieldDetail dtoFieldDetail;
		DtoModule dtoModule = new DtoModule();
		String langId = httpServletRequest.getHeader("langId");
		if (langId != null) 
			{
				    List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
				    if(dtoScreenDetail.getModuleCode() != null && !dtoScreenDetail.getModuleCode().isEmpty()) {
					if (dtoScreenDetail.getScreenCode() != null && !dtoScreenDetail.getScreenCode().isEmpty()) 
					{
						AccessRoleModuleRelation accessRoleModuleRelation = repositoryAccessRoleModuleRelation.getModulesByAccessRolesOfUserAndModuleCode(userGroupId, dtoScreenDetail.getModuleCode());
						if (accessRoleModuleRelation!=null && accessRoleModuleRelation.getModule() != null) 
						{
							Module module = accessRoleModuleRelation.getModule();
							Module moduleByLanguage = repositoryModule.findByModuleCodeAndIsDeletedAndLanguageLanguageId(module.getModuleCode(), false,Integer.parseInt(langId));
							if( moduleByLanguage!=null && moduleByLanguage.getIsActive())
							{
								dtoModule = new DtoModule(moduleByLanguage);
								Integer screenId = repositoryAccessRoleScreenRelation.getDistnictScreenIdByAccessRoleModuleRelationIdAndScreenCodeAndModuleCode(accessRoleModuleRelation.getAccessRoleModuleRelationId(), dtoScreenDetail.getScreenCode(),moduleByLanguage.getModuleCode());
								if (screenId != null && screenId>0) 
								{
									Screen screen = repositoryScreen.findOne(screenId);
								    Screen screenByLanguage = repositoryScreen.findByScreenCodeAndIsDeletedAndLanguageLanguageId(screen.getScreenCode(),false,Integer.parseInt(langId));
								    if(screenByLanguage!=null)
								    {
									    	if(screenByLanguage.getSideMenu()!=null){
												dtoModule.setModuleName(screenByLanguage.getSideMenu());
											}
											else
											{
												dtoModule.setModuleName(screenByLanguage.getScreenName());
											}
									    	
										dtoScreenDetail = new DtoScreenDetail(screenByLanguage, langId);
										List<AccessRoleScreenRelation> fieldIdList = repositoryAccessRoleScreenRelation.getListOfFieldByAccessRoleModuleRelationIdAndScreenCode(accessRoleModuleRelation.getAccessRoleModuleRelationId(), screenByLanguage.getScreenCode());
										if (fieldIdList != null && !fieldIdList.isEmpty()) {
											for (AccessRoleScreenRelation accessRoleScreenRelation : fieldIdList) 
											{
												Field field = accessRoleScreenRelation.getField();
												Field fieldByLanguage = repositoryFields.findByFieldCodeAndLanguageLanguageId(field.getFieldCode(),Integer.parseInt(langId));
												if(accessRoleScreenRelation.getReadAccess()){
													readAccess=true;
												}
												
		                                        if(accessRoleScreenRelation.getDeleteAccess()){
		                                        	
		                                        	deleteAccess=true;
												}
		                                        
		                                        if(accessRoleScreenRelation.getWriteAccess()){
		                                        	writeAccess=true;
												}
		                                        
												if(fieldByLanguage!=null){
													dtoFieldDetail = new DtoFieldDetail(fieldByLanguage, langId);
												}
												else{
													dtoFieldDetail = new DtoFieldDetail(field, langId);
												}
												
												dtoFieldDetail.setReadAccess(accessRoleScreenRelation.getReadAccess());
												dtoFieldDetail.setDeleteAccess(accessRoleScreenRelation.getDeleteAccess());
												dtoFieldDetail.setWriteAccess(accessRoleScreenRelation.getWriteAccess());
												
//												FieldAccess fieldAccess = repositoryFieldAccess.findByModuleModuleIdAndCompanyCompanyIdAndFieldFieldIdAndLanguageLanguageIdAndIsDeleted(dtoModule.getModuleId(), companyId, field.getFieldId(), CommonUtils.parseInteger(langId), false);
												Field englishField = repositoryFields.findByFieldCodeAndLanguageLanguageId(field.getFieldCode(), 1);
												if (englishField != null ) {
												FieldAccess fieldAccess = repositoryFieldAccess.findByCompanyCompanyIdAndFieldFieldIdAndIsDeleted(companyId, englishField.getFieldId(), false);
												if (fieldAccess != null) {
													dtoFieldDetail.setIsMandatory(fieldAccess.getMandatory());
												} else {
													dtoFieldDetail.setIsMandatory(false);
												}
												}
												
												
												DtoFieldValidationMessage dtoFieldValidationMessage;
												List<DtoFieldValidationMessage> listDtoFieldValidationMessage = new ArrayList<>();

												List<FieldValidation> fieldValidationList = repositoryFieldValidation
														.findByFieldFieldCodeAndIsDeleted(field.getFieldCode(), false);
												if (fieldValidationList != null && !fieldValidationList.isEmpty()) 
												{
													for (FieldValidation fieldValidation : fieldValidationList) {
														ValidationMessage validationMessage = fieldValidation.getValidationMessage();
														validationMessage=repositoryValidationMessages.findByMessageShortAndLanguageLanguageId(validationMessage.getMessageShort(),Integer.parseInt(langId));
														if(validationMessage!=null){
															dtoFieldValidationMessage = new DtoFieldValidationMessage(validationMessage,
																	langId);
															dtoFieldValidationMessage.setFieldId(fieldValidation.getField().getFieldId());
															listDtoFieldValidationMessage.add(dtoFieldValidationMessage);
														}
														
													}
												}
												dtoFieldDetail.setListDtoFieldValidationMessage(listDtoFieldValidationMessage);
												dtoFieldDetails.add(dtoFieldDetail);
												
											}
										}
									}
								}
							}
							else
							{
								dtoModule.setMessageType("MODULE_INACTIVE");	
							}
						}
						
						dtoScreenDetail.setReadAccess(readAccess);
						dtoScreenDetail.setWriteAccess(writeAccess);
						dtoScreenDetail.setDeleteAccess(deleteAccess);
						dtoScreenDetail.setFieldList(dtoFieldDetails);
						
						dtoModule.setDeleteAccess(deleteAccess);
						dtoModule.setReadAccess(readAccess);
						dtoModule.setWriteAccess(writeAccess);
						dtoModule.setDtoScreenDetail(dtoScreenDetail);
					} 
					else 
					{
						dtoModule.setMessageType("PROVIDE_SCREEN_ID");
					}
				} 
				else 
				{
					dtoModule.setMessageType("PROVIDE_MODULE_ID");
				}
			} 
			else 
			{
				dtoModule.setMessageType("PROVIDE_LANGUAGE_ID");
			}
		return dtoModule;
	}
	
	/**
	 * @param dtoScreenDetail
	 * @return
	 */
	public DtoModule getScreenDetailByUserId(DtoScreenDetail dtoScreenDetail) 
	{
		 DtoModule dtoModule = null;
		 if(httpServletRequest.getHeader(USER_ID)!=null)
		 {
				int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
				String companyTenantId =httpServletRequest.getHeader(COMPANY_TENANT_ID);
				User user = repositoryUser.findOne(loggedInUserId);
				if(user!=null)
				{
					if(user.getRole().getRoleId()==SmartRoles.SUPERADMIN.getIndex())
					{
						dtoModule=getScreenDetail(dtoScreenDetail);
					}
					else
					{
						Company company= repositoryCompany.findByTenantIdAndIsDeleted(companyTenantId, false);
						if(company!=null)
						{
							Integer userGroupId = repositoryUserCompanyRelation.getUserGroupByUserIdAndCompanyId(loggedInUserId,company.getCompanyId());
							if(userGroupId!=null && userGroupId>0)
							{
								dtoModule= getScreenDetailByUserAccessRole(dtoScreenDetail,loggedInUserId,userGroupId, company.getCompanyId());
								if(dtoModule.getDtoScreenDetail()!=null && !dtoModule.getDtoScreenDetail().getDeleteAccess() && !dtoModule.getDtoScreenDetail().getReadAccess() && !dtoModule.getDtoScreenDetail().getWriteAccess())
								{
										dtoModule.setMessageType("DO_NOT_ACCESS_OF_SCREEN");
								}
							}
							else
							{
								dtoModule=new DtoModule();
								dtoModule.setMessageType("USER_GROUP_NOT_FOUND");
							}
						}
						else
						{
							dtoModule=new DtoModule();
							dtoModule.setMessageType("USER_GROUP_NOT_FOUND");
						}
					}
				}
				else
				{
					dtoModule=new DtoModule();
					dtoModule.setMessageType("USER_NOT_FOUND");
				}
		}
		else
		{
			dtoModule=new DtoModule();
			dtoModule.setMessageType("USER_NOT_FOUND");
		}
		return dtoModule;
	}

	public DtoUser checkValidCompanyAceess(DtoUser dtoUser) {
		
		Boolean flag=false;
		
		//function to check the valid access for company
		
		UserCompanyRelation userCompanyRelation=repositoryUserCompanyRelation.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(dtoUser.getCompanyId(), dtoUser.getUserId(), false);
		
		flag= checkValidCompanyAccess(dtoUser.getCompanyId(), dtoUser.getUserId());
		
		if(flag){
			
			//here update the session table with company tanent id 
			
			UserSession session = repositoryUserSession.findBySessionAndIsDeleted(dtoUser.getSession(), false);
			
			if(session!=null){
				session.setCompnayTenantId(userCompanyRelation.getCompany().getTenantId());
				repositoryUserSession.saveAndFlush(session);
				dtoUser.setCompanyTenantId(userCompanyRelation.getCompany().getTenantId());
			}
		}
		return dtoUser;
	}
	
	public boolean checkValidCompanyAccess(int companyId , int userId)
    {
       UserCompanyRelation userCompanyRelation = repositoryUserCompanyRelation.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(companyId, userId, false);
       if(userCompanyRelation!=null)
       {
             AuthorizationSetting authorizationSetting  = userCompanyRelation.getAuthorizationSetting();
             if (authorizationSetting!=null && 
                          authorizationSetting.getWeekDay().contains(String.valueOf(UtilDateAndTime.getWeekDayNumber(new Date()))) && 
                          ( 
                          UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getEndDate()).equalsIgnoreCase(UtilDateAndTime.dateToStringddmmyyyy(new Date())) 
                          || authorizationSetting.getEndDate().after(new Date())) && 
                          (UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getStartDate()).equalsIgnoreCase(UtilDateAndTime.dateToStringddmmyyyy(new Date())) 
                          || authorizationSetting.getStartDate().before(new Date())) 
                          && UtilTimeIntervals.isNowInInterval
                          (
                             UtilDateAndTime.convertDateToStringTime24Formats(authorizationSetting.getStartTime()) ,
                             UtilDateAndTime.convertDateToStringTime24Formats(authorizationSetting.getEndTime())
                          )
                      )
                  {
                    return true;
                  }
             
       }
       return false;
    }

	public boolean checkCompanyAccessForOtherModule() 
	{
		  int userId=Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		  String companyTanentId=httpServletRequest.getHeader(/*"companyTenantId"*/ COMPANY_TENANT_ID);
		  Company company=repositoryCompany.findByTenantIdAndIsDeleted(companyTanentId,false);
	      UserCompanyRelation userCompanyRelation = repositoryUserCompanyRelation.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(company.getCompanyId(), userId, false);
	      if(userCompanyRelation!=null)
	      {
             AuthorizationSetting authorizationSetting  = userCompanyRelation.getAuthorizationSetting();
             if (authorizationSetting!=null && 
                      authorizationSetting.getWeekDay().contains(String.valueOf(UtilDateAndTime.getWeekDayNumber(new Date()))) && 
                      ( 
                      UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getEndDate()).equalsIgnoreCase(UtilDateAndTime.dateToStringddmmyyyy(new Date())) 
                      || authorizationSetting.getEndDate().after(new Date())) && 
                      (UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getStartDate()).equalsIgnoreCase(UtilDateAndTime.dateToStringddmmyyyy(new Date())) 
                      || authorizationSetting.getStartDate().before(new Date())) 
                      && UtilTimeIntervals.isNowInInterval
                      (
                         UtilDateAndTime.convertDateToStringTime24Formats(authorizationSetting.getStartTime()) ,
                         UtilDateAndTime.convertDateToStringTime24Formats(authorizationSetting.getEndTime())
                      )
                  )
              {
                return true;
              }
	      }
	      return false;
	}

	public DtoCompany getCompanyDatabaseCredential() {

		int userId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		String companyTanentId = httpServletRequest.getHeader(/*"companyTenantId"*/ COMPANY_TENANT_ID);
		Company company = repositoryCompany.findByTenantIdAndIsDeleted(companyTanentId, false);
		if(company!=null){
			UserCompanyRelation userCompanyRelation = repositoryUserCompanyRelation
					.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(company.getCompanyId(), userId, false);
			if (userCompanyRelation != null) {
				AuthorizationSetting authorizationSetting = userCompanyRelation.getAuthorizationSetting();
				if (authorizationSetting != null) {
					DtoCompany dtoCompany = new DtoCompany();
					dtoCompany.setDatabaseIP(company.getDatabaseIP());
					dtoCompany.setPort(company.getDatabasePort());
					dtoCompany.setUsername(company.getDatabaseUsername());
					dtoCompany.setPassword(company.getDatabasePassword());
					return dtoCompany;
				}
			}
		} 
		
		return null;
	}
	
	public DtoModule getScreenGridDetail(DtoScreenDetail dtoScreenDetail) {
		DtoFieldDetail dtoFieldDetail;
		DtoModule dtoModule = new DtoModule();
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoScreenDetail.getPageNumber());
		dtoSearch.setPageSize(dtoScreenDetail.getPageSize());
		dtoSearch.setTotalCount(repositoryGrid.getCountOfTotalGrid());
		String langId = httpServletRequest.getHeader("langId");
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid").toString());
		if (langId != null) {
			// List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
			Set<DtoFieldDetail> dtoFieldDetails = new HashSet<>();
			List<DtoFieldDetail> dtoFieldDetails2 = new ArrayList<>();
			List<DtoGrid> dtoGridList = new ArrayList<>();
			if (dtoScreenDetail.getModuleCode() != null && !dtoScreenDetail.getModuleCode().isEmpty()) {
				Module module = repositoryModule.findByModuleCodeAndIsDeletedAndLanguageLanguageId(
						dtoScreenDetail.getModuleCode(), false, Integer.parseInt(langId));
				if (module.getIsActive()) {
					if (dtoScreenDetail.getScreenCode() != null && !dtoScreenDetail.getScreenCode().isEmpty()) {
						if (module != null) {
							dtoModule = new DtoModule(module);
							Screen screen = repositoryScreen.findByScreenCodeAndIsDeletedAndModuleModuleId(
									dtoScreenDetail.getScreenCode(), false, module.getModuleId());
							if (screen != null) {
								if (screen.getSideMenu() != null) {
									dtoModule.setModuleName(screen.getSideMenu());
								} else {
									dtoModule.setModuleName(screen.getScreenName());
								}
								dtoScreenDetail = new DtoScreenDetail(screen, langId);

								List<Field> fieldList = repositoryFields
										.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(screen.getScreenId(), false,
												false);
								if (fieldList != null && !fieldList.isEmpty()) {
									for (Field field : fieldList) {
										DtoFieldDetail dtoFieldDetail2 = new DtoFieldDetail(field, langId);
										dtoFieldDetail2.setReadAccess(true);
										dtoFieldDetail2.setWriteAccess(true);
										dtoFieldDetail2.setDeleteAccess(true);
										dtoFieldDetail2.setIsMandatory(field.getIsMandatory());
										dtoFieldDetail2.setLanguageId(field.getLanguage().getLanguageId());
										DtoFieldValidationMessage dtoFieldValidationMessage;
										List<DtoFieldValidationMessage> listDtoFieldValidationMessage = new ArrayList<>();

										List<FieldValidation> fieldValidationList = repositoryFieldValidation
												.findByFieldFieldCodeAndIsDeleted(field.getFieldCode(), false);
										if (fieldValidationList != null && !fieldValidationList.isEmpty()) {
											for (FieldValidation fieldValidation : fieldValidationList) {
												ValidationMessage validationMessage = fieldValidation
														.getValidationMessage();
												validationMessage = repositoryValidationMessages
														.findByMessageShortAndLanguageLanguageId(
																validationMessage.getMessageShort(),
																Integer.parseInt(langId));
												if (validationMessage != null) {
													dtoFieldValidationMessage = new DtoFieldValidationMessage(
															validationMessage, langId);
													dtoFieldValidationMessage
															.setFieldId(fieldValidation.getField().getFieldId());
													listDtoFieldValidationMessage.add(dtoFieldValidationMessage);
												}
											}
										}
										dtoFieldDetail2.setListDtoFieldValidationMessage(listDtoFieldValidationMessage);
										dtoFieldDetails2.add(dtoFieldDetail2);
									}
								}

								List<Grid> gridList = repositoryGrid.findByScreenIdAndModuleId(screen.getScreenId(),
										module.getModuleId());
								List<GridData> gridDataList = null;

								if (gridList != null) {
									for (Grid grid : gridList) {
										DtoGrid dtoGrid = new DtoGrid();
										gridDataList = repositoryGridData.findByGridIdAndIsReset(grid.getGridId(),
												loggedInUserId);
										if (gridDataList != null) {
											for (GridData gridData : gridDataList) {

												Field field = gridData.getFieldId();

												dtoFieldDetail = new DtoFieldDetail(field, langId);
												dtoFieldDetail.setReadAccess(true);
												dtoFieldDetail.setWriteAccess(true);
												dtoFieldDetail.setDeleteAccess(true);
												dtoFieldDetail.setIsMandatory(field.getIsMandatory());
												dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
												dtoFieldDetail.setFieldId(field.getFieldId());
												dtoFieldDetail.setFieldName(field.getFieldName());
												dtoFieldDetail.setFieldWidth(field.getFieldWidth());
												dtoFieldDetail.setGridFieldName(field.getGridFieldName());
												dtoFieldDetail.setColOrder(gridData.getColOrder());
												dtoFieldDetail.setIsVisible(gridData.getIsVisible());
												dtoGrid.setGridId(grid.getGridId());
												DtoFieldValidationMessage dtoFieldValidationMessage;
												List<DtoFieldValidationMessage> listDtoFieldValidationMessage = new ArrayList<>();

												List<FieldValidation> fieldValidationList = repositoryFieldValidation
														.findByFieldFieldCodeAndIsDeleted(field.getFieldCode(), false);
												if (fieldValidationList != null && !fieldValidationList.isEmpty()) {
													for (FieldValidation fieldValidation : fieldValidationList) {
														ValidationMessage validationMessage = fieldValidation
																.getValidationMessage();
														validationMessage = repositoryValidationMessages
																.findByMessageShortAndLanguageLanguageId(
																		validationMessage.getMessageShort(),
																		Integer.parseInt(langId));
														if (validationMessage != null) {
															dtoFieldValidationMessage = new DtoFieldValidationMessage(
																	validationMessage, langId);
															dtoFieldValidationMessage.setFieldId(
																	fieldValidation.getField().getFieldId());
															listDtoFieldValidationMessage
																	.add(dtoFieldValidationMessage);
														}
													}
												}
												dtoFieldDetail.setListDtoFieldValidationMessage(
														listDtoFieldValidationMessage);
												dtoFieldDetails.add(dtoFieldDetail);
											}
										}

										List<Field> fieldGridList = repositoryFields
												.findByGridIdAndNotInGridData(grid.getId(), loggedInUserId);
										if (fieldGridList != null && !fieldGridList.isEmpty()) {
											for (Field field : fieldGridList) {

												dtoFieldDetail = new DtoFieldDetail(field, langId);
												dtoFieldDetail.setReadAccess(true);
												dtoFieldDetail.setWriteAccess(true);
												dtoFieldDetail.setDeleteAccess(true);
												dtoFieldDetail.setIsMandatory(field.getIsMandatory());
												dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
												dtoFieldDetail.setFieldId(field.getFieldId());
												dtoFieldDetail.setFieldName(field.getFieldName());
												dtoFieldDetail.setColOrder(field.getColOrder());
												dtoFieldDetail.setIsVisible(field.getIsVisible());
												dtoFieldDetail.setFieldWidth(field.getFieldWidth());
												dtoFieldDetail.setGridFieldName(field.getGridFieldName());
												dtoGrid.setGridId(grid.getGridId());
												DtoFieldValidationMessage dtoFieldValidationMessage;
												List<DtoFieldValidationMessage> listDtoFieldValidationMessage = new ArrayList<>();

												List<FieldValidation> fieldValidationList = repositoryFieldValidation
														.findByFieldFieldCodeAndIsDeleted(field.getFieldCode(), false);
												if (fieldValidationList != null && !fieldValidationList.isEmpty()) {
													for (FieldValidation fieldValidation : fieldValidationList) {
														ValidationMessage validationMessage = fieldValidation
																.getValidationMessage();
														validationMessage = repositoryValidationMessages
																.findByMessageShortAndLanguageLanguageId(
																		validationMessage.getMessageShort(),
																		Integer.parseInt(langId));
														if (validationMessage != null) {
															dtoFieldValidationMessage = new DtoFieldValidationMessage(
																	validationMessage, langId);
															dtoFieldValidationMessage.setFieldId(
																	fieldValidation.getField().getFieldId());
															listDtoFieldValidationMessage
																	.add(dtoFieldValidationMessage);
														}
													}
												}
												dtoFieldDetail.setListDtoFieldValidationMessage(
														listDtoFieldValidationMessage);
												dtoFieldDetails.add(dtoFieldDetail);
											}
										}
										dtoGrid.setFieldDetailList(dtoFieldDetails);
										dtoGridList.add(dtoGrid);
									}
								}
							}
						}
						// dtoGrid.setFieldDetailList(dtoFieldDetails);
						dtoScreenDetail.setDeleteAccess(true);
						dtoScreenDetail.setReadAccess(true);
						dtoScreenDetail.setWriteAccess(true);
						dtoScreenDetail.setFieldList(dtoFieldDetails2);
						dtoScreenDetail.setGirdList(dtoGridList);
						dtoModule.setDtoScreenDetail(dtoScreenDetail);
						dtoModule.setReadAccess(true);
						dtoModule.setWriteAccess(true);
						dtoModule.setDeleteAccess(true);
					} else {
						dtoModule.setMessageType("PROVIDE_SCREEN_ID");
					}
				} else {
					dtoModule.setMessageType("MODULE_INACTIVE");
				}
			} else {
				dtoModule.setMessageType("PROVIDE_MODULE_ID");
			}
		} else {
			dtoModule.setMessageType("PROVIDE_LANGUAGE_ID");
		}
		return dtoModule;
	}
}

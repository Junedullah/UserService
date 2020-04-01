

package com.ss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.constant.SmartCodeType;
import com.ss.model.AccessRole;
import com.ss.model.AccessRoleModuleRelation;
import com.ss.model.AccessRoleScreenRelation;
import com.ss.model.Field;
import com.ss.model.Module;
import com.ss.model.ReportCategory;
import com.ss.model.Reports;
import com.ss.model.Screen;
import com.ss.model.TransactionType;
import com.ss.model.Transactions;
import com.ss.model.dto.DtoAccessRole;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoReport;
import com.ss.model.dto.DtoReportCategory;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoTransaction;
import com.ss.model.dto.DtoTransactionType;
import com.ss.repository.RepositoryAccessRole;
import com.ss.repository.RepositoryAccessRoleModuleRelation;
import com.ss.repository.RepositoryAccessRoleScreenRelation;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryRoleGroupAccessRole;
import com.ss.repository.RepositoryScreen;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Mar 30, 2020
 * Modified on: Mar 30, 2020 11:19:38 AM
 * @author Juned Baig
 * Version: 
 */
@Service("serviceAccessRole")
public class ServiceAccessRole {

	private static final Logger LOGGER = Logger.getLogger(ServiceAccessRole.class);

	@Autowired
	RepositoryAccessRole repositoryAccessRole;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryFields repositoryFields;

	@Autowired
	RepositoryAccessRoleScreenRelation repositoryAccessRoleScreenRelation;

	/*@Autowired
	RepositoryAccessRoleTransactionRelation repositoryAccessRoleTransactionRelation;


	@Autowired
	RepositoryTransactionTypes repositoryTransactionTypes;

	@Autowired
	RepositoryTransactions repositoryTransactions;

	@Autowired
	RepositoryReports repositoryReports;

	@Autowired
	RepositoryReportCategory repositoryReportCategory;

	@Autowired
	RepositoryAccessRoleReportRelation repositoryAccessRoleReportRelation;*/

	@Autowired
	RepositoryAccessRoleModuleRelation repositoryAccessRoleModuleRelation;

	@Autowired
	RepositoryRoleGroupAccessRole repositoryRoleGroupAccessRole;

	private static final String USER_ID ="userid";

	/**
	 * Description: Save New Access Role 
	 * @param dtoRole
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveAccessRole(DtoAccessRole dtoAccessRole) {
		int id = 0;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		if (dtoAccessRole != null) {
			AccessRole accessRole = this.repositoryAccessRole.findByAccessRoleIdAndIsDeleted(dtoAccessRole.getId(),
					false);
			if (accessRole != null) {
				accessRole.setUpdatedBy(loggedInUserId);
				accessRole.setRoleName(dtoAccessRole.getRoleName());
				accessRole.setRoleDescription(dtoAccessRole.getRoleDescription());
				accessRole = this.repositoryAccessRole.saveAndFlush(accessRole);
				if (accessRole != null) {
					id = accessRole.getAccessRoleId();
				}
			} else {
				accessRole = new AccessRole();
				accessRole.setCreatedBy(loggedInUserId);
				accessRole.setRoleCode(this.codeGenerator.getGeneratedCode(SmartCodeType.ROLE.name()));
				accessRole.setIsDeleted(false);
				accessRole.setRoleName(dtoAccessRole.getRoleName());
				accessRole.setRoleDescription(dtoAccessRole.getRoleDescription());
				accessRole = this.repositoryAccessRole.saveAndFlush(accessRole);
				if (accessRole != null) {
					id = accessRole.getAccessRoleId();
				}
			}
		}
		return id;
	}
	
	/**
	 * Description: Get Access Role List
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch getAccessRoleList(DtoSearch dtoSearch) {
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryAccessRole.getCountOfTotalAccessRoles());
		List<AccessRole> accessRoleList = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(),  Direction.DESC, "createdDate");
			accessRoleList = this.repositoryAccessRole.findByIsDeleted(false, pageable);
		} else {
			accessRoleList = this.repositoryAccessRole.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		List<DtoAccessRole> dtoAccessRoleList = new ArrayList<>();
		if (accessRoleList != null) {
			DtoAccessRole dtoAccessRole = null;
			for (AccessRole accessRole : accessRoleList) {
				dtoAccessRole = new DtoAccessRole(accessRole);
				dtoAccessRoleList.add(dtoAccessRole);
			}
		}
		dtoSearch2.setRecords(dtoAccessRoleList);
		return dtoSearch2;
	}
	
	/**
	 * Description: Get Access Role List for drop down
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch getAccessRoleListForDropDown(DtoSearch dtoSearch) {
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryAccessRole.getCountOfTotalAccessRoles());
		List<AccessRole> accessRoleList = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			accessRoleList = this.repositoryAccessRole.findByIsDeleted(false, pageable);
		} else {
			accessRoleList = this.repositoryAccessRole.findByIsDeleted(false);
		}

		List<DtoAccessRole> dtoAccessRoleList = new ArrayList<>();
		if (accessRoleList != null && !accessRoleList.isEmpty()) {
			DtoAccessRole dtoAccessRole = null;
			for (AccessRole accessRole : accessRoleList) {
				dtoAccessRole = new DtoAccessRole();
				dtoAccessRole.setId(accessRole.getAccessRoleId());
				if (UtilRandomKey.isNotBlank(accessRole.getRoleName())) {
					dtoAccessRole.setRoleName(accessRole.getRoleName());
				} else {
					dtoAccessRole.setRoleName("N/A");
				}
				dtoAccessRoleList.add(dtoAccessRole);
			}

		}
		dtoSearch2.setRecords(dtoAccessRoleList);
		return dtoSearch2;
	}
	
	/**
	 * Description: Delete Multiple Roles
	 * @param idList
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean deleteMultipleRoles(List<Integer> idList) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		boolean status = false;
		try {
			this.repositoryAccessRole.deleteRoles(idList, true, loggedInUserId);
			// delete access role with role group mapping for these roles
			this.repositoryRoleGroupAccessRole.deleteAllRoleGroupAccessRolesForMultipleAccessRole(idList,
					loggedInUserId);
			 
			status = true;
		} catch (NumberFormatException e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}
		return status;
	}
	
	/**
	 * Description: Get Access Role details by access role id
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoAccessRole getAccessRoleDetails(Integer id) {
		DtoAccessRole dtoAccessRole = null;
		AccessRole accessRole = this.repositoryAccessRole.findByAccessRoleIdAndIsDeleted(id, false);
		if (accessRole != null) {
			dtoAccessRole = new DtoAccessRole(accessRole);
		}
		return dtoAccessRole;
	}
	
	/**
	 * Description: save and update fields level access for access role 
	 * @param dtoModulesList
	 * @param accessRole
	 * @return
	 */
	public boolean saveorUpdateAcessRoleScreenAccess(List<DtoModule> dtoModulesList, AccessRole accessRole) {
		boolean result = false;
		try 
		{
			AccessRoleModuleRelation accessRoleModuleRelation = null;
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
			if (dtoModulesList != null && !dtoModulesList.isEmpty()) 
			{
				for (DtoModule dtoModule : dtoModulesList) 
				{
					boolean isActive=true;
					if(!dtoModule.getReadAccess() && !dtoModule.getDeleteAccess() && !dtoModule.getWriteAccess()){
						isActive=false;
					}
					
					int moduleId = dtoModule.getModuleId();
					Module module=repositoryModule.findOne(dtoModule.getModuleId());
					if(module!=null){
						accessRoleModuleRelation = repositoryAccessRoleModuleRelation
								.findByAccessRoleAccessRoleIdAndModuleModuleCodeAndIsDeleted(accessRole.getAccessRoleId(),
										module.getModuleCode(), false);
					}
					
					if (accessRoleModuleRelation == null) 
					{
						accessRoleModuleRelation = new AccessRoleModuleRelation();
						accessRoleModuleRelation.setAccessRole(accessRole);
						accessRoleModuleRelation.setModule(module);
						accessRoleModuleRelation.setIsActive(isActive);
						accessRoleModuleRelation = repositoryAccessRoleModuleRelation.save(accessRoleModuleRelation);
					}
					else
					{
						accessRoleModuleRelation.setIsActive(isActive);
						accessRoleModuleRelation=repositoryAccessRoleModuleRelation.saveAndFlush(accessRoleModuleRelation);
					}
					
					repositoryAccessRoleScreenRelation.deleteByAccessRoleModuleRelation(accessRoleModuleRelation.getAccessRoleModuleRelationId());
					List<DtoScreenDetail> screensList = dtoModule.getScreensList();
					if (screensList != null && !screensList.isEmpty()) 
					{
						for (DtoScreenDetail dtoScreenDetail : screensList) 
						{
							int screenId = dtoScreenDetail.getScreenId();
							List<DtoFieldDetail> fieldsList = dtoScreenDetail.getFieldList();
							if (fieldsList != null && !fieldsList.isEmpty()) 
							{
								for (DtoFieldDetail dtoFieldDetail : fieldsList) 
								{
									AccessRoleScreenRelation accessRoleScreenRelation = repositoryAccessRoleScreenRelation
											.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdAndFieldFieldId(
													accessRole.getAccessRoleId(), moduleId, screenId,
													dtoFieldDetail.getFieldId());
									
									if (accessRoleScreenRelation != null) 
									{
										accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
										accessRoleScreenRelation.setIsDeleted(false);
									} 
									else 
									{
										accessRoleScreenRelation=repositoryAccessRoleScreenRelation.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdAndFieldFieldIdIsNull(accessRole.getAccessRoleId(),moduleId,screenId);
										if (accessRoleScreenRelation != null && accessRoleScreenRelation.getField()==null) 
										{
											accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
											accessRoleScreenRelation.setIsDeleted(false);
										}
										else
										{
											accessRoleScreenRelation=repositoryAccessRoleScreenRelation.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdIsNullAndFieldFieldIdIsNull(accessRole.getAccessRoleId(),moduleId);
											if (accessRoleScreenRelation != null && accessRoleScreenRelation.getField()==null && accessRoleScreenRelation.getScreen()==null) 
											{
												accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
												accessRoleScreenRelation.setIsDeleted(false);
											}
											else
											{
												accessRoleScreenRelation = new AccessRoleScreenRelation();
												accessRoleScreenRelation.setCreatedBy(loggedInUserId);
											}
										}
										
									}
									
									accessRoleScreenRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
									accessRoleScreenRelation.setScreen(repositoryScreen.findOne(screenId));
									accessRoleScreenRelation
											.setField(repositoryFields.findOne(dtoFieldDetail.getFieldId()));
									accessRoleScreenRelation.setReadAccess(dtoFieldDetail.getReadAccess());
									accessRoleScreenRelation.setWriteAccess(dtoFieldDetail.getWriteAccess());
									accessRoleScreenRelation.setDeleteAccess(dtoFieldDetail.getDeleteAccess());
									repositoryAccessRoleScreenRelation.saveAndFlush(accessRoleScreenRelation);
								}
							}
							else
							{
								AccessRoleScreenRelation accessRoleScreenRelation=repositoryAccessRoleScreenRelation.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdAndFieldFieldIdIsNull(accessRole.getAccessRoleId(),moduleId,screenId);
								if (accessRoleScreenRelation != null) 
								{
									accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
									accessRoleScreenRelation.setIsDeleted(false);
								} 
								else 
								{
									accessRoleScreenRelation=repositoryAccessRoleScreenRelation.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdIsNullAndFieldFieldIdIsNull(accessRole.getAccessRoleId(),moduleId);
									if (accessRoleScreenRelation != null) 
									{
										accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
										accessRoleScreenRelation.setIsDeleted(false);
									} 
									else
									{
										accessRoleScreenRelation = new AccessRoleScreenRelation();
										accessRoleScreenRelation.setCreatedBy(loggedInUserId);
									}
								}
								accessRoleScreenRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
								accessRoleScreenRelation.setScreen(repositoryScreen.findOne(screenId));
								if(dtoScreenDetail.getReadAccess()!=null){
									accessRoleScreenRelation.setReadAccess(dtoScreenDetail.getReadAccess());
								}
								else{
									accessRoleScreenRelation.setReadAccess(false);
								}
								
	                            if(dtoScreenDetail.getWriteAccess()!=null){
	                            	 accessRoleScreenRelation.setWriteAccess(dtoScreenDetail.getWriteAccess());
								}
	                            else{
	                            	accessRoleScreenRelation.setWriteAccess(false);
	                            }

								if(dtoScreenDetail.getDeleteAccess()!=null)
								{
									accessRoleScreenRelation.setDeleteAccess(dtoScreenDetail.getDeleteAccess());
								}
								else
								{
									accessRoleScreenRelation.setDeleteAccess(false);
								}
								repositoryAccessRoleScreenRelation.saveAndFlush(accessRoleScreenRelation);
							}
							
						}
					}
					else
					{
						    AccessRoleScreenRelation accessRoleScreenRelation=repositoryAccessRoleScreenRelation.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdIsNullAndFieldFieldIdIsNull(accessRole.getAccessRoleId(),moduleId);
							if (accessRoleScreenRelation != null) 
							{
								accessRoleScreenRelation.setUpdatedBy(loggedInUserId);
								accessRoleScreenRelation.setIsDeleted(false);
							} 
							else 
							{
								accessRoleScreenRelation = new AccessRoleScreenRelation();
								accessRoleScreenRelation.setCreatedBy(loggedInUserId);
							}
							
							accessRoleScreenRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
							if(dtoModule.getReadAccess()!=null){
								accessRoleScreenRelation.setReadAccess(dtoModule.getReadAccess());
							}
							else{
								accessRoleScreenRelation.setReadAccess(false);
							}
							
                            if(dtoModule.getWriteAccess()!=null){
                            	 accessRoleScreenRelation.setWriteAccess(dtoModule.getWriteAccess());
							}
                            else{
                            	accessRoleScreenRelation.setWriteAccess(false);
                            }

							if(dtoModule.getDeleteAccess()!=null)
							{
								accessRoleScreenRelation.setDeleteAccess(dtoModule.getDeleteAccess());
							}
							else
							{
								accessRoleScreenRelation.setDeleteAccess(false);
							}
							repositoryAccessRoleScreenRelation.saveAndFlush(accessRoleScreenRelation);
					}
				}
			}
			result = true;
		} catch (Exception e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
			result = false;
		}
		return result;
	}
	
	/**
	 * Description: save and update fields level access for access role 
	 * @param dtoModulesList
	 * @param accessRole
	 * @return
	 */
	public boolean saveorUpdateAcessRoleScreenAccess1(List<DtoModule> dtoModulesList, AccessRole accessRole) {
		boolean result = false;
		try 
		{
			List<AccessRoleScreenRelation> accessRoleScreenRelationList= new ArrayList<>();
			AccessRoleModuleRelation accessRoleModuleRelation = null;
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
			if (dtoModulesList != null && !dtoModulesList.isEmpty()) 
			{
				for (DtoModule dtoModule : dtoModulesList) 
				{
					boolean isActive=true;
					if(!dtoModule.getReadAccess() && !dtoModule.getDeleteAccess() && !dtoModule.getWriteAccess()){
						isActive=false;
					}
					
					int moduleId = dtoModule.getModuleId();
					Module module=repositoryModule.findOne(moduleId);
					if(module!=null){
						accessRoleModuleRelation = repositoryAccessRoleModuleRelation
								.findByAccessRoleAccessRoleIdAndModuleModuleCodeAndIsDeleted(accessRole.getAccessRoleId(),
										module.getModuleCode(), false);
					}
					
					if (accessRoleModuleRelation == null) 
					{
						accessRoleModuleRelation = new AccessRoleModuleRelation();
						accessRoleModuleRelation.setAccessRole(accessRole);
						accessRoleModuleRelation.setModule(module);
					}
					
					accessRoleModuleRelation.setIsActive(isActive);
					accessRoleModuleRelation = repositoryAccessRoleModuleRelation.saveAndFlush(accessRoleModuleRelation);
					repositoryAccessRoleScreenRelation.deleteByAccessRoleModuleRelation(accessRoleModuleRelation.getAccessRoleModuleRelationId());
					List<DtoScreenDetail> screensList = dtoModule.getScreensList();
					for (DtoScreenDetail dtoScreenDetail : screensList) 
					{
							int screenId = dtoScreenDetail.getScreenId();
							Screen screen=repositoryScreen.findOne(screenId);
							List<DtoFieldDetail> fieldsList = dtoScreenDetail.getFieldList();
							for (DtoFieldDetail dtoFieldDetail : fieldsList) 
							{
								Field field=repositoryFields.findOne(dtoFieldDetail.getFieldId());
								AccessRoleScreenRelation accessRoleScreenRelation= new AccessRoleScreenRelation();
								accessRoleScreenRelation.setIsDeleted(false);
								accessRoleScreenRelation.setCreatedBy(loggedInUserId);
								accessRoleScreenRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
								accessRoleScreenRelation.setScreen(screen);
								accessRoleScreenRelation.setField(field);
								accessRoleScreenRelation.setReadAccess(dtoFieldDetail.getReadAccess());
								accessRoleScreenRelation.setWriteAccess(dtoFieldDetail.getWriteAccess());
								accessRoleScreenRelation.setDeleteAccess(dtoFieldDetail.getDeleteAccess());
								accessRoleScreenRelationList.add(accessRoleScreenRelation);
							}
					}
				}
				repositoryAccessRoleScreenRelation.save(accessRoleScreenRelationList);
				result = true;
			}
		} catch (Exception e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
			result = false;
		}
		return result;
	}
	
	
	/**
	 * Description: Search access role by search keyword
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchAcessRole(DtoSearch dtoSearch) {
		if (dtoSearch != null) {
			dtoSearch.setTotalCount(
					this.repositoryAccessRole.predictiveAccessRoleSearchTotalCount(dtoSearch.getSearchKeyword()));
			List<AccessRole> accessRoleList = this.repositoryAccessRole.predictiveAccessRoleSearchWithPagination(
					dtoSearch.getSearchKeyword(),
					new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"));
			if (accessRoleList != null && !accessRoleList.isEmpty()) {
				List<DtoAccessRole> dtoAccessRoleList = new ArrayList<>();
				for (AccessRole accessRole : accessRoleList) {
					dtoAccessRoleList.add(new DtoAccessRole(accessRole.getAccessRoleId(), accessRole.getRoleCode(),
							accessRole.getRoleName(), accessRole.getRoleDescription()));
				}
				dtoSearch.setRecords(dtoAccessRoleList);
			}
		}
		return dtoSearch;
	}

	/**
	 * Description: save and update transaction level access for access role 
	 * @param dtoModulesList
	 * @param accessRole
	 * @return
	 */
	/*public boolean saveorUpdateAcessRoleTransactionAccess(List<DtoModule> dtoModulesList, AccessRole accessRole) {
		int accessRoleId = accessRole.getAccessRoleId();
		boolean result = false;
		try {
			AccessRoleModuleRelation accessRoleModuleRelation = null;
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
			if (dtoModulesList != null && !dtoModulesList.isEmpty()) {
				for (DtoModule dtoModule : dtoModulesList) {
					int moduleId = dtoModule.getModuleId();
					accessRoleModuleRelation = repositoryAccessRoleModuleRelation
							.findByAccessRoleAccessRoleIdAndModuleModuleIdAndIsDeleted(accessRole.getAccessRoleId(),
									moduleId, false);
					if (accessRoleModuleRelation == null) {
						accessRoleModuleRelation = new AccessRoleModuleRelation();
						accessRoleModuleRelation.setAccessRole(accessRole);
						accessRoleModuleRelation.setModule(repositoryModule.findOne(moduleId));
						accessRoleModuleRelation = repositoryAccessRoleModuleRelation.save(accessRoleModuleRelation);
					}

					List<DtoTransactionType> transactionTypeList = dtoModule.getTransactionTypeList();
					if (transactionTypeList != null && !transactionTypeList.isEmpty()) 
					{
						for (DtoTransactionType transactionType : transactionTypeList) 
						{
							int tTypeId = transactionType.getTransactionTypeId();
							List<DtoTransaction> transactionsList = transactionType.getTransactionsList();
							if (transactionsList != null && !transactionsList.isEmpty() ) 
							{
								for (DtoTransaction dtoTransaction : transactionsList) 
								{
									AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
											.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionsTransactionIdAndTransactionTypeTransactionTypeId(
													accessRoleId, moduleId, dtoTransaction.getTransactionId(), tTypeId);
									if (accessRoleTransactionRelation != null) 
									{
										accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
										accessRoleTransactionRelation.setIsDeleted(false);
									}
									else 
									{
										accessRoleTransactionRelation= repositoryAccessRoleTransactionRelation
												.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionTypeTransactionTypeIdAndTransactionsTransactionIdIsNull(
														accessRoleId, moduleId,tTypeId);
										if (accessRoleTransactionRelation != null && accessRoleTransactionRelation.getTransactions()==null) 
										{
											accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
											accessRoleTransactionRelation.setIsDeleted(false);
										}
										else
										{
											accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
													.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionsTransactionIdIsNullAndTransactionTypeTransactionTypeIdIsNull(
															accessRoleId, moduleId);
											if(accessRoleTransactionRelation!=null && accessRoleTransactionRelation.getTransactions()==null && accessRoleTransactionRelation.getTransactionType()==null)
											{
												accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
												accessRoleTransactionRelation.setIsDeleted(false);
											}
											else{
												accessRoleTransactionRelation = new AccessRoleTransactionRelation();
												accessRoleTransactionRelation.setCreatedBy(loggedInUserId);
											}
										}
									}
									
									accessRoleTransactionRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
									accessRoleTransactionRelation.setTransactions(
											repositoryTransactions.findOne(dtoTransaction.getTransactionId()));
									accessRoleTransactionRelation
											.setTransactionType(repositoryTransactionTypes.findOne(tTypeId));
									accessRoleTransactionRelation.setViewAccess(dtoTransaction.getViewAccess());
									accessRoleTransactionRelation.setPostAccess(dtoTransaction.getPostAccess());
									accessRoleTransactionRelation.setDeleteAccess(dtoTransaction.getDeleteAccess());
									repositoryAccessRoleTransactionRelation.saveAndFlush(accessRoleTransactionRelation);
								}
							}
							else
							{
								AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
										.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionTypeTransactionTypeIdAndTransactionsTransactionIdIsNull(
												accessRoleId, moduleId,tTypeId);
								if (accessRoleTransactionRelation != null) 
								{
									accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
									accessRoleTransactionRelation.setIsDeleted(false);
								}
								else 
								{
									accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
											.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionsTransactionIdIsNullAndTransactionTypeTransactionTypeIdIsNull(
													accessRoleId, moduleId);
									if(accessRoleTransactionRelation!=null)
									{
										accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
										accessRoleTransactionRelation.setIsDeleted(false);
									}
									else
									{
										accessRoleTransactionRelation = new AccessRoleTransactionRelation();
										accessRoleTransactionRelation.setCreatedBy(loggedInUserId);
									}
								}
								accessRoleTransactionRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
								accessRoleTransactionRelation.setTransactionType(repositoryTransactionTypes.findOne(tTypeId));
								if(transactionType.getViewAccess()!=null){
									accessRoleTransactionRelation.setViewAccess(transactionType.getViewAccess());
								}
								else{
									accessRoleTransactionRelation.setViewAccess(false);
								}
								
		                        if(transactionType.getPostAccess()!=null)
		                        {
		                        	accessRoleTransactionRelation.setPostAccess(transactionType.getPostAccess());
								}
		                        else
		                        {
		                        	accessRoleTransactionRelation.setPostAccess(false);
		                        }

								if(transactionType.getDeleteAccess()!=null)
								{
									accessRoleTransactionRelation.setDeleteAccess(transactionType.getDeleteAccess());
								}
								else
								{
									accessRoleTransactionRelation.setDeleteAccess(false);
								}
								repositoryAccessRoleTransactionRelation.saveAndFlush(accessRoleTransactionRelation);
							}
						}
					}
					else
					{
						AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
								.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionsTransactionIdIsNullAndTransactionTypeTransactionTypeIdIsNull(
										accessRoleId, moduleId);
						if (accessRoleTransactionRelation != null) 
						{
							accessRoleTransactionRelation.setUpdatedBy(loggedInUserId);
							accessRoleTransactionRelation.setIsDeleted(false);
						} 
						else 
						{
							accessRoleTransactionRelation = new AccessRoleTransactionRelation();
							accessRoleTransactionRelation.setCreatedBy(loggedInUserId);
						}
						accessRoleTransactionRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
						if(dtoModule.getViewAccess()!=null){
							accessRoleTransactionRelation.setViewAccess(dtoModule.getViewAccess());
						}
						else{
							accessRoleTransactionRelation.setViewAccess(false);
						}
						
                        if(dtoModule.getPostAccess()!=null)
                        {
                        	accessRoleTransactionRelation.setPostAccess(dtoModule.getPostAccess());
						}
                        else
                        {
                        	accessRoleTransactionRelation.setPostAccess(false);
                        }

						if(dtoModule.getDeleteAccess()!=null)
						{
							accessRoleTransactionRelation.setDeleteAccess(dtoModule.getDeleteAccess());
						}
						else
						{
							accessRoleTransactionRelation.setDeleteAccess(false);
						}
						repositoryAccessRoleTransactionRelation.saveAndFlush(accessRoleTransactionRelation);
					}
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}*/

	/**
	 * Description: save and update report level access for access role 
	 * @param dtoModulesList
	 * @param accessRole
	 * @return
	 */
	/*public boolean saveorUpdateAcessRoleReportAccess(List<DtoModule> dtoModulesList, AccessRole accessRole) {
		int accessRoleId = accessRole.getAccessRoleId();
		boolean result = false;
		try {
			AccessRoleModuleRelation accessRoleModuleRelation = null;
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
			if (dtoModulesList != null && !dtoModulesList.isEmpty()) {
				for (DtoModule dtoModule : dtoModulesList) {
					int moduleId = dtoModule.getModuleId();
					accessRoleModuleRelation = repositoryAccessRoleModuleRelation
							.findByAccessRoleAccessRoleIdAndModuleModuleIdAndIsDeleted(accessRole.getAccessRoleId(),
									moduleId, false);
					if (accessRoleModuleRelation == null) {
						accessRoleModuleRelation = new AccessRoleModuleRelation();
						accessRoleModuleRelation.setAccessRole(accessRole);
						accessRoleModuleRelation.setModule(repositoryModule.findOne(moduleId));
						accessRoleModuleRelation = repositoryAccessRoleModuleRelation.save(accessRoleModuleRelation);
					}
					
					 List<DtoReportCategory> reportCategoryList = dtoModule.getReportCategoryList();
					 if (reportCategoryList != null && !reportCategoryList.isEmpty()) 
					 {
						for (DtoReportCategory dtoReportCategory : reportCategoryList) 
						{
							int rCategoryId = dtoReportCategory.getReportCategoryId();
							List<DtoReport> reportList = dtoReportCategory.getReportList();
							if (reportList != null && !reportList.isEmpty()) 
							{
								for (DtoReport dtoReport : reportList) {
									AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
											.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdAndReportCategoryReportCategoryId(
													moduleId, accessRoleId, dtoReport.getReportId(), rCategoryId);
									if (accessRoleReportRelation != null) 
									{
										accessRoleReportRelation.setUpdatedBy(loggedInUserId);
										accessRoleReportRelation.setIsDeleted(false);
									} 
									else 
									{
										accessRoleReportRelation = repositoryAccessRoleReportRelation
												.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportCategoryReportCategoryIdAndReportsReportIdIsNull(
														moduleId, accessRoleId,rCategoryId);
										if (accessRoleReportRelation != null && accessRoleReportRelation.getReports()==null) {
											accessRoleReportRelation.setUpdatedBy(loggedInUserId);
											accessRoleReportRelation.setIsDeleted(false);
										}
										else
										{
											accessRoleReportRelation = repositoryAccessRoleReportRelation
													.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdIsNullAndReportCategoryReportCategoryIdIsNull(
															moduleId, accessRoleId);
											if (accessRoleReportRelation != null && accessRoleReportRelation.getReportCategory()==null && accessRoleReportRelation.getReports()==null) 
											{
												accessRoleReportRelation.setUpdatedBy(loggedInUserId);
												accessRoleReportRelation.setIsDeleted(false);
											}
											else
											{
												accessRoleReportRelation = new AccessRoleReportRelation();
												accessRoleReportRelation.setCreatedBy(loggedInUserId);
											}
										}
									}

									accessRoleReportRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
									accessRoleReportRelation
											.setReports(repositoryReports.findOne(dtoReport.getReportId()));
									accessRoleReportRelation
											.setReportCategory(repositoryReportCategory.findOne(rCategoryId));
									accessRoleReportRelation.setEmailAccess(dtoReport.getEmailAccess());
									accessRoleReportRelation.setViewAccess(dtoReport.getViewAccess());
									accessRoleReportRelation.setExportAccess(dtoReport.getExportAccess());
									repositoryAccessRoleReportRelation.saveAndFlush(accessRoleReportRelation);
								}
							}
							else
							{
								AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
										.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportCategoryReportCategoryIdAndReportsReportIdIsNull(
												moduleId, accessRoleId,rCategoryId);
								if (accessRoleReportRelation != null) 
								{
									accessRoleReportRelation.setUpdatedBy(loggedInUserId);
									accessRoleReportRelation.setIsDeleted(false);
								} 
								else 
								{
									accessRoleReportRelation = repositoryAccessRoleReportRelation
											.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdIsNullAndReportCategoryReportCategoryIdIsNull(
													moduleId, accessRoleId);
									if (accessRoleReportRelation != null) 
									{
										accessRoleReportRelation.setUpdatedBy(loggedInUserId);
										accessRoleReportRelation.setIsDeleted(false);
									} 
									else
									{
										accessRoleReportRelation = new AccessRoleReportRelation();
										accessRoleReportRelation.setCreatedBy(loggedInUserId);
									}
									
								}
								
								accessRoleReportRelation.setReportCategory(repositoryReportCategory.findOne(rCategoryId));
								accessRoleReportRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
								if(dtoReportCategory.getEmailAccess()!=null){
									accessRoleReportRelation.setEmailAccess(dtoReportCategory.getEmailAccess());
								}
								else{
									accessRoleReportRelation.setEmailAccess(false);
								}
								
		                        if(dtoReportCategory.getViewAccess()!=null)
		                        {
		                        	accessRoleReportRelation.setViewAccess(dtoReportCategory.getViewAccess());
								}
		                        else
		                        {
		                        	accessRoleReportRelation.setViewAccess(false);
		                        }

								if(dtoReportCategory.getExportAccess()!=null)
								{
									accessRoleReportRelation.setExportAccess(dtoReportCategory.getExportAccess());
								}
								else
								{
									accessRoleReportRelation.setExportAccess(false);
								}
								repositoryAccessRoleReportRelation.saveAndFlush(accessRoleReportRelation);
							}
							
						}
					}
					else
					{
						AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
								.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdIsNullAndReportCategoryReportCategoryIdIsNull(
										moduleId, accessRoleId);
						if (accessRoleReportRelation != null) {
							accessRoleReportRelation.setUpdatedBy(loggedInUserId);
							accessRoleReportRelation.setIsDeleted(false);
						} else {
							accessRoleReportRelation = new AccessRoleReportRelation();
							accessRoleReportRelation.setCreatedBy(loggedInUserId);
						}

						accessRoleReportRelation.setAccessRoleModuleRelation(accessRoleModuleRelation);
						if(dtoModule.getEmailAccess()!=null){
							accessRoleReportRelation.setEmailAccess(dtoModule.getEmailAccess());
						}
						else{
							accessRoleReportRelation.setEmailAccess(false);
						}
						
                        if(dtoModule.getViewAccess()!=null)
                        {
                        	accessRoleReportRelation.setViewAccess(dtoModule.getViewAccess());
						}
                        else
                        {
                        	accessRoleReportRelation.setViewAccess(false);
                        }

						if(dtoModule.getExportAccess()!=null)
						{
							accessRoleReportRelation.setExportAccess(dtoModule.getExportAccess());
						}
						else
						{
							accessRoleReportRelation.setExportAccess(false);
						}
						repositoryAccessRoleReportRelation.saveAndFlush(accessRoleReportRelation);
					}
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
*/
	/**
	 * Description: get all fields levels data for access role 
	 * @param dtoScreenDetail
	 * @return
	 */
	public List<DtoModule> getAllScreenDetailList(DtoScreenDetail dtoScreenDetail) {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoFieldDetail dtoFieldDetail;
		List<Module> moduleList = null;
		
		if (dtoScreenDetail.getPageNumber() != null && dtoScreenDetail.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoScreenDetail.getPageNumber(), dtoScreenDetail.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false,true,Integer.parseInt(langId),pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false,true,Integer.parseInt(langId));
		}
		
		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) 
			{
				if(module.getIsActive())
				{
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleReadAccess = false;
					boolean moduleWriteAccess = false;
					boolean moduleDeleteAccess = false;
	
					List<DtoScreenDetail> dtoScreenDetailsList = new ArrayList<>();
					List<Screen> screenList = repositoryScreen.findByIsDeletedAndModuleModuleId(false,
							module.getModuleId());
					if (screenList != null && !screenList.isEmpty()) {
						for (Screen screen : screenList) {
							boolean screenreadAccess = false;
							boolean screenWriteAccess = false;
							boolean screenDeleteAccess = false;
							List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
							dtoScreenDetail = new DtoScreenDetail(screen, langId);
	
							List<Field> fieldList = repositoryFields
									.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(screen.getScreenId(), false, false);
							if (fieldList != null && !fieldList.isEmpty()) {
								for (Field field : fieldList) {
									dtoFieldDetail = new DtoFieldDetail(field, langId);
									dtoFieldDetail.setReadAccess(false);
									dtoFieldDetail.setWriteAccess(false);
									dtoFieldDetail.setDeleteAccess(false);
									dtoFieldDetails.add(dtoFieldDetail);
								}
							}
							dtoScreenDetail.setDeleteAccess(screenDeleteAccess);
							dtoScreenDetail.setReadAccess(screenreadAccess);
							dtoScreenDetail.setWriteAccess(screenWriteAccess);
							dtoScreenDetail.setFieldList(dtoFieldDetails);
							dtoScreenDetailsList.add(dtoScreenDetail);
						}
					}
					dtoModule.setWriteAccess(moduleWriteAccess);
					dtoModule.setReadAccess(moduleReadAccess);
					dtoModule.setDeleteAccess(moduleDeleteAccess);
					dtoModule.setScreensList(dtoScreenDetailsList);
					dtoModulesList.add(dtoModule);
				}
			}
		}

		return dtoModulesList;
	}

	/**
	 * Description: get all fields levels data for access role by access role id
	 * @param accessRole
	 * @param dtoScreenDetail
	 * @return
	 */
	public List<DtoModule> getAllScreenDetailListByAccessRoleId(AccessRole accessRole,
			DtoScreenDetail dtoScreenDetail) {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoFieldDetail dtoFieldDetail;
		List<Module> moduleList = null;
		if (dtoScreenDetail.getPageNumber() != null && dtoScreenDetail.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoScreenDetail.getPageNumber(), dtoScreenDetail.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true,
					Integer.parseInt(langId), pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true,
					Integer.parseInt(langId));
		}

		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) {
				if (module.getIsActive()) {
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleReadAccess = false;
					boolean moduleWriteAccess = false;
					boolean moduleDeleteAccess = false;

					List<DtoScreenDetail> dtoScreenDetailsList = new ArrayList<>();
					List<Screen> screenList = repositoryScreen.findByIsDeletedAndModuleModuleId(false,
							module.getModuleId());
					if (screenList != null && !screenList.isEmpty()) {
						for (Screen screen : screenList) {
							boolean screenreadAccess = false;
							boolean screenWriteAccess = false;
							boolean screenDeleteAccess = false;
							List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
							dtoScreenDetail = new DtoScreenDetail(screen, langId);

							List<Field> fieldList = repositoryFields.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(
									screen.getScreenId(), false, false);
							if (fieldList != null && !fieldList.isEmpty()) {
								for (Field field : fieldList) {
									dtoFieldDetail = new DtoFieldDetail(field, langId);
									AccessRoleScreenRelation accessRoleModuleRelation = repositoryAccessRoleScreenRelation
											.findByAccessRoleModuleRelationModuleModuleCodeAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndScreenScreenCodeAndFieldFieldCodeAndIsDeleted(
													module.getModuleCode(), accessRole.getAccessRoleId(),
													screen.getScreenCode(), field.getFieldCode(), false);

									if (accessRoleModuleRelation != null) {
										dtoFieldDetail.setReadAccess(accessRoleModuleRelation.getReadAccess());
										dtoFieldDetail.setWriteAccess(accessRoleModuleRelation.getWriteAccess());
										dtoFieldDetail.setDeleteAccess(accessRoleModuleRelation.getDeleteAccess());
										if (accessRoleModuleRelation.getReadAccess()) {
											moduleReadAccess = true;
											screenreadAccess = true;
										}
										if (accessRoleModuleRelation.getWriteAccess()) {
											moduleWriteAccess = true;
											screenWriteAccess = true;
										}
										if (accessRoleModuleRelation.getDeleteAccess()) {
											moduleDeleteAccess = true;
											screenDeleteAccess = true;
										}
									} else {
										dtoFieldDetail.setReadAccess(false);
										dtoFieldDetail.setWriteAccess(false);
										dtoFieldDetail.setDeleteAccess(false);
									}
									dtoFieldDetails.add(dtoFieldDetail);
								}
							} else {
								AccessRoleScreenRelation accessRoleModuleRelation = repositoryAccessRoleScreenRelation
										.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleCodeAndScreenScreenCodeAndFieldFieldIdIsNull(
												accessRole.getAccessRoleId(), module.getModuleCode(),
												screen.getScreenCode());
								if (accessRoleModuleRelation != null) {
									if (accessRoleModuleRelation.getReadAccess()) {
										moduleReadAccess = true;
										screenreadAccess = true;
									}
									if (accessRoleModuleRelation.getWriteAccess()) {
										moduleWriteAccess = true;
										screenWriteAccess = true;
									}
									if (accessRoleModuleRelation.getDeleteAccess()) {
										moduleDeleteAccess = true;
										screenDeleteAccess = true;
									}
								}
							}
							dtoScreenDetail.setDeleteAccess(screenDeleteAccess);
							dtoScreenDetail.setReadAccess(screenreadAccess);
							dtoScreenDetail.setWriteAccess(screenWriteAccess);
							dtoScreenDetail.setFieldList(dtoFieldDetails);
							dtoScreenDetailsList.add(dtoScreenDetail);
						}
					} else {
						AccessRoleScreenRelation accessRoleModuleRelation = repositoryAccessRoleScreenRelation
								.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleCodeAndScreenScreenIdIsNullAndFieldFieldIdIsNull(
										accessRole.getAccessRoleId(), module.getModuleCode());
						if (accessRoleModuleRelation != null) {
							if (accessRoleModuleRelation.getReadAccess()) {
								moduleReadAccess = true;
							}
							if (accessRoleModuleRelation.getWriteAccess()) {
								moduleWriteAccess = true;
							}
							if (accessRoleModuleRelation.getDeleteAccess()) {
								moduleDeleteAccess = true;
							}
						}
					}
					dtoModule.setWriteAccess(moduleWriteAccess);
					dtoModule.setReadAccess(moduleReadAccess);
					dtoModule.setDeleteAccess(moduleDeleteAccess);
					dtoModule.setScreensList(dtoScreenDetailsList);
					dtoModulesList.add(dtoModule);
				}
			}
		}

		return dtoModulesList;
	}
	
	/*
	
	public List<DtoModule> getAllScreenDetailListByAccessRoleId1(AccessRole accessRole,
			DtoScreenDetail dtoScreenDetail) {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoFieldDetail dtoFieldDetail;
		List<Module> moduleList = null;
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false,true,Integer.parseInt(langId));
		
		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) 
			{
				if(module.getIsActive())
				{
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleReadAccess = false;
					boolean moduleWriteAccess = false;
					boolean moduleDeleteAccess = false;
					List<DtoScreenDetail> dtoScreenDetailsList = new ArrayList<>();
					
					List<AccessRoleScreenRelation> accessRoleScreenRelationList = repositoryAccessRoleScreenRelation
							.findByAccessRoleModuleRelationModuleModuleCodeAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndIsDeleted(
									module.getModuleCode(), accessRole.getAccessRoleId(),
									 false);
					if(accessRoleScreenRelationList!=null && !accessRoleScreenRelationList.isEmpty()){
						
						for (AccessRoleScreenRelation accessRoleScreenRelation : accessRoleScreenRelationList) {
							
							List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
							DtoScreenDetail dtoScreenDetail1 = new DtoScreenDetail(accessRoleScreenRelation.getScreen(), langId);
	
							List<Field> fieldList = repositoryFields
									.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(accessRoleScreenRelation.getScreen().getScreenId(), false, false);
								for (Field field : fieldList) {
									dtoFieldDetail = new DtoFieldDetail(field, langId);
									

									dtoFieldDetail.setReadAccess(accessRoleScreenRelation.getReadAccess());
									dtoFieldDetail.setWriteAccess(accessRoleScreenRelation.getWriteAccess());
									dtoFieldDetail.setDeleteAccess(accessRoleScreenRelation.getDeleteAccess());
								dtoFieldDetails.add(dtoFieldDetail);
								}
								
								if (accessRoleScreenRelation.getReadAccess()) {
									moduleReadAccess = true;
									dtoScreenDetail1.setReadAccess(true);
								}
								if (accessRoleScreenRelation.getWriteAccess()) {
									moduleWriteAccess = true;
									dtoScreenDetail1.setWriteAccess(true);
								}
								if (accessRoleScreenRelation.getDeleteAccess()) {
									moduleDeleteAccess = true;
									dtoScreenDetail1.setDeleteAccess(true);
								}
								
								dtoScreenDetail1.setFieldList(dtoFieldDetails);
								dtoScreenDetailsList.add(dtoScreenDetail1);
								
								}
						
						
					}
					else
					{
						List<Screen> screenList = repositoryScreen.findByIsDeletedAndModuleModuleId(false,
								module.getModuleId());
							for (Screen screen : screenList) {
								List<DtoFieldDetail> dtoFieldDetails = new ArrayList<>();
								DtoScreenDetail dtoScreenDetail2 = new DtoScreenDetail(screen, langId);
		
								List<Field> fieldList = repositoryFields
										.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(screen.getScreenId(), false, false);
									for (Field field : fieldList) {
										dtoFieldDetail = new DtoFieldDetail(field, langId);
										
											dtoFieldDetail.setReadAccess(false);
											dtoFieldDetail.setWriteAccess(false);
											dtoFieldDetail.setDeleteAccess(false);
										dtoFieldDetails.add(dtoFieldDetail);
									}
									dtoScreenDetail2.setDeleteAccess(false);
									dtoScreenDetail2.setReadAccess(false);
									dtoScreenDetail2.setWriteAccess(false);
									dtoScreenDetail2.setFieldList(dtoFieldDetails);
								dtoScreenDetailsList.add(dtoScreenDetail2);
							}
						
						
					}
	
					dtoModule.setWriteAccess(moduleWriteAccess);
					dtoModule.setReadAccess(moduleReadAccess);
					dtoModule.setDeleteAccess(moduleDeleteAccess);
					dtoModule.setScreensList(dtoScreenDetailsList);
					dtoModulesList.add(dtoModule);
					
				
				}
			}
		}

		return dtoModulesList;
	}*/

	/**
	 * Description: get all transaction levels data for access role
	 * @param dtoTransactionType
	 * @return
	 */
	/*public List<DtoModule> getAllAccessRolesTrasationList(DtoTransactionType dtoTransactionType) {
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoTransaction dtoTransaction = null;
		List<Module> moduleList = null;
		if (dtoTransactionType.getPageNumber() != null && dtoTransactionType.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoTransactionType.getPageNumber(), dtoTransactionType.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true,pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true);
		}

		if (moduleList != null && !moduleList.isEmpty()) 
		{
			for (Module module : moduleList) 
			{
				if(module.getIsActive())
				{
					DtoModule dtoModule = new DtoModule(module);
					boolean modulePostAccess = false;
					boolean moduleDeleteAccess = false;
					boolean moduleViewAccess = false;
					List<DtoTransactionType> dtoTransactionTypeList = new ArrayList<>();
					List<TransactionType> transactionTypesList = repositoryTransactionTypes
							.findByIsDeletedAndModuleModuleId(false, module.getModuleId());
					if (transactionTypesList != null && !transactionTypesList.isEmpty()) {
						for (TransactionType transactionType : transactionTypesList) {
							boolean transPostAccess = false;
							boolean transDeleteAccess = false;
							boolean transViewAccess = false;
							int transactionTypeId = transactionType.getTransactionTypeId();
							List<DtoTransaction> dtoTransactionList = new ArrayList<>();
							dtoTransactionType = new DtoTransactionType(transactionType);
							List<Transactions> transactionsList = repositoryTransactions
									.findByTransactionTypeTransactionTypeIdAndTransactionTypeIsDeletedAndIsDeleted(
											transactionTypeId, false, false);
							if (transactionsList != null && !transactionsList.isEmpty()) {
								for (Transactions transactions : transactionsList) {
									dtoTransaction = new DtoTransaction(transactions);
									dtoTransaction.setPostAccess(false);
									dtoTransaction.setViewAccess(false);
									dtoTransaction.setDeleteAccess(false);
									dtoTransactionList.add(dtoTransaction);
								}
							}
							dtoTransactionType.setDeleteAccess(transDeleteAccess);
							dtoTransactionType.setViewAccess(transViewAccess);
							dtoTransactionType.setPostAccess(transPostAccess);
							dtoTransactionType.setTransactionsList(dtoTransactionList);
							dtoTransactionTypeList.add(dtoTransactionType);
						}
					}
					dtoModule.setDeleteAccess(moduleDeleteAccess);
					dtoModule.setPostAccess(modulePostAccess);
					dtoModule.setViewAccess(moduleViewAccess);
					dtoModule.setTransactionTypeList(dtoTransactionTypeList);
					dtoModulesList.add(dtoModule);
				}
			}
		}

		return dtoModulesList;
	}
*/
	/**
	 * Description: get all transaction levels data for access role by access role id
	 * @param dtoTransactionType
	 * @param accessRole
	 * @return
	 */
	/*public List<DtoModule> getAllAccessRolesTrasationListByAccessRoleId(DtoTransactionType dtoTransactionType,
			AccessRole accessRole) {
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoTransaction dtoTransaction = null;
		List<Module> moduleList = null;
		if (dtoTransactionType.getPageNumber() != null && dtoTransactionType.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoTransactionType.getPageNumber(), dtoTransactionType.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true,pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true);
		}

		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) 
			{
			     if(module.getIsActive())
				 {
					
					DtoModule dtoModule = new DtoModule(module);
					boolean modulePostAccess = false;
					boolean moduleDeleteAccess = false;
					boolean moduleViewAccess = false;
					List<DtoTransactionType> dtoTransactionTypeList = new ArrayList<>();
					List<TransactionType> transactionTypesList = repositoryTransactionTypes
							.findByIsDeletedAndModuleModuleId(false, module.getModuleId());
					if (transactionTypesList != null && !transactionTypesList.isEmpty()) 
					{
						for (TransactionType transactionType : transactionTypesList) {
							boolean transPostAccess = false;
							boolean transDeleteAccess = false;
							boolean transViewAccess = false;
							int transactionTypeId = transactionType.getTransactionTypeId();
							List<DtoTransaction> dtoTransactionList = new ArrayList<>();
							dtoTransactionType = new DtoTransactionType(transactionType);
							List<Transactions> transactionsList = repositoryTransactions
									.findByTransactionTypeTransactionTypeIdAndTransactionTypeIsDeletedAndIsDeleted(
											transactionTypeId, false, false);
							if (transactionsList != null && !transactionsList.isEmpty()) {
								for (Transactions transactions : transactionsList) {
									int transactionId = transactions.getTransactionId();
									dtoTransaction = new DtoTransaction(transactions);
									AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
											.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndTransactionsTransactionIdAndTransactionTypeTransactionTypeIdAndIsDeleted(
													module.getModuleId(), accessRole.getAccessRoleId(), transactionId,
													transactionTypeId, false);
									if (accessRoleTransactionRelation != null) {
										dtoTransaction.setPostAccess(accessRoleTransactionRelation.getPostAccess());
										dtoTransaction.setViewAccess(accessRoleTransactionRelation.getViewAccess());
										dtoTransaction.setDeleteAccess(accessRoleTransactionRelation.getDeleteAccess());
	
										if (accessRoleTransactionRelation.getPostAccess()) {
											modulePostAccess = true;
											transPostAccess = true;
										}
	
										if (accessRoleTransactionRelation.getViewAccess()) {
											moduleViewAccess = true;
											transViewAccess = true;
	
										}
	
										if (accessRoleTransactionRelation.getDeleteAccess()) {
											moduleDeleteAccess = true;
											transDeleteAccess = true;
										}
									} else {
										dtoTransaction.setPostAccess(false);
										dtoTransaction.setViewAccess(false);
										dtoTransaction.setDeleteAccess(false);
									}
									dtoTransactionList.add(dtoTransaction);
								}
							}
							else{
								AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
										.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionTypeTransactionTypeIdAndTransactionsTransactionIdIsNull(
												accessRole.getAccessRoleId(), module.getModuleId(),transactionTypeId);
								if(accessRoleTransactionRelation!=null)
								{
									if (accessRoleTransactionRelation.getPostAccess()) {
										modulePostAccess = true;
										transPostAccess = true;
									}
	
									if (accessRoleTransactionRelation.getViewAccess()) {
										moduleViewAccess = true;
										transViewAccess = true;
	
									}
	
									if (accessRoleTransactionRelation.getDeleteAccess()) {
										moduleDeleteAccess = true;
										transDeleteAccess = true;
									}
								}
								
							}
							dtoTransactionType.setDeleteAccess(transDeleteAccess);
							dtoTransactionType.setViewAccess(transViewAccess);
							dtoTransactionType.setPostAccess(transPostAccess);
							dtoTransactionType.setTransactionsList(dtoTransactionList);
							dtoTransactionTypeList.add(dtoTransactionType);
						}
					}
					else
					{
						AccessRoleTransactionRelation accessRoleTransactionRelation = repositoryAccessRoleTransactionRelation
								.findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndTransactionsTransactionIdIsNullAndTransactionTypeTransactionTypeIdIsNull(
										accessRole.getAccessRoleId(), module.getModuleId());
						if(accessRoleTransactionRelation!=null)
						{
							if (accessRoleTransactionRelation.getPostAccess()) {
								modulePostAccess = true;
							}
	
							if (accessRoleTransactionRelation.getViewAccess()) {
								moduleViewAccess = true;
							}
	
							if (accessRoleTransactionRelation.getDeleteAccess()) {
								moduleDeleteAccess = true;
							}
						}
					}
					dtoModule.setDeleteAccess(moduleDeleteAccess);
					dtoModule.setPostAccess(modulePostAccess);
					dtoModule.setViewAccess(moduleViewAccess);
					dtoModule.setTransactionTypeList(dtoTransactionTypeList);
					dtoModulesList.add(dtoModule);
			   }
			}
		}

		return dtoModulesList;
	}*/

	/**
	 * Description: get all report levels data for access role
	 * @param dtoReportCategory
	 * @return
	 */
	/*public List<DtoModule> getAllAccessRolesReportsList(DtoReportCategory dtoReportCategory) {
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoReport dtoReport = null;
		List<Module> moduleList = null;
		if (dtoReportCategory.getPageNumber() != null && dtoReportCategory.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoReportCategory.getPageNumber(), dtoReportCategory.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true,pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true);
		}

		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) 
			{
				if(module.getIsActive())
				{
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleExportAccess = false;
					boolean moduleEmailAccess = false;
					boolean moduleViewAccess = false;

					List<DtoReportCategory> dtoReportCategoryList = new ArrayList<>();
					List<ReportCategory> reportCategoryList = repositoryReportCategory
							.findByIsDeletedAndModuleModuleId(false, module.getModuleId());
					if (reportCategoryList != null && !reportCategoryList.isEmpty()) 
					{
						for (ReportCategory reportCategory : reportCategoryList) 
						{
							boolean reportExportAccess = false;
							boolean reportEmailAccess = false;
							boolean reportViewAccess = false;
							int reportCategoryId = reportCategory.getReportCategoryId();
							List<DtoReport> dtoReportList = new ArrayList<>();
							dtoReportCategory = new DtoReportCategory(reportCategory);
							
							List<Reports> reportList = repositoryReports
									.findByReportCategoryReportCategoryIdAndReportCategoryIsDeletedAndIsDeleted(
											reportCategoryId, false, false);
							if (reportList != null && !reportList.isEmpty()) 
							{
								for (Reports reports : reportList) {
									dtoReport = new DtoReport(reports);
									dtoReport.setExportAccess(false);
									dtoReport.setViewAccess(false);
									dtoReport.setEmailAccess(false);
									dtoReportList.add(dtoReport);
								}
							}
							dtoReportCategory.setEmailAccess(reportEmailAccess);
							dtoReportCategory.setViewAccess(reportViewAccess);
							dtoReportCategory.setExportAccess(reportExportAccess);
							dtoReportCategory.setReportList(dtoReportList);
							dtoReportCategoryList.add(dtoReportCategory);
						}
					}
					dtoModule.setExportAccess(moduleExportAccess);
					dtoModule.setViewAccess(moduleViewAccess);
					dtoModule.setEmailAccess(moduleEmailAccess);
					dtoModule.setReportCategoryList(dtoReportCategoryList);
					dtoModulesList.add(dtoModule);
				}
			}
		}
		return dtoModulesList;
	}

	*//**
	 * Description: get all report levels data for access role by access role id
	 * @param dtoReportCategory
	 * @param accessRole
	 * @return
	 *//*
	public List<DtoModule> getAllAccessRolesReportsListByAccessRoleId(DtoReportCategory dtoReportCategory,
			AccessRole accessRole) {
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoReport dtoReport = null;
		List<Module> moduleList = null;
		if (dtoReportCategory.getPageNumber() != null && dtoReportCategory.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoReportCategory.getPageNumber(), dtoReportCategory.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true,pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActive(false,true);
		}

		if (moduleList != null && !moduleList.isEmpty()) {
			for (Module module : moduleList) 
			{
				if(module.getIsActive())
				{
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleExportAccess = false;
					boolean moduleEmailAccess = false;
					boolean moduleViewAccess = false;
	
					List<DtoReportCategory> dtoReportCategoryList = new ArrayList<>();
					List<ReportCategory> reportCategoryList = repositoryReportCategory
							.findByIsDeletedAndModuleModuleId(false, module.getModuleId());
					if (reportCategoryList != null && !reportCategoryList.isEmpty()) {
						for (ReportCategory reportCategory : reportCategoryList) {
							boolean reportExportAccess = false;
							boolean reportEmailAccess = false;
							boolean reportViewAccess = false;
							int reportCategoryId = reportCategory.getReportCategoryId();
							List<DtoReport> dtoReportList = new ArrayList<>();
							dtoReportCategory = new DtoReportCategory(reportCategory);
	
							List<Reports> reportList = repositoryReports
									.findByReportCategoryReportCategoryIdAndReportCategoryIsDeletedAndIsDeleted(
											reportCategoryId, false, false);
							if (reportList != null && !reportList.isEmpty()) {
								for (Reports reports : reportList) {
									int reportId = reports.getReportId();
									dtoReport = new DtoReport(reports);
									AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
											.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdAndReportCategoryReportCategoryIdAndIsDeleted(
													module.getModuleId(), accessRole.getAccessRoleId(), reportId,
													reportCategoryId, false);
									if (accessRoleReportRelation != null) {
										dtoReport.setEmailAccess(accessRoleReportRelation.getEmailAccess());
										dtoReport.setViewAccess(accessRoleReportRelation.getViewAccess());
										dtoReport.setExportAccess(accessRoleReportRelation.getExportAccess());
	
										if (accessRoleReportRelation.getEmailAccess()) {
											moduleEmailAccess = true;
											reportEmailAccess = true;
										}
										if (accessRoleReportRelation.getViewAccess()) {
											moduleViewAccess = true;
											reportViewAccess = true;
										}
										if (accessRoleReportRelation.getExportAccess()) {
											moduleExportAccess = true;
											reportExportAccess = true;
										}
									} else {
										dtoReport.setExportAccess(false);
										dtoReport.setViewAccess(false);
										dtoReport.setEmailAccess(false);
									}
									dtoReportList.add(dtoReport);
								}
							}
							else
							{
								AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
										.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportCategoryReportCategoryIdAndReportsReportIdIsNull(
												module.getModuleId(), accessRole.getAccessRoleId(),reportCategoryId);
								if(accessRoleReportRelation!=null)
								{
									if (accessRoleReportRelation.getEmailAccess()) {
										moduleEmailAccess = true;
										reportEmailAccess = true;
									}
									if (accessRoleReportRelation.getViewAccess()) {
										moduleViewAccess = true;
										reportViewAccess = true;
									}
									if (accessRoleReportRelation.getExportAccess()) {
										moduleExportAccess = true;
										reportExportAccess = true;
									}
								}
							}
							dtoReportCategory.setEmailAccess(reportEmailAccess);
							dtoReportCategory.setViewAccess(reportViewAccess);
							dtoReportCategory.setExportAccess(reportExportAccess);
							dtoReportCategory.setReportList(dtoReportList);
							dtoReportCategoryList.add(dtoReportCategory);
						}
					}
					else
					{
						AccessRoleReportRelation accessRoleReportRelation = repositoryAccessRoleReportRelation
								.findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndReportsReportIdIsNullAndReportCategoryReportCategoryIdIsNull(
										module.getModuleId(), accessRole.getAccessRoleId());
						if(accessRoleReportRelation!=null)
						{
							if (accessRoleReportRelation.getEmailAccess()) {
								moduleEmailAccess = true;
								
							}
							if (accessRoleReportRelation.getViewAccess()) {
								moduleViewAccess = true;
								
							}
							if (accessRoleReportRelation.getExportAccess()) {
								moduleExportAccess = true;
							}
						}
					}
					dtoModule.setExportAccess(moduleExportAccess);
					dtoModule.setViewAccess(moduleViewAccess);
					dtoModule.setEmailAccess(moduleEmailAccess);
					dtoModule.setReportCategoryList(dtoReportCategoryList);
					dtoModulesList.add(dtoModule);
			   }
			}
		}
		return dtoModulesList;
	}
*/
}


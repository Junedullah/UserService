/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the company.
 * Name of Project: SmartSoftware
 * Created on:fab 11, 2020
 * Modified on: fab 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.Company;
import com.ss.model.Field;
import com.ss.model.FieldAccess;
import com.ss.model.Module;
import com.ss.model.Screen;
import com.ss.model.dto.DtoFieldAccess;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryFieldAccess;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;




@Service("serviceFieldAccess")
public class ServiceFieldAccess {

	static Logger log = Logger.getLogger(ServiceFieldAccess.class.getName());
	
	@Autowired
	RepositoryFieldAccess repositoryFieldAccess;
	

	@Autowired
	RepositoryModule  repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryFields repositoryFields;


	@Autowired
	RepositoryLanguage repositoryLanguage;


	@Autowired
	RepositoryCompany repositoryCompany;

	
	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	
	
	@Autowired
	ServiceResponse serviceResponse;
	
	private static final String USER_ID = "userid";

	/**
	 * Description: save and update grid data
	 * 
	 * @param dtoGrid
	 * @return
	 */
	@Transactional
	public DtoFieldAccess saveOrUpdate(DtoFieldAccess dtoFieldAccess) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		FieldAccess fieldAccess = null;
		if (dtoFieldAccess.getFieldAccessId() != null && dtoFieldAccess.getFieldAccessId() > 0) {
			fieldAccess = repositoryFieldAccess.findByIdAndIsDeleted(dtoFieldAccess.getFieldAccessId());

			 fieldAccess.setUpdatedBy(loggedInUserId);
             fieldAccess.setUpdatedDate(new Date());
             fieldAccess.setFieldAccessId((dtoFieldAccess.getFieldAccessId()));
         } else {
             fieldAccess = new FieldAccess();
             fieldAccess.setCreatedDate(new Date());
             fieldAccess.setUpdatedDate(new Date());
             Integer rowId = repositoryFieldAccess.getCountOfTotalFieldAccess();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             fieldAccess.setFieldAccessId(increment);
         }
		fieldAccess.setMandatory(dtoFieldAccess.getIsMandatory());
		
		 fieldAccess.setLanguage(repositoryLanguage.findOne(dtoFieldAccess.getLanguageId()));
		
	   fieldAccess.setScreen(repositoryScreen.findOne(dtoFieldAccess.getScreenId()));
	   
	   fieldAccess.setCompany(repositoryCompany.findOne(dtoFieldAccess.getCompanyId()));
	   
	   fieldAccess.setModule(repositoryModule.findOne(dtoFieldAccess.getModuleId()));
	   
	   fieldAccess.setField(repositoryFields.findOne(dtoFieldAccess.getFieldId()));


		fieldAccess = repositoryFieldAccess.saveAndFlush(fieldAccess);

		return dtoFieldAccess;
	}

	public DtoSearch getAllFieldAccess(DtoFieldAccess dtoFieldAccess) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoFieldAccess.getPageNumber());
		dtoSearch.setPageSize(dtoFieldAccess.getPageSize());
		dtoSearch.setTotalCount(repositoryFieldAccess.getCountOfTotalFieldAccess());
		List<FieldAccess> fieldAccessList = null;
		if (dtoFieldAccess.getPageNumber() != null && dtoFieldAccess.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoFieldAccess.getPageNumber(), dtoFieldAccess.getPageSize(), Direction.DESC, "createdDate");
			fieldAccessList = repositoryFieldAccess.findByIsDeleted(false, pageable);
		} else {
			fieldAccessList = repositoryFieldAccess.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoFieldAccess> dtoFieldAccesss=new ArrayList<>();
		if(fieldAccessList!=null && !fieldAccessList.isEmpty())
		{
			for (FieldAccess fieldAccess : fieldAccessList) 
			{
			 dtoFieldAccess=new DtoFieldAccess();
			 
			 dtoFieldAccess.setFieldAccessId(fieldAccess.getFieldAccessId());
			 dtoFieldAccess.setIsMandatory(fieldAccess.getMandatory());
			 dtoFieldAccess.setLanguageId(fieldAccess.getLanguage().getLanguageId());
		     dtoFieldAccess.setScreenId(fieldAccess.getScreen().getScreenId());
		     dtoFieldAccess.setFieldId(fieldAccess.getField().getFieldId());
		     dtoFieldAccess.setLanguageId(fieldAccess.getLanguage().getLanguageId());
		     dtoFieldAccess.setModuleId(fieldAccess.getModule().getModuleId());
			
		     dtoFieldAccesss.add(dtoFieldAccess);
			}
			dtoSearch.setRecords(dtoFieldAccesss);
		}
		return dtoSearch;
	}
	
	public List<Integer> delete(List<Integer> ids) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		try {
			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
				Integer fieldAccessId = idIterator.next();
				List<FieldAccess> fieldAccessList = repositoryFieldAccess.findBygridIdAndIsDeleted(ids);
				if(fieldAccessList.size() == 0){
					return ids;
				}else{
					repositoryFieldAccess.deleteSingleFieldAccess(true, loggedInUserId, fieldAccessId);
					idIterator.remove();
				}
			}
		} catch (Exception e) {
			log.error("Error while deleting FieldAccess", e);
		}
		return ids;
	}


public DtoFieldAccess getById(int id) {
	DtoFieldAccess dtoFieldAccess  = new DtoFieldAccess();
	try {
		if (id > 0) {
			FieldAccess fieldAccess = repositoryFieldAccess.findByAndIsDeleted(id);

			if (fieldAccess != null) {
				dtoFieldAccess = new DtoFieldAccess();
				
				 dtoFieldAccess.setFieldAccessId(fieldAccess.getFieldAccessId());
				 dtoFieldAccess.setIsMandatory(fieldAccess.getMandatory());
				 dtoFieldAccess.setLanguageId(fieldAccess.getLanguage().getLanguageId());
			     dtoFieldAccess.setScreenId(fieldAccess.getScreen().getScreenId());
			     dtoFieldAccess.setFieldId(fieldAccess.getField().getFieldId());
			     dtoFieldAccess.setLanguageId(fieldAccess.getLanguage().getLanguageId());
			     dtoFieldAccess.setModuleId(fieldAccess.getModule().getModuleId());
				
				

			} 
		} else {
			dtoFieldAccess.setMessageType("INVALID_ID");

		}

	} catch (Exception e) {
		log.error(e);
	}
	return dtoFieldAccess;
}

public DtoSearch getAllFieldsByCompanyId(DtoFieldAccess dtoFieldAccess) {
	log.info("getAllFields Method");
	List<DtoFieldAccess> listFieldAccesses = new ArrayList<>();
	DtoSearch dtoSearch = new DtoSearch();
	dtoSearch.setPageNumber(dtoFieldAccess.getPageNumber());
	dtoSearch.setPageSize(dtoFieldAccess.getPageSize());
	List<FieldAccess> fieldAccesses = repositoryFieldAccess.findbyCompanyId(dtoFieldAccess.getCompanyId());
	List<FieldAccess> screenFieldList = repositoryFieldAccess.findScreenIdbyCompanyId(dtoFieldAccess.getScreenId(), dtoFieldAccess.getCompanyId());

	for (FieldAccess screenLists : screenFieldList) {
		DtoFieldAccess screenFieldAccess = new DtoFieldAccess();
		screenFieldAccess.setFieldAccessId(screenLists.getFieldAccessId());
		screenFieldAccess.setScreenId(screenLists.getScreen().getScreenId());
		screenFieldAccess.setScreenName(screenLists.getScreen().getScreenName());
		listFieldAccesses.add(screenFieldAccess);
	}

	for (FieldAccess fieldAccess : fieldAccesses) {
		DtoFieldAccess access = new DtoFieldAccess();
		access.setFieldAccessId(fieldAccess.getFieldAccessId());
		access.setFieldId(fieldAccess.getField().getFieldId());
		access.setFieldName(fieldAccess.getField().getFieldName());
		access.setCompanyId(fieldAccess.getCompany().getCompanyId());
		access.setIsMandatory(fieldAccess.getMandatory());
		access.setLanguageId(fieldAccess.getLanguage().getLanguageId());
		access.setModuleId(fieldAccess.getModule().getModuleId());
		access.setModuleName(fieldAccess.getModule().getName());
		access.setScreenId(fieldAccess.getScreen().getScreenId());
		access.setScreenName(fieldAccess.getScreen().getScreenName());
		listFieldAccesses.add(access);
	}

	Company company = repositoryCompany.findOne(dtoFieldAccess.getCompanyId());
	List<Integer> fieldIds = new ArrayList<>();

	for (FieldAccess fieldAccess : fieldAccesses) {
		if (fieldAccess.getField() != null) {
			fieldIds.add(fieldAccess.getField().getFieldId());
		}
	}

	List<Field> Listfield = repositoryFields.findByRemainFieldId(fieldIds);

	for (Field field : Listfield) {
		DtoFieldAccess access = new DtoFieldAccess();
		access.setFieldId(field.getFieldId());
		access.setFieldName(field.getFieldName());
		access.setCompanyId(company.getCompanyId());
		access.setIsMandatory(field.getIsMandatory());
		access.setLanguageId(field.getLanguage().getLanguageId());
		access.setModuleId(field.getScreen().getModule().getModuleId());
		access.setModuleName(field.getScreen().getModule().getName());
		access.setScreenId(field.getScreen().getScreenId());
		access.setScreenName(field.getScreen().getScreenName());
		listFieldAccesses.add(access);
	}
	dtoSearch.setRecords(listFieldAccesses);
	log.debug("All Field List Size is:" + dtoSearch.getTotalCount());
	return dtoSearch;
}

public List<DtoModule> getAllScreenDetailList(DtoScreenDetail dtoScreenDetail) {
	String langId = httpServletRequest.getHeader("langid");
	List<DtoModule> dtoModulesList = new ArrayList<>();
	DtoFieldDetail dtoFieldDetail;
	DtoFieldAccess dtoFieldAccess = null;
	List<Module> moduleList = null;

	if (dtoScreenDetail.getPageNumber() != null && dtoScreenDetail.getPageSize() != null) {
		Pageable pageable = new PageRequest(dtoScreenDetail.getPageNumber(), dtoScreenDetail.getPageSize());
		moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));
	} else {
		moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));
	}

	if (moduleList != null && moduleList.size() > 0) {
		for (Module module : moduleList) {
			if (module.getIsActive()) {
				DtoModule dtoModule = new DtoModule(module);
				boolean moduleIsMandatory = false;

				List<DtoScreenDetail> dtoScreenDetailsList = new ArrayList<>();
				List<Screen> screenList = repositoryScreen.findByIsDeletedAndModuleModuleId(false,
						module.getModuleId());
				if (screenList != null && screenList.size() > 0) {
					for (Screen screen : screenList) {
						boolean screenIsMandatory = false;

						List<DtoFieldDetail> dtoFieldDetailsList = new ArrayList<>();

						dtoScreenDetail = new DtoScreenDetail(screen, langId);
						if (screen.getCompany() != null) {

							Company company = repositoryCompany.findOne(screen.getCompany().getCompanyId());
							System.out.println("Company: " + company);
							if (company != null) {
								List<Field> fieldList = repositoryFields.findByIsDeletedAndScreenScreenIdAndCompanyId(false, screen.getScreenId(), company.getCompanyId());

								if (fieldList != null && fieldList.size() > 0) {
									for (Field field : fieldList) {
										dtoFieldDetail = new DtoFieldDetail(field, langId);
										dtoFieldDetail.setIsMandatory(field.getIsMandatory());
										dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
										dtoFieldDetail.setCompanyId(company.getCompanyId());
										dtoFieldDetailsList.add(dtoFieldDetail);
									}
								}
							}
						}

						dtoScreenDetail.setIsMandatory(screenIsMandatory);
						dtoScreenDetail.setLanguageid(screen.getLanguage().getLanguageId());
						// dtoScreenDetail.setFieldAccessList(dtoFieldDetails);
						dtoScreenDetail.setFieldList(dtoFieldDetailsList);
						dtoScreenDetailsList.add(dtoScreenDetail);
					}
				}
				dtoModule.setIsMandatory(moduleIsMandatory);
				dtoModule.setScreensList(dtoScreenDetailsList);
				dtoModulesList.add(dtoModule);
			}
		}
	}
	return dtoModulesList;
}

public List<DtoModule> getAllScreenDetailListByComapnyId(FieldAccess fieldAccess, DtoScreenDetail dtoScreenDetail) {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoModule> dtoModulesList = new ArrayList<>();
		DtoFieldAccess dtoFieldAccess = null;
		List<Module> moduleList = null;
		List<FieldAccess> fieldAccessList = null;
		if (dtoScreenDetail.getPageNumber() != null && dtoScreenDetail.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoScreenDetail.getPageNumber(), dtoScreenDetail.getPageSize());
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId), pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));
		}

		if (moduleList != null && moduleList.size() > 0) {
			for (Module module : moduleList) {
				if (module.getIsActive()) {
					DtoModule dtoModule = new DtoModule(module);
					boolean moduleFieldAccess = false;

					Company company = repositoryCompany.findByCompanyIdAndIsDeleted(dtoFieldAccess.getCompanyId(), false);
					fieldAccessList = repositoryFieldAccess.findFieldIdByCompanyId(company.getCompanyId());
					List<DtoScreenDetail> dtoScreenDetailsList = new ArrayList<>();
					List<Screen> screenList = repositoryScreen.findByIsDeletedAndModuleModuleId(false, module.getModuleId());
					if (screenList != null && screenList.size() > 0) {
						for (Screen screen : screenList) {
							boolean screenFieldAccess = false;
							Integer languageId = null;
							List<DtoFieldAccess> dtoFieldAccesses = new ArrayList<DtoFieldAccess>();
							dtoScreenDetail = new DtoScreenDetail(screen, langId);

							fieldAccessList = repositoryFieldAccess.findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(screen.getScreenId(), false, false);
							if (fieldAccessList != null && fieldAccessList.size() > 0) {
								for (FieldAccess field : fieldAccessList) {
									dtoFieldAccess = new DtoFieldAccess(field, langId);

									FieldAccess fieldAccessscreenRelation = repositoryFieldAccess
											.findByModuleModuleIdAndScreenScreenIdAndCompanyCompanyIdAndFieldFieldIdAndLanguageLanguageIdAndIsDeleted(
													module.getModuleId(), screen.getScreenId(), company.getCompanyId(),
													fieldAccess.getField().getFieldId(), Integer.parseInt(langId),
													false);

									if (fieldAccessscreenRelation != null) {
										dtoFieldAccess.setIsMandatory(fieldAccessscreenRelation.getMandatory());
										dtoFieldAccess.setLanguageId(fieldAccessscreenRelation.getLanguage().getLanguageId());
										if (fieldAccessscreenRelation.getMandatory()) {
											screenFieldAccess = true;
											moduleFieldAccess = true;
										}
									} else {
										dtoFieldAccess.setIsMandatory(false);
										dtoFieldAccess.setLanguageId(languageId);
									}
									dtoFieldAccesses.add(dtoFieldAccess);
								}
							} else {
								Field field = repositoryFields.findByFieldIdAndIsDeleted(fieldAccess.getField().getFieldId(), false);
								if (field != null) {
									if (field.getIsMandatory()) {
										screenFieldAccess = true;
										moduleFieldAccess = true;
									}
								}
							}
							dtoScreenDetail.setIsMandatory(screenFieldAccess);
							dtoScreenDetail.setLanguageid(Integer.parseInt(langId));
							dtoScreenDetailsList.add(dtoScreenDetail);
						}
					} else {
						FieldAccess field = repositoryFieldAccess.findByModuleModuleIdAndCompanyCompanyIdAndFieldFieldIdAndLanguageLanguageIdAndIsDeleted(
										module.getModuleId(), company.getCompanyId(), fieldAccess.getField().getFieldId(), Integer.parseInt(langId), false);
						if (field != null) {
							if (field.getMandatory()) {

								moduleFieldAccess = true;
							}
						}
					}
					dtoScreenDetail.setIsMandatory(moduleFieldAccess);
					dtoScreenDetail.setLanguageid(Integer.parseInt(langId));
					dtoScreenDetailsList.add(dtoScreenDetail);
				}
			}
		}
		return dtoModulesList;
	}


	
}

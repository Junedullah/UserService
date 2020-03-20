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
import com.ss.model.Module;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;



@Service("serviceModule")
public class ServiceModule {

	static Logger log = Logger.getLogger(ServiceModule.class.getName());

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	@Autowired
	RepositoryLanguage repositoryLanguage;

	
	@Autowired
	ServiceResponse serviceResponse;
	
	private static final String USER_ID = "userid";

	/**
	 * Description: save and update company data
	 * 
	 * @param dtoCompany
	 * @return
	 */
	@Transactional
	public DtoModule saveOrUpdateModule(DtoModule dtoModule) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Module module = null;
		if (dtoModule.getModuleId() != null && dtoModule.getModuleId() > 0) {
			module = repositoryModule.findByModuleIdAndIsDeleted(dtoModule.getModuleId(), false);

			 module.setUpdatedBy(loggedInUserId);
             module.setUpdatedDate(new Date());
             module.setModuleId(dtoModule.getModuleId());
         } else {
             module = new Module();
             module.setCreatedDate(new Date());
             module.setUpdatedDate(new Date());
             Integer rowId = repositoryModule.getCountOfTotalModule();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             module.setModuleId(increment);
         }
		module.setDescription(dtoModule.getDescription());
		module.setModuleCode(dtoModule.getModuleCode());
		module.setName(dtoModule.getName());
		module.setHelpMessage(dtoModule.getHelpMessage());
		module.setIsActive(dtoModule.getIsActive());
		module.setLanguage(repositoryLanguage.findOne(dtoModule.getLanguageId()));
	
		module = repositoryModule.saveAndFlush(module);

		return dtoModule;
	}

	public DtoSearch getAllModule(DtoModule dtoModule) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoModule.getPageNumber());
		dtoSearch.setPageSize(dtoModule.getPageSize());
		dtoSearch.setTotalCount(repositoryModule.getCountOfTotalModule());
		List<Module> moduleList = null;
		if (dtoModule.getPageNumber() != null && dtoModule.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoModule.getPageNumber(), dtoModule.getPageSize(), Direction.DESC, "createdDate");
			moduleList = repositoryModule.findByIsDeleted(false, pageable);
		} else {
			moduleList = repositoryModule.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoModule> dtoModules=new ArrayList<>();
		if(moduleList!=null && !moduleList.isEmpty())
		{
			for (Module module : moduleList) 
			{
			 dtoModule=new DtoModule();
			 dtoModule.setModuleId(module.getModuleId());
			 dtoModule.setName(module.getName());
			 dtoModule.setModuleCode(module.getModuleCode());
			 dtoModule.setDescription(module.getDescription());
			 dtoModule.setHelpMessage(module.getHelpMessage());
			 dtoModule.setIsActive(module.getIsActive());
				dtoModule.setLanguageId(module.getLanguage().getLanguageId()); 
				 
				dtoModules.add(dtoModule);
			}
			dtoSearch.setRecords(dtoModules);
		}
		return dtoSearch;
	}
	
	
}

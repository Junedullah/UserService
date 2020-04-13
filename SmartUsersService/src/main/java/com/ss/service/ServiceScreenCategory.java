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

import com.ss.model.Field;
import com.ss.model.Language;
import com.ss.model.ScreenCategory;
import com.ss.model.dto.DtoFieldAccessDeatils;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoScreenCategory;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryGrid;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryScreenCategory;




@Service("serviceScreenCategory")
public class ServiceScreenCategory {

	static Logger log = Logger.getLogger(ServiceScreenCategory.class.getName());

	

	@Autowired
	RepositoryScreenCategory repositoryScreenCategory;
	
	
	@Autowired
	RepositoryModule  repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;



	@Autowired
	RepositoryLanguage repositoryLanguage;

	
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
	public DtoScreenCategory saveOrUpdate(DtoScreenCategory dtoScreenCategory) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		ScreenCategory screenCategory = null;
		if (dtoScreenCategory.getScreenCategoryId() != null && dtoScreenCategory.getScreenCategoryId() > 0) {
			screenCategory  = repositoryScreenCategory.findByIdAndIsDeleted(dtoScreenCategory.getScreenCategoryId());

			 screenCategory.setUpdatedBy(loggedInUserId);
             screenCategory.setUpdatedDate(new Date());
             screenCategory.setScreenCategoryId((dtoScreenCategory.getScreenCategoryId()));
         } else {
             screenCategory = new ScreenCategory();
             screenCategory.setCreatedDate(new Date());
             screenCategory.setUpdatedDate(new Date());
             Integer rowId = repositoryScreenCategory.getCountOfTotalScreenCategory();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             screenCategory.setScreenCategoryId(increment);
         }
		screenCategory.setScreenCategoryName(dtoScreenCategory.getScreenCategoryName());
		screenCategory.setScreenCategoryCode(dtoScreenCategory.getScreenCategoryCode());
		screenCategory.setHelpMessage(dtoScreenCategory.getHelpMessage());
		
		
		
		 screenCategory.setLanguage(repositoryLanguage.findOne(dtoScreenCategory.getLanguageId()));
		
	  screenCategory.setModule(repositoryModule.findByAndIsDeleted(dtoScreenCategory.getModuleId()));

		screenCategory = repositoryScreenCategory.saveAndFlush(screenCategory);

		return dtoScreenCategory;
	}

	public DtoSearch getAllScreenCategory(DtoScreenCategory dtoScreenCategory) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoScreenCategory.getPageNumber());
		dtoSearch.setPageSize(dtoScreenCategory.getPageSize());
		dtoSearch.setTotalCount(repositoryScreenCategory.getCountOfTotalScreenCategory());
		List<ScreenCategory> screenCategoryList = null;
		if (dtoScreenCategory.getPageNumber() != null && dtoScreenCategory.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoScreenCategory.getPageNumber(), dtoScreenCategory.getPageSize(), Direction.DESC, "createdDate");
			screenCategoryList = repositoryScreenCategory.findByIsDeleted(false, pageable);
		} else {
			screenCategoryList = repositoryScreenCategory.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoScreenCategory> dtoScreenCategories=new ArrayList<>();
		if(screenCategoryList!=null && !screenCategoryList.isEmpty())
		{
			for (ScreenCategory screenCategory : screenCategoryList) 
			{
			 dtoScreenCategory=new DtoScreenCategory();
			 
			 dtoScreenCategory.setScreenCategoryId(screenCategory.getScreenCategoryId());
			 dtoScreenCategory.setHelpMessage(screenCategory.getHelpMessage());
			 dtoScreenCategory.setScreenCategoryName(screenCategory.getScreenCategoryName());
			 dtoScreenCategory.setScreenCategoryCode(screenCategory.getScreenCategoryCode());
			dtoScreenCategory.setLanguageId(screenCategory.getLanguage().getLanguageId());
			dtoScreenCategory.setModuleId(screenCategory.getModule().getModuleId());
		    
				dtoScreenCategories.add(dtoScreenCategory);
			}
			dtoSearch.setRecords(dtoScreenCategories);
		}
		return dtoSearch;
	}
	
	public List<Integer> delete(List<Integer> ids) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		try {
			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
				Integer screenCategoryId = idIterator.next();
				List<ScreenCategory> screenCategoryList = repositoryScreenCategory.findBygridIdAndIsDeleted(ids);
				if(screenCategoryList.size() == 0){
					return ids;
				}else{
					repositoryScreenCategory.deleteSingleScreenCategory(true, loggedInUserId, screenCategoryId);
					idIterator.remove();
				}
			}
		} catch (Exception e) {
			log.error("Error while deleting ScreenCategory", e);
		}
		return ids;
	}


public DtoScreenCategory getById(int id) {
	DtoScreenCategory dtoScreenCategory  = new DtoScreenCategory();
	try {
		if (id > 0) {
			ScreenCategory screenCategory = repositoryScreenCategory.findByAndIsDeleted(id);

			if (screenCategory != null) {
				dtoScreenCategory = new DtoScreenCategory();
				
				 dtoScreenCategory.setScreenCategoryId(screenCategory.getScreenCategoryId());
				 dtoScreenCategory.setHelpMessage(screenCategory.getHelpMessage());
				 dtoScreenCategory.setScreenCategoryName(screenCategory.getScreenCategoryName());
				 dtoScreenCategory.setScreenCategoryCode(screenCategory.getScreenCategoryCode());
				dtoScreenCategory.setLanguageId(screenCategory.getLanguage().getLanguageId());
				dtoScreenCategory.setModuleId(screenCategory.getModule().getModuleId());
			    
				

			} 
		} else {
			dtoScreenCategory.setMessageType("INVALID_ID");

		}

	} catch (Exception e) {
		log.error(e);
	}
	return dtoScreenCategory;
}


 
 
	
}

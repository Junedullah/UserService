package com.ss.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.Screen;
import com.ss.model.ScreenCategory;
import com.ss.model.ScreenMenu;
import com.ss.model.dto.DtoScreenCategory;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoScreenMenu;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryScreenCategory;
import com.ss.repository.RepositoryScreenMenu;

@Service("ServiceScreen")
public class ServiceScreen {
	
	static Logger log = Logger.getLogger(ServiceModule.class.getName());
	
	@Autowired
	RepositoryModule repositoryModule;
	
	@Autowired
	RepositoryScreenCategory repositoryScreenCategory;
	
	@Autowired
	RepositoryScreenMenu repositoryScreenMenu;

	@Autowired
	RepositoryScreen repositoryScreen;

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
	 *Description: save and update ScreenCategory data
	 * 
	 * @param dtoCompany
	 * @return
	 */
	
	@Transactional
	public DtoScreenDetail saveOrUpdateScreen(DtoScreenDetail dtoScreenDetail) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Screen screen = null;
		if (dtoScreenDetail.getScreenId() != null && dtoScreenDetail.getScreenId() > 0) {
			screen = repositoryScreen.findByScreenIdAndIsDeleted(dtoScreenDetail.getScreenId(), false);

			screen.setUpdatedBy(loggedInUserId);
			screen.setUpdatedDate(new Date());
			screen.setScreenId(dtoScreenDetail.getScreenId());
         } else {
        	 screen = new Screen();
        	 screen.setCreatedDate(new Date());
        	 screen.setUpdatedDate(new Date());
             Integer rowId = repositoryScreenCategory.getCountOfTotal();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             screen.setScreenId(increment);
         }
		screen.setDescription(dtoScreenDetail.getDescription());
		screen.setScreenCode(dtoScreenDetail.getScreenCode());
		screen.setScreenName(dtoScreenDetail.getScreenName());
		screen.setSideMenu(dtoScreenDetail.getSideMenuCode());
		screen.setSideMenuURL(dtoScreenDetail.getSideMenuUrl());
		screen.setHelpMessage(dtoScreenDetail.getHelpMessage());		
		screen.setLanguage(repositoryLanguage.findOne(dtoScreenDetail.getLanguageId()));
		screen.setModule(repositoryModule.findOne(dtoScreenDetail.getModuleId()));
	
		screen = repositoryScreen.saveAndFlush(screen);

		return dtoScreenDetail;
	}

	
	/**
	 *Description: save and update ScreenCategory data
	 * 
	 * @param dtoCompany
	 * @return
	 */
	
	@Transactional
	public DtoScreenCategory saveOrUpdateScreenCategory(DtoScreenCategory dtoScreenCategory) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		ScreenCategory screenCategory = null;
		if (dtoScreenCategory.getScreenCategoryId() != null && dtoScreenCategory.getScreenCategoryId() > 0) {
			screenCategory = repositoryScreenCategory.findByScreenCategoryIdAndIsDeleted(dtoScreenCategory.getScreenCategoryId(), false);

			screenCategory.setUpdatedBy(loggedInUserId);
			screenCategory.setUpdatedDate(new Date());
			screenCategory.setScreenCategoryId(dtoScreenCategory.getScreenCategoryId());
         } else {
        	 screenCategory = new ScreenCategory();
        	 screenCategory.setCreatedDate(new Date());
        	 screenCategory.setUpdatedDate(new Date());
             Integer rowId = repositoryScreenCategory.getCountOfTotal();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             screenCategory.setScreenCategoryId(increment);
         }
		screenCategory.setScreenCategoryCode(dtoScreenCategory.getScreenCategoryCode());
		screenCategory.setScreenCategoryName(dtoScreenCategory.getScreenCategoryName());		
		screenCategory.setHelpMessage(dtoScreenCategory.getHelpMessage());		
		screenCategory.setLanguage(repositoryLanguage.findOne(dtoScreenCategory.getLanguageId()));
		screenCategory.setModule(repositoryModule.findOne(dtoScreenCategory.getModuleId()));
	
		screenCategory = repositoryScreenCategory.saveAndFlush(screenCategory);

		return dtoScreenCategory;
	}

	/**
	 *Description: save and update ScreenMenu data
	 * 
	 * @param dtoScreenMenu
	 * @return
	 */
	@Transactional
	public DtoScreenMenu saveOrUpdateScreenMenu(DtoScreenMenu dtoScreenMenu) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		ScreenMenu screenMenu = null;
		if (dtoScreenMenu.getScreenMenuId() != null && dtoScreenMenu.getScreenMenuId() > 0) {
			screenMenu = repositoryScreenMenu.findByScreenMenuIdAndIsDeleted(dtoScreenMenu.getScreenMenuId(), false);

			screenMenu.setUpdatedBy(loggedInUserId);
			screenMenu.setUpdatedDate(new Date());
			screenMenu.setScreenMenuId(dtoScreenMenu.getScreenMenuId());
         } else {
        	 screenMenu = new ScreenMenu();
        	 screenMenu.setCreatedDate(new Date());
        	 screenMenu.setUpdatedDate(new Date());
             Integer rowId = repositoryScreenMenu.getCountOfTotal();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             screenMenu.setScreenMenuId(increment);
         }
		screenMenu.setSideMenuCode(dtoScreenMenu.getSideMenuCode());
		screenMenu.setDescription(dtoScreenMenu.getDescription());		
		screenMenu.setScreenName(dtoScreenMenu.getScreenName());		
		screenMenu.setHelpMessage(dtoScreenMenu.getHelpMessage());
		screenMenu.setSideMenuURL(dtoScreenMenu.getSideMenuUrl());
		screenMenu.setScreenCategory(repositoryScreenCategory.findOne(dtoScreenMenu.getScreenCategoryId()));
		screenMenu.setLanguage(repositoryLanguage.findOne(dtoScreenMenu.getLanguageId()));
		screenMenu.setModule(repositoryModule.findOne(dtoScreenMenu.getModuleId()));
	
		screenMenu = repositoryScreenMenu.saveAndFlush(screenMenu);

		return dtoScreenMenu;
	}

	public boolean deleteScreenCategory(DtoScreenCategory dtoScreenCategory) {
		
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
			ScreenCategory screenCategory = repositoryScreenCategory.findOne(dtoScreenCategory.getScreenCategoryId());
			if(screenCategory!=null)
			{
				screenCategory.setIsDeleted(true);				
				screenCategory.setUpdatedBy(loggedInUserId);
				repositoryScreenCategory.saveAndFlush(screenCategory);
				return true;
			}
			else{
				return false;
			}
		
	}

	public boolean deleteScreenMenu(DtoScreenMenu dtoScreenMenu) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		ScreenMenu screenMenu = repositoryScreenMenu.findOne(dtoScreenMenu.getScreenMenuId());
		if(screenMenu!=null)
		{
			screenMenu.setIsDeleted(true);				
			screenMenu.setUpdatedBy(loggedInUserId);
			repositoryScreenMenu.saveAndFlush(screenMenu);
			return true;
		}
		else{
			return false;
		}
	}

	
	
	
}

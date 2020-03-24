package com.ss.service;
/**SmartSoftware User - Service */
/**
 * Description: ControllerUser
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hsqldb.types.DTIType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.authetication.SessionManager;
import com.ss.model.Module;
import com.ss.model.Screen;
import com.ss.model.ScreenMenu;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoScreenDetail;
import com.ss.model.dto.DtoScreenMenu;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryScreenMenu;

@Service("ServiceScreen")
public class ServiceScreen {

	static Logger log = Logger.getLogger(ServiceScreen.class.getName());

	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryLanguage repositoryLanguage;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired	
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryScreenMenu repositoryScreenMenu;
	
	@Autowired
	RepositoryModule repositoryModule;

	private static final String USER_ID = "userid";

	/**
	 * Description: save and update Screen data
	 * 
	 * @param dtoScreenDetail
	 * @return
	 */
	@Transactional
	public  DtoScreenDetail saveOrUpdateScreen(DtoScreenDetail dtoScreenDetail) {		

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
			Integer rowId = repositoryScreen.getCountOfTotalScreen();
			Integer increment = 0;
			if (rowId != 0) {
				increment = rowId + 1;
			} else {
				increment = 1;
			}
			screen.setScreenId(increment);
		}

		screen.setDescription(dtoScreenDetail.getDescription());
		screen.setHelpMessage(dtoScreenDetail.getHelpMessage());
		screen.setScreenCode(dtoScreenDetail.getScreenCode());
		screen.setScreenName(dtoScreenDetail.getScreenName());
		screen.setLanguage(repositoryLanguage.findOne(dtoScreenDetail.getLanguageid()));
		screen.setModule(repositoryModule.findOne(dtoScreenDetail.getModuleId()));

		screen = repositoryScreen.saveAndFlush(screen);

		return dtoScreenDetail;
	}

	/**
	 * Description: save and update Screen Menu
	 * 
	 * @param dtoScreenMenu
	 * @return
	 */
	@Transactional
	public  DtoScreenMenu saveOrUpdateScreenMenu(DtoScreenMenu dtoScreenMenu) {

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
			Integer rowId = repositoryScreenMenu.getCountOfTotalScreen();
			Integer increment = 0;
			if (rowId != 0) {
				increment = rowId + 1;
			} else {
				increment = 1;
			}
			screenMenu.setScreenMenuId(increment);
		}

		screenMenu.setDescription(dtoScreenMenu.getDescription());
		screenMenu.setHelpMessage(dtoScreenMenu.getHelpMessage());
		screenMenu.setSideMenuCode(dtoScreenMenu.getSideMenuCode());
		screenMenu.setSideMenuURL(dtoScreenMenu.getSideMenuURL());
		screenMenu.setSideMenuCode(dtoScreenMenu.getSideMenuCode());
		screenMenu.setModule(repositoryModule.findOne(dtoScreenMenu.getModuleId()));		
		screenMenu.setLanguage(repositoryLanguage.findOne(dtoScreenMenu.getLanguageid()));
		screenMenu = repositoryScreenMenu.saveAndFlush(screenMenu);

		return dtoScreenMenu;
	}
	
	
	/**
	 * Description: Get All Screen
	 * 
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getAllScreen(DtoSearch dtoSearch) {
		dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch.setPageSize(dtoSearch.getPageSize());
		dtoSearch.setTotalCount(repositoryScreen.getCountOfTotalScreen());
		List<Screen> screenList = null;
		DtoScreenDetail dtoScreenDetail = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate");
			screenList = repositoryScreen.findByIsDeleted(false, pageable);
		} else {
			screenList = repositoryScreen.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoScreenDetail> dtoScreenDetails=new ArrayList<>();
		if(screenList!=null && !screenList.isEmpty())
		{
			for (Screen screen : screenList) 
			{				
				dtoScreenDetail=new DtoScreenDetail();
				dtoScreenDetail.setScreenId(screen.getScreenId());
				dtoScreenDetail.setScreenName(screen.getScreenName());				
				dtoScreenDetail.setDescription(screen.getDescription());
				dtoScreenDetail.setHelpMessage(screen.getHelpMessage());				
				dtoScreenDetail.setLanguageid(screen.getLanguage().getLanguageId()); 
				dtoScreenDetail.setModuleCode(screen.getModule().getModuleCode());
				 
				dtoScreenDetails.add(dtoScreenDetail);
			}
			dtoSearch.setRecords(dtoScreenDetails);
		}
		return dtoSearch;
	}

	
	/**
	 * Description: Get All Screen Menu
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getAllScreenMenu(DtoSearch dtoSearch) {
		dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch.setPageSize(dtoSearch.getPageSize());
		dtoSearch.setTotalCount(repositoryScreen.getCountOfTotalScreen());
		List<ScreenMenu> screenMenuList = null;
		DtoScreenMenu dtoScreenMenu = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate");
			screenMenuList = repositoryScreenMenu.findByIsDeleted(false, pageable);
		} else {
			screenMenuList = repositoryScreenMenu.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoScreenMenu> dtoScreenMenus=new ArrayList<>();
		if(screenMenuList!=null && !screenMenuList.isEmpty())
		{
			for (ScreenMenu screenMenu : screenMenuList) 
			{				
				dtoScreenMenu=new DtoScreenMenu();
				dtoScreenMenu.setScreenMenuId(screenMenu.getScreenMenuId());
				dtoScreenMenu.setScreenName(screenMenu.getScreenName());				
				dtoScreenMenu.setDescription(screenMenu.getDescription());
				dtoScreenMenu.setHelpMessage(screenMenu.getHelpMessage());				
				dtoScreenMenu.setLanguageid(screenMenu.getLanguage().getLanguageId()); 
				dtoScreenMenu.setModuleCode(screenMenu.getModule().getModuleCode());
				dtoScreenMenu.setScreenCode(screenMenu.getScreenCode());
				dtoScreenMenu.setModuleName(screenMenu.getModule().getName());				 
				dtoScreenMenus.add(dtoScreenMenu);
			}
			dtoSearch.setRecords(dtoScreenMenus);
		}
		return dtoSearch;
	}

	
	/**
	 * Description: deleteScreen
	 * @param ids
	 * @return
	 */
	public List<Integer> deleteScreen(List<Integer> ids) {
		
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
			try {
				for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
					Integer screenId = idIterator.next();
					List<Screen> screensList = repositoryScreen.findByScreenIdAndIsDeleted(ids);
					if(screensList.size() == 0){
						return ids;
					}else{
						repositoryScreen.deleteSingleScreen(true, loggedInUserId, screenId);
						idIterator.remove();
					}
				}
			} catch (Exception e) {
				log.error("Error while deleting Account", e);
			}
			return ids;
	}
	
	/**
	 * Description: deleteScreen
	 * @param ids
	 * @return
	 */
	public List<Integer> deleteScreenMenu(List<Integer> ids){
		
			int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
			try {
				for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
					Integer screenMenuId = idIterator.next();
					List<ScreenMenu> screenMenusList = repositoryScreenMenu.findByScreenMenuIdAndIsDeleted(ids);
					if(screenMenusList.size() == 0){
						return ids;
					}else{
						repositoryScreen.deleteSingleScreen(true, loggedInUserId, screenMenuId);
						idIterator.remove();
					}
				}
			} catch (Exception e) {
				log.error("Error while deleting Account", e);
			}
			return ids;
	}

	/**
	 * Description: Screen get By Id
	 * @param screenId
	 * @return
	 */
	public DtoScreenDetail getById(Integer screenId) {
		DtoScreenDetail dtoScreenDetail= new DtoScreenDetail();
		try {
			if (screenId > 0) {
				Screen screen = repositoryScreen.findByAndIsDeleted(screenId);

				if (screen != null) {
					dtoScreenDetail=new DtoScreenDetail();
					dtoScreenDetail.setScreenId(screen.getScreenId());
					dtoScreenDetail.setScreenName(screen.getScreenName());				
					dtoScreenDetail.setDescription(screen.getDescription());
					dtoScreenDetail.setHelpMessage(screen.getHelpMessage());				
					dtoScreenDetail.setLanguageid(screen.getLanguage().getLanguageId()); 
					dtoScreenDetail.setModuleCode(screen.getModule().getModuleCode());				

				} 
			} else {
				dtoScreenDetail.setMessageType("INVALID_ID");
			}

		} catch (Exception e) {
			log.error(e);
		}
		return dtoScreenDetail;
	}
	
	
	/**
	 * Description: ScreenMenu GET By Id
	 * @param screenMenuId
	 * @return
	 */
	public DtoScreenMenu screenMenuGetById(Integer screenMenuId) {
		DtoScreenMenu dtoScreenMenu= new DtoScreenMenu();
		try {
			if (screenMenuId > 0) {
				ScreenMenu screenMenu = repositoryScreenMenu.findByAndIsDeleted(screenMenuId);

				if (screenMenu != null) {
					dtoScreenMenu=new DtoScreenMenu();
					dtoScreenMenu.setScreenMenuId(screenMenu.getScreenMenuId());
					dtoScreenMenu.setScreenName(screenMenu.getScreenName());				
					dtoScreenMenu.setDescription(screenMenu.getDescription());
					dtoScreenMenu.setHelpMessage(screenMenu.getHelpMessage());				
					dtoScreenMenu.setLanguageid(screenMenu.getLanguage().getLanguageId()); 
					dtoScreenMenu.setModuleCode(screenMenu.getModule().getModuleCode());
					dtoScreenMenu.setScreenCode(screenMenu.getScreenCode());
					dtoScreenMenu.setModuleName(screenMenu.getModule().getName());	

				} 
			} else {
				dtoScreenMenu.setMessageType("INVALID_ID");
			}

		} catch (Exception e) {
			log.error(e);
		}
		return dtoScreenMenu;
	}
}

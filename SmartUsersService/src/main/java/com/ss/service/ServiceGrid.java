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
import com.ss.model.Grid;
import com.ss.model.Module;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoGrid;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryGrid;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;




@Service("serviceGrid")
public class ServiceGrid {

	static Logger log = Logger.getLogger(ServiceGrid.class.getName());

	@Autowired
	RepositoryGrid repositoryGrid;
	
	@Autowired
	RepositoryModule  repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;

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
	public DtoGrid saveOrUpdateGrid(DtoGrid dtoGrid) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Grid grid = null;
		if (dtoGrid.getId() != null && dtoGrid.getId() > 0) {
			grid = repositoryGrid.findByIdAndIsDeleted(dtoGrid.getId());

			 grid.setUpdatedBy(loggedInUserId);
             grid.setUpdatedDate(new Date());
             grid.setId(dtoGrid.getId());
         } else {
             grid = new Grid();
             grid.setCreatedDate(new Date());
             grid.setUpdatedDate(new Date());
             Integer rowId = repositoryGrid.getCountOfTotalGrid();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             grid.setId(increment);
         }
		grid.setGridId(dtoGrid.getGridId());
	    grid.setModule(repositoryModule.findOne(dtoGrid.getModuleId()));
	  //  grid.setScreen(repositoryScreen.findOne(dtoGrid.getScreenId()));

		grid = repositoryGrid.saveAndFlush(grid);

		return dtoGrid;
	}

//	public DtoSearch getAllModule(DtoModule dtoModule) {
//		DtoSearch dtoSearch = new DtoSearch();
//		dtoSearch.setPageNumber(dtoModule.getPageNumber());
//		dtoSearch.setPageSize(dtoModule.getPageSize());
//		dtoSearch.setTotalCount(repositoryModule.getCountOfTotalModule());
//		List<Module> moduleList = null;
//		if (dtoModule.getPageNumber() != null && dtoModule.getPageSize() != null) {
//			Pageable pageable = new PageRequest(dtoModule.getPageNumber(), dtoModule.getPageSize(), Direction.DESC, "createdDate");
//			moduleList = repositoryModule.findByIsDeleted(false, pageable);
//		} else {
//			moduleList = repositoryModule.findByIsDeletedOrderByCreatedDateDesc(false);
//		}
//		
//		List<DtoModule> dtoModules=new ArrayList<>();
//		if(moduleList!=null && !moduleList.isEmpty())
//		{
//			for (Module module : moduleList) 
//			{
//			 dtoModule=new DtoModule();
//			 dtoModule.setModuleId(module.getModuleId());
//			 dtoModule.setName(module.getName());
//			 dtoModule.setModuleCode(module.getModuleCode());
//			 dtoModule.setDescription(module.getDescription());
//			 dtoModule.setHelpMessage(module.getHelpMessage());
//			 dtoModule.setIsActive(module.getIsActive());
//				dtoModule.setLanguageId(module.getLanguage().getLanguageId()); 
//				 
//				dtoModules.add(dtoModule);
//			}
//			dtoSearch.setRecords(dtoModules);
//		}
//		return dtoSearch;
//	}
//	public List<Integer> delete(List<Integer> ids) {
//
//		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
//		try {
//			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
//				Integer moduleId = idIterator.next();
//				List<Module> modulesList = repositoryModule.findBymoduleIdAndIsDeleted(ids);
//				if(modulesList.size() == 0){
//					return ids;
//				}else{
//					repositoryModule.deleteSingleModulse(true, loggedInUserId, moduleId);
//					idIterator.remove();
//				}
//			}
//		} catch (Exception e) {
//			log.error("Error while deleting Account", e);
//		}
//		return ids;
//	}
//
//
//public DtoModule getById(int moduleId) {
//	DtoModule dtoModule  = new DtoModule();
//	try {
//		if (moduleId > 0) {
//			Module module = repositoryModule.findByAndIsDeleted(moduleId);
//
//			if (module != null) {
//				dtoModule = new DtoModule();
//				dtoModule.setModuleId(module.getModuleId());
//				dtoModule.setLanguageId(module.getLanguage().getLanguageId());
//				dtoModule.setDescription(module.getDescription());
//				dtoModule.setHelpMessage(module.getHelpMessage());
//				dtoModule.setModuleCode(module.getModuleCode());
//				dtoModule.setName(module.getName());
//				dtoModule.setIsActive(module.getIsActive());
//				
//
//			} 
//		} else {
//			dtoModule.setMessageType("INVALID_ID");
//
//		}
//
//	} catch (Exception e) {
//		log.error(e);
//	}
//	return dtoModule;
//}
//
//
//	
}

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
	   
	   grid.setScreen(repositoryScreen.findOne(dtoGrid.getScreenId()));

		grid = repositoryGrid.saveAndFlush(grid);

		return dtoGrid;
	}

	public DtoSearch getAllGrid(DtoGrid dtoGrid) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoGrid.getPageNumber());
		dtoSearch.setPageSize(dtoGrid.getPageSize());
		dtoSearch.setTotalCount(repositoryGrid.getCountOfTotalGrid());
		List<Grid> gridList = null;
		if (dtoGrid.getPageNumber() != null && dtoGrid.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoGrid.getPageNumber(), dtoGrid.getPageSize(), Direction.DESC, "createdDate");
			gridList = repositoryGrid.findByIsDeleted(false, pageable);
		} else {
			gridList = repositoryGrid.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoGrid> dtoGrids=new ArrayList<>();
		if(gridList!=null && !gridList.isEmpty())
		{
			for (Grid grid : gridList) 
			{
			 dtoGrid=new DtoGrid();
			 dtoGrid.setId(grid.getId());
			 dtoGrid.setGridId(grid.getGridId());
		 dtoGrid.setScreenId(grid.getScreen().getScreenId());
			 dtoGrid.setModuleId(grid.getModule().getModuleId());
			
				dtoGrids.add(dtoGrid);
			}
			dtoSearch.setRecords(dtoGrids);
		}
		return dtoSearch;
	}
	
	public List<Integer> delete(List<Integer> ids) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		try {
			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
				Integer gridId = idIterator.next();
				List<Grid> gridsList = repositoryGrid.findBygridIdAndIsDeleted(ids);
				if(gridsList.size() == 0){
					return ids;
				}else{
					repositoryGrid.deleteSingleGrids(true, loggedInUserId, gridId);
					idIterator.remove();
				}
			}
		} catch (Exception e) {
			log.error("Error while deleting Account", e);
		}
		return ids;
	}


public DtoGrid getById(int id) {
	DtoGrid dtoGrid  = new DtoGrid();
	try {
		if (id > 0) {
			Grid grid = repositoryGrid.findByAndIsDeleted(id);

			if (grid != null) {
				dtoGrid = new DtoGrid();
				dtoGrid.setId(grid.getId());
				
				dtoGrid.setGridId(grid.getGridId());
				
				dtoGrid.setModuleId(grid.getModule().getModuleId());
				
			dtoGrid.setScreenId(grid.getScreen().getScreenId());
				

			} 
		} else {
			dtoGrid.setMessageType("INVALID_ID");

		}

	} catch (Exception e) {
		log.error(e);
	}
	return dtoGrid;
}
 
 
	
}

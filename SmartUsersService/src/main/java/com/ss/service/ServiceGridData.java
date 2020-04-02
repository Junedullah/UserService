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
import com.ss.model.Grid;
import com.ss.model.GridData;
import com.ss.model.Module;
import com.ss.model.Screen;
import com.ss.model.dto.DtoGridData;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryGrid;
import com.ss.repository.RepositoryGridData;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryUser;




@Service("serviceGridData")
public class ServiceGridData {

	static Logger log = Logger.getLogger(ServiceGridData.class.getName());
	static Logger logger = Logger.getLogger(ServiceField.class.getName());

	
	@Autowired
	RepositoryUser repositoryUser;

	
	@Autowired
	ServiceGrid serviceGrid;

	@Autowired
	RepositoryGrid repositoryGrid;
	
	@Autowired
	RepositoryGridData repositoryGridData;
	
	@Autowired
	RepositoryFields repositoryFields;
	
	
	@Autowired
	RepositoryModule  repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	
	
	@Autowired(required = false)
	ServiceResponse serviceResponse;
	
	private static final String USER_ID = "userid";

	/**
	 * Description: save and update grid data
	 * 
	 * @param dtoGridData
	 * @return
	 */
	@Transactional
	public DtoGridData saveOrUpdateGridData(DtoGridData dtoGridData) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		GridData gridData = null;
		if (dtoGridData.getGridDataId() != null && dtoGridData.getGridDataId() > 0) {
			gridData = repositoryGridData.findByIdAndIsDeleted(dtoGridData.getGridDataId());

			 gridData.setUpdatedBy(loggedInUserId);
             gridData.setUpdatedDate(new Date());
             gridData.setGridDataId(dtoGridData.getGridDataId());
         } else {
             gridData = new GridData();
             gridData.setCreatedDate(new Date());
             gridData.setUpdatedDate(new Date());
             Integer rowId = repositoryGridData.getCountOfTotalGridDta();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             gridData.setGridDataId(increment);
         }
		
		
		
		gridData.setColOrder(dtoGridData.getColOrder());
		gridData.setIsVisible(dtoGridData.getIsVisible());
		gridData.setIsReset(dtoGridData.getIsReset());
		
		
	    gridData.setModule(repositoryModule.findOne(dtoGridData.getModuleId()));
	   
	   gridData.setScreen(repositoryScreen.findOne(dtoGridData.getScreenId()));
	   
	   gridData.setField(repositoryFields.findOne(dtoGridData.getFieldId()));
	   
	   gridData.setGrid(repositoryGrid.findOne(dtoGridData.getGridId()));

		gridData = repositoryGridData.saveAndFlush(gridData);

		return dtoGridData;
	}

	public DtoSearch getAllGridData(DtoGridData dtoGridData) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoGridData.getPageNumber());
		dtoSearch.setPageSize(dtoGridData.getPageSize());
		dtoSearch.setTotalCount(repositoryGridData.getCountOfTotalGridData());
		List<GridData> gridDataList = null;
		if (dtoGridData.getPageNumber() != null && dtoGridData.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoGridData.getPageNumber(), dtoGridData.getPageSize(), Direction.DESC, "createdDate");
			gridDataList = repositoryGridData.findByIsDeleted(false, pageable);
		} else {
			gridDataList = repositoryGridData.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoGridData> dtoGridDatas=new ArrayList<>();
		if(gridDataList!=null && !gridDataList.isEmpty())
		{
			for (GridData gridData : gridDataList) 
			{
			 dtoGridData=new DtoGridData();
			 
			 dtoGridData.setGridDataId(gridData.getGridDataId());
			 dtoGridData.setColOrder(gridData.getColOrder());
			 dtoGridData.setIsReset(gridData.getIsReset());
			 dtoGridData.setIsVisible(gridData.getIsVisible());
			 
			 dtoGridData.setScreenId(gridData.getScreen().getScreenId());
			 dtoGridData.setModuleId(gridData.getModule().getModuleId());
			 dtoGridData.setFieldId(gridData.getField().getFieldId());
			 dtoGridData.setGridId(gridData.getGrid().getGridId());
			
			 dtoGridDatas.add(dtoGridData);
			}
			dtoSearch.setRecords(dtoGridDatas);
		}
		return dtoSearch;
	}

	public List<Integer> delete(List<Integer> ids) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		try {
			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
				Integer gridDataId = idIterator.next();
				List<GridData> gridDatasList = repositoryGridData.findBygridIdAndIsDeleted(ids);
				if(gridDatasList.size() == 0){
					return ids;
				}else{
					repositoryGridData.deleteSingleGridData(true, loggedInUserId, gridDataId);
					idIterator.remove();
				}
			}
		} catch (Exception e) {
			log.error("Error while deleting gridData", e);
		}
		return ids;
	}
	
	public DtoGridData getById(int id) {
		DtoGridData dtoGridData  = new DtoGridData();
		try {
			if (id > 0) {
				GridData gridData = repositoryGridData.findByAndIsDeleted(id);

				if (gridData != null) {
					dtoGridData = new DtoGridData();
					
					dtoGridData.setGridDataId(gridData.getGridDataId());
					
					 dtoGridData.setGridDataId(gridData.getGridDataId());
					 dtoGridData.setColOrder(gridData.getColOrder());
					 dtoGridData.setIsReset(gridData.getIsReset());
					 dtoGridData.setIsVisible(gridData.getIsVisible());
					 
					 dtoGridData.setScreenId(gridData.getScreen().getScreenId());
					 dtoGridData.setModuleId(gridData.getModule().getModuleId());
					 dtoGridData.setFieldId(gridData.getField().getFieldId());
					 dtoGridData.setGridId(gridData.getGrid().getGridId());
					

				} 
			} else {
				dtoGridData.setMessageType("INVALID_ID");

			}

		} catch (Exception e) {
			log.error(e);
		}
		return dtoGridData;
	}

	
	 
	public DtoGridData changeVisible(DtoGridData dtoGridData) {
		logger.info("Change Visible Method");
		// DtoGridData dtoGridData = new DtoGridData();
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid").toString());
		List<GridData> dtoGridDataList = new ArrayList<>();

		try {
			for (DtoGridData fieldId : dtoGridData.getColumnList()) {
				GridData gridData = repositoryGridData.findByFieldFieldIdAndIsDeleted(fieldId.getFieldId(), loggedInUserId);

				if (gridData != null) {
					if (fieldId.getIsVisible() == null) {
						repositoryGridData.changeStatus(true, loggedInUserId, fieldId.getFieldId(), fieldId.getColOrder(), loggedInUserId);
					} else {
						repositoryGridData.changeStatus(fieldId.getIsVisible(), loggedInUserId, fieldId.getFieldId(), fieldId.getColOrder(), loggedInUserId);
					}
					repositoryGridData.changeStatusReset(fieldId.getFieldId(), loggedInUserId);
					dtoGridDataList.add(gridData);
				} else {
					GridData gridDatas = new GridData();
					Field field = repositoryFields.findOne(fieldId.getFieldId());
					Screen screen = repositoryScreen.findOne(field.getScreen().getScreenId());
					Module module = repositoryModule.findOne(screen.getModule().getModuleId());
					Grid grid = repositoryGrid.findOne(field.getGrid().getGridId());
					gridDatas.setField(field);
					gridDatas.setScreen(screen);
					gridDatas.setModule(module);
					gridDatas.setGrid(grid);
					if (fieldId.getIsVisible() == null) {
						gridDatas.setIsVisible(true);
					} else {
						gridDatas.setIsVisible(fieldId.getIsVisible());
					}

					gridDatas.setIsReset(false);
					gridDatas.setColOrder(fieldId.getColOrder());
					gridDatas.setCreatedBy(loggedInUserId);
					gridDatas.setUpdatedBy(loggedInUserId);
					repositoryGridData.saveAndFlush(gridDatas);
				}
			}
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
		return dtoGridData;
	}

	public DtoGridData hideAllColumns(Integer gridId) {
		logger.info("Hide All Column Method called!!");
		DtoGridData dtoGridData = new DtoGridData();
		/* DtoGrid dtoGrid = new DtoGrid(); */
		DtoSearch dtoSearch = new DtoSearch();
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid").toString());

		List<GridData> gridDataList = new ArrayList<>();
		List<DtoGridData> dtoGridDataList = new ArrayList<>();

		try {
			gridDataList = repositoryGridData.findByGridIdAndIsReset(gridId, loggedInUserId);
			if (gridDataList != null && gridDataList.size() > 0) {
				DtoGridData dtoGridData2 = new DtoGridData();
				repositoryGridData.hideAllColumns(loggedInUserId, gridId);
				dtoGridDataList.add(dtoGridData2);
				dtoGridData.setColumnList(dtoGridDataList);
			}
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
		logger.debug("Hide Columns :" + dtoGridData.getGridDataId());
		return dtoGridData;
	}

	public DtoGridData showAllColumns(Integer gridId) {
		logger.info("Show All Column Method called!!");
		DtoGridData dtoGridData = new DtoGridData();
		DtoSearch dtoSearch = new DtoSearch();
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid").toString());

		List<GridData> gridDataList = new ArrayList<>();
		List<DtoGridData> dtoGridDataList = new ArrayList<>();

		try {
			gridDataList = repositoryGridData.findByGridIdAndIsReset(gridId, loggedInUserId);
			if (gridDataList != null && gridDataList.size() > 0) {
				DtoGridData dtoGridData2 = new DtoGridData();
				repositoryGridData.showAllColumns(loggedInUserId, gridId);
				dtoGridDataList.add(dtoGridData2);
				dtoGridData.setColumnList(dtoGridDataList);
			}
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
		logger.debug("Show Columns :" + dtoGridData.getGridDataId());
		return dtoGridData;
	}

	public DtoGridData resetGrid(Integer gridId) {
		logger.info("Reset All Column Method called!!");
		DtoGridData dtoGridData = new DtoGridData();
		/* DtoGrid dtoGrid = new DtoGrid(); */
		DtoSearch dtoSearch = new DtoSearch();
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid").toString());

		List<GridData> gridDataList = new ArrayList<>();
		List<DtoGridData> dtoGridDataList = new ArrayList<>();

		try {
			gridDataList = repositoryGridData.findByGridIdAndIsReset(gridId, loggedInUserId);
			if (gridDataList != null && gridDataList.size() > 0) {
				// GridData GridData2 = new GridData();
				DtoGridData dtoGridData2 = new DtoGridData();
				repositoryGridData.resetGrid(loggedInUserId, gridId);
				dtoGridDataList.add(dtoGridData2);
				dtoGridData.setColumnList(dtoGridDataList);
			}
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		}
		logger.debug("Reset Columns :" + dtoGridData.getGridDataId());
		return dtoGridData;
	}


	
}

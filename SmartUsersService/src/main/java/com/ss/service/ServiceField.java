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
import com.ss.model.Grid;
import com.ss.model.Module;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoFieldDetail;
import com.ss.model.dto.DtoGrid;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryGrid;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryScreen;




@Service("serviceField")
public class ServiceField {

	static Logger log = Logger.getLogger(ServiceField.class.getName());

	@Autowired
	RepositoryGrid repositoryGrid;
	
	@Autowired
	RepositoryModule  repositoryModule;
	
	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryFields repositoryFields;


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
	public DtoFieldDetail saveOrUpdate(DtoFieldDetail dtoFieldDetail) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Field field = null;
		if (dtoFieldDetail.getFieldId() != null && dtoFieldDetail.getFieldId() > 0) {
			field = repositoryFields.findByIdAndIsDeleted(dtoFieldDetail.getFieldId());

			 field.setUpdatedBy(loggedInUserId);
             field.setUpdatedDate(new Date());
             field.setFieldId((dtoFieldDetail.getFieldId()));
         } else {
             field = new Field();
             field.setCreatedDate(new Date());
             field.setUpdatedDate(new Date());
             Integer rowId = repositoryFields.getCountOfTotalField();
             Integer increment = 0;
             if (rowId != 0) {
                 increment = rowId + 1;
             } else {
                 increment = 1;
             }
             field.setFieldId(increment);
         }
		field.setDescription(dtoFieldDetail.getDescription());
		field.setFieldName(dtoFieldDetail.getFieldName());
		field.setGridFieldName(dtoFieldDetail.getGridFieldName());
		field.setHelpMessage(dtoFieldDetail.getHelpMessage());
		field.setFieldShort(dtoFieldDetail.getFieldShort());
		field.setFieldCode(dtoFieldDetail.getFieldCode());
		field.setIsMandatory(dtoFieldDetail.getIsMandatory());
		field.setFieldWidth(dtoFieldDetail.getFieldWidth());
		field.setIsVisible(dtoFieldDetail.getIsVisible());
		
		 field.setLanguage(repositoryLanguage.findOne(dtoFieldDetail.getLanguageId()));
		
	   field.setScreen(repositoryScreen.findOne(dtoFieldDetail.getScreenId()));
	   
	   field.setGrid(repositoryGrid.findOne(dtoFieldDetail.getGridId()));
	   
	   field.setColOrder(dtoFieldDetail.getColOrder());
	   

		field = repositoryFields.saveAndFlush(field);

		return dtoFieldDetail;
	}

	public DtoSearch getAllField(DtoFieldDetail dtoFieldDetail) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoFieldDetail.getPageNumber());
		dtoSearch.setPageSize(dtoFieldDetail.getPageSize());
		dtoSearch.setTotalCount(repositoryFields.getCountOfTotalField());
		List<Field> fieldList = null;
		if (dtoFieldDetail.getPageNumber() != null && dtoFieldDetail.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoFieldDetail.getPageNumber(), dtoFieldDetail.getPageSize(), Direction.DESC, "createdDate");
			fieldList = repositoryFields.findByIsDeleted(false, pageable);
		} else {
			fieldList = repositoryFields.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoFieldDetail> dtoFieldDetails=new ArrayList<>();
		if(fieldList!=null && !fieldList.isEmpty())
		{
			for (Field field : fieldList) 
			{
			 dtoFieldDetail=new DtoFieldDetail();
			 
			 dtoFieldDetail.setFieldId(field.getFieldId());
			 dtoFieldDetail.setDescription(field.getDescription());
			 dtoFieldDetail.setFieldName(field.getFieldName());
			 dtoFieldDetail.setGridFieldName(field.getGridFieldName());
			 dtoFieldDetail.setHelpMessage(field.getHelpMessage());
			 dtoFieldDetail.setFieldCode(field.getFieldCode());
			 dtoFieldDetail.setFieldShort(field.getFieldShort());
			 dtoFieldDetail.setIsMandatory(field.getIsMandatory());
			 dtoFieldDetail.setFieldWidth(field.getFieldWidth());
			 dtoFieldDetail.setGridId(field.getGrid().getGridId());
			 dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
		     dtoFieldDetail.setScreenId(field.getScreen().getScreenId());
		     dtoFieldDetail.setIsVisible(field.getIsVisible());
		     dtoFieldDetail.setColOrder(field.getColOrder());
			
				dtoFieldDetails.add(dtoFieldDetail);
			}
			dtoSearch.setRecords(dtoFieldDetails);
		}
		return dtoSearch;
	}
	
	public List<Integer> delete(List<Integer> ids) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		try {
			for (Iterator<Integer> idIterator = ids.iterator(); idIterator.hasNext();) {
				Integer fieldId = idIterator.next();
				List<Field> fieldsList = repositoryFields.findBygridIdAndIsDeleted(ids);
				if(fieldsList.size() == 0){
					return ids;
				}else{
					repositoryFields.deleteSingleFields(true, loggedInUserId, fieldId);
					idIterator.remove();
				}
			}
		} catch (Exception e) {
			log.error("Error while deleting Account", e);
		}
		return ids;
	}


public DtoFieldDetail getById(int id) {
	DtoFieldDetail dtoFieldDetail  = new DtoFieldDetail();
	try {
		if (id > 0) {
			Field field = repositoryFields.findByAndIsDeleted(id);

			if (field != null) {
				dtoFieldDetail = new DtoFieldDetail();
				
				 dtoFieldDetail.setFieldId(field.getFieldId());
				 dtoFieldDetail.setDescription(field.getDescription());
				 dtoFieldDetail.setFieldName(field.getFieldName());
				 dtoFieldDetail.setGridFieldName(field.getGridFieldName());
				 dtoFieldDetail.setHelpMessage(field.getHelpMessage());
				 dtoFieldDetail.setFieldCode(field.getFieldCode());
				 dtoFieldDetail.setFieldShort(field.getFieldShort());
				 dtoFieldDetail.setIsMandatory(field.getIsMandatory());
				 dtoFieldDetail.setFieldWidth(field.getFieldWidth());
				 dtoFieldDetail.setGridId(field.getGrid().getGridId());
				 dtoFieldDetail.setLanguageId(field.getLanguage().getLanguageId());
			     dtoFieldDetail.setScreenId(field.getScreen().getScreenId());
			     dtoFieldDetail.setIsVisible(field.getIsVisible());
			     dtoFieldDetail.setColOrder(field.getColOrder());
				
				

			} 
		} else {
			dtoFieldDetail.setMessageType("INVALID_ID");

		}

	} catch (Exception e) {
		log.error(e);
	}
	return dtoFieldDetail;
}
 
 
	
}

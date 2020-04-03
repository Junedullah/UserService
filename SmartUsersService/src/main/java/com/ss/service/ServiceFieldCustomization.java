/**SmartSoftware User - Service */
/**
 * Description: ServiceFieldCustomization
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Shahnawaz
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ss.model.FieldCustomization;
import com.ss.model.dto.DtoFieldCustomization;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryFieldCustomization;
import com.ss.repository.RepositoryUser;

@Service("serviceFieldCustomization")
public class ServiceFieldCustomization {

	static Logger LOGGER = LoggerFactory.getLogger(ServiceFieldCustomization.class.getName());
	


	@Autowired
	private RepositoryFieldCustomization repositoryFieldCustomization;

	@Autowired(required = false)
	private HttpServletRequest httpServletRequest;

	@Autowired
	private RepositoryUser repositoryUser;

	
	/**
	 * Description: save and update fieldCustomization data
	 * 
	 * @param dtoFieldCustomization
	 * @return
	 */
	@Transactional
	public DtoFieldCustomization saveOrUpdateFieldCustomization(DtoFieldCustomization dtoFieldCustomization) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));

		FieldCustomization fieldCustomization = null;
		if (dtoFieldCustomization.getId() > 0) {
			fieldCustomization = repositoryFieldCustomization.findOne(dtoFieldCustomization.getId());
			fieldCustomization.setUpdatedBy(loggedInUserId);
		} else {
			fieldCustomization = new FieldCustomization();
			fieldCustomization.setCreatedBy(loggedInUserId);
		}

		fieldCustomization.setCode(dtoFieldCustomization.getCode());
		fieldCustomization.setFieldsToShow(dtoFieldCustomization.getFieldsToShow());
		
		fieldCustomization.setUser(repositoryUser.findOne(dtoFieldCustomization.getUserId()));
		fieldCustomization = repositoryFieldCustomization.saveAndFlush(fieldCustomization);
		if (fieldCustomization != null) {
			dtoFieldCustomization.setId(fieldCustomization.getId());
			return dtoFieldCustomization;
		} else
			return null;
	}

	/**
	 * Description: get fieldCustomization detail by fieldCustomization id
	 * 
	 * @param fieldCustomizationId
	 * @return
	 */
	public DtoFieldCustomization getByCodeAndUserId(String code, int userId) {
		DtoFieldCustomization dtoFieldCustomization = null;
		if (!StringUtils.isEmpty(code) && userId > 0) {
			FieldCustomization fieldCustomization = repositoryFieldCustomization.findByCodeAndUser(code, userId);
			if (fieldCustomization != null) {
				dtoFieldCustomization = new DtoFieldCustomization();
				dtoFieldCustomization.setId(fieldCustomization.getId());
				dtoFieldCustomization.setCode(fieldCustomization.getCode());
				dtoFieldCustomization.setFieldsToShow(fieldCustomization.getFieldsToShow());
				dtoFieldCustomization.setUserId(fieldCustomization.getUser().getUserId());
			}
		}
		return dtoFieldCustomization;
	}

	public DtoFieldCustomization deleteFieldCustomization(DtoFieldCustomization dtoFieldCustomization) {

		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader("userid"));
		DtoFieldCustomization deletedFieldCustomization = null;
		try {
			repositoryFieldCustomization.deleteSingleFieldCustomization(true, loggedInUserId,
					dtoFieldCustomization.getId());
		} catch (Exception e) {
			LOGGER.error("Error deleting leadSource: " + e);
		}
		return deletedFieldCustomization;
	}

	public DtoFieldCustomization getById(int id) throws Exception {
	DtoFieldCustomization dtoFieldCustomization  = new DtoFieldCustomization();
	
		if (id > 0) {
			FieldCustomization fieldCustomization = repositoryFieldCustomization.findByAndIsDeleted(id);

			if (fieldCustomization != null) {
				dtoFieldCustomization = new DtoFieldCustomization();
				
				dtoFieldCustomization.setId(fieldCustomization.getId());
				dtoFieldCustomization.setCode(fieldCustomization.getCode());
				dtoFieldCustomization.setFieldsToShow(fieldCustomization.getFieldsToShow());
				dtoFieldCustomization.setUserId(fieldCustomization.getUser().getUserId());
						
				
			} 
		} else {
			dtoFieldCustomization.setMessageType("INVALID_ID");

		}

	
	return dtoFieldCustomization;
}

	public DtoSearch getAllFieldCustomization(DtoFieldCustomization dtoFieldCustomization) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoFieldCustomization.getPageNumber());
		dtoSearch.setPageSize(dtoFieldCustomization.getPageSize());
		dtoSearch.setTotalCount(repositoryFieldCustomization.getCountOfTotalFieldCustomization());
		List<FieldCustomization> fieldCustomizationList = null;
		if (dtoFieldCustomization.getPageNumber() != null && dtoFieldCustomization.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoFieldCustomization.getPageNumber(), dtoFieldCustomization.getPageSize(), Direction.DESC, "createdDate");
			fieldCustomizationList = repositoryFieldCustomization.findByIsDeleted(false, pageable);
		} else {
			fieldCustomizationList = repositoryFieldCustomization.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoFieldCustomization> dtoFieldCustomizations=new ArrayList<>();
		if(fieldCustomizationList!=null && !fieldCustomizationList.isEmpty())
		{
			for (FieldCustomization  fieldCustomization : fieldCustomizationList) 
			{
			 dtoFieldCustomization=new DtoFieldCustomization();
			 dtoFieldCustomization.setId(fieldCustomization.getId());
				dtoFieldCustomization.setCode(fieldCustomization.getCode());
				dtoFieldCustomization.setFieldsToShow(fieldCustomization.getFieldsToShow());
				dtoFieldCustomization.setUserId(fieldCustomization.getUser().getUserId());
						
				dtoFieldCustomizations.add(dtoFieldCustomization);
			}
			dtoSearch.setRecords(dtoFieldCustomizations);
		}
		return dtoSearch;
	}

	}

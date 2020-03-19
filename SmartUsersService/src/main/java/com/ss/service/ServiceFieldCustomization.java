/**SmartSoftware User - Service */
/**
 * Description: ServiceFieldCustomization
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ss.model.FieldCustomization;
import com.ss.model.dto.DtoFieldCustomization;
import com.ss.repository.RepositoryFieldCustomization;

@Service("serviceFieldCustomization")
public class ServiceFieldCustomization {

	static Logger LOGGER = LoggerFactory.getLogger(ServiceFieldCustomization.class.getName());

	@Autowired
	private RepositoryFieldCustomization repositoryFieldCustomization;

	@Autowired(required = false)
	private HttpServletRequest httpServletRequest;

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
		fieldCustomization.setUserId(dtoFieldCustomization.getUserId());
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
				dtoFieldCustomization.setUserId(fieldCustomization.getUserId());
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
}

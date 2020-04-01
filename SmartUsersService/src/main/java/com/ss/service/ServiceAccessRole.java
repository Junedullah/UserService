/**
 * BTI - BAAN for Technology And Trade IntL. 
 * Copyright @ 2017 BTI. 
 * 
 * All rights reserved.
 * 
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF BTI. 
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE 
 * PRIOR EXPRESS WRITTEN PERMISSION OF BTI.
 */
package com.ss.service;

import com.ss.constant.SmartCodeType;
import com.ss.model.*;
import com.ss.model.dto.*;
import com.ss.repository.*;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: Service Access Role 
 * Name of Project: SmartSoftware
 * Created on: MARCH 27, 2020
 * @author Tehmina Ali
 * Version:
 */
@Service("serviceAccessRole")
public class ServiceAccessRole {

	private static final Logger LOGGER = Logger.getLogger(ServiceAccessRole.class);

	@Autowired
	RepositoryAccessRole repositoryAccessRole;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryFields repositoryFields;

	@Autowired
	RepositoryAccessRoleScreenRelation repositoryAccessRoleScreenRelation;

	@Autowired
	RepositoryAccessRoleModuleRelation repositoryAccessRoleModuleRelation;

	@Autowired
	RepositoryRoleGroupAccessRole repositoryRoleGroupAccessRole;

	private static final String USER_ID ="userid";

	/**
	 * Description: Save New Access Role 
	 * @param dtoAccessRole
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int saveAccessRole(DtoAccessRole dtoAccessRole) {
		int id = 0;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		if (dtoAccessRole != null) {
			AccessRole accessRole = this.repositoryAccessRole.findByAccessRoleIdAndIsDeleted(dtoAccessRole.getId(),
					false);
			if (accessRole != null) {
				accessRole.setUpdatedBy(loggedInUserId);
				accessRole.setRoleName(dtoAccessRole.getRoleName());
				accessRole.setRoleDescription(dtoAccessRole.getRoleDescription());
				accessRole = this.repositoryAccessRole.saveAndFlush(accessRole);
				if (accessRole != null) {
					id = accessRole.getAccessRoleId();
				}
			} else {
				accessRole = new AccessRole();
				accessRole.setCreatedBy(loggedInUserId);
				accessRole.setRoleCode(this.codeGenerator.getGeneratedCode(SmartCodeType.ROLE.name()));
				accessRole.setIsDeleted(false);
				accessRole.setRoleName(dtoAccessRole.getRoleName());
				accessRole.setRoleDescription(dtoAccessRole.getRoleDescription());
				accessRole = this.repositoryAccessRole.saveAndFlush(accessRole);
				if (accessRole != null) {
					id = accessRole.getAccessRoleId();
				}
			}
		}
		return id;
	}


	/**
	 * Description: Get Access Role details by access role id
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoAccessRole getAccessRoleDetails(Integer id) {
		DtoAccessRole dtoAccessRole = null;
		AccessRole accessRole = this.repositoryAccessRole.findByAccessRoleIdAndIsDeleted(id, false);
		if (accessRole != null) {
			dtoAccessRole = new DtoAccessRole(accessRole);
		}
		return dtoAccessRole;
	}

	/**
	 * Description: Search access role by search keyword
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchAcessRole(DtoSearch dtoSearch) {
		if (dtoSearch != null) {
			dtoSearch.setTotalCount(
					this.repositoryAccessRole.predictiveAccessRoleSearchTotalCount(dtoSearch.getSearchKeyword()));
			List<AccessRole> accessRoleList = this.repositoryAccessRole.predictiveAccessRoleSearchWithPagination(
					dtoSearch.getSearchKeyword(),
					new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"));
			if (accessRoleList != null && !accessRoleList.isEmpty()) {
				List<DtoAccessRole> dtoAccessRoleList = new ArrayList<>();
				for (AccessRole accessRole : accessRoleList) {
					dtoAccessRoleList.add(new DtoAccessRole(accessRole.getAccessRoleId(), accessRole.getRoleCode(),
							accessRole.getRoleName(), accessRole.getRoleDescription()));
				}
				dtoSearch.setRecords(dtoAccessRoleList);
			}
		}
		return dtoSearch;
	}

	/**
	 * Description: Delete Multiple Roles
	 * @param idList
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean deleteMultipleRoles(List<Integer> idList) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		boolean status = false;
		try {
			this.repositoryAccessRole.deleteRoles(idList, true, loggedInUserId);
			// delete access role with role group mapping for these roles
			this.repositoryRoleGroupAccessRole.deleteAllRoleGroupAccessRolesForMultipleAccessRole(idList,
					loggedInUserId);

			status = true;
		} catch (NumberFormatException e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}
		return status;
	}

}

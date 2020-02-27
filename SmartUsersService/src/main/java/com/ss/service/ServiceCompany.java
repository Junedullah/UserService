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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.Company;
import com.ss.model.RoleGroup;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserGroupRoleGroup;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoCompanyStats;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryCityMaster;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryCountryMaster;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryRoleGroupAccessRole;
import com.ss.repository.RepositoryStateMaster;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserGroupRoleGroup;
import com.ss.util.CommonUtils;
import com.ss.util.DatabaseFactory;
import com.ss.util.UtilRandomKey;



@Service("serviceCompany")
public class ServiceCompany {

	static Logger log = Logger.getLogger(ServiceCompany.class.getName());

	@Autowired
	RepositoryCompany repositoryCompany;

	/*
	 * @Autowired RepositoryReportMaster repositoryReportMaster;
	 */
	/*
	 * @Autowired CodeGenerator codeGenerator;
	 */
	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryCountryMaster repositoryCountryMaster;

	@Autowired
	RepositoryCityMaster repositoryCityMaster;

	@Autowired
	RepositoryStateMaster repositoryStateMaster;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;

	@Autowired
	RepositoryUserGroupRoleGroup repositoryUserGroupRoleGroup;

	@Autowired
	RepositoryRoleGroupAccessRole repositoryRoleGroupAccessRole;

	@Autowired
	ServiceResponse serviceResponse;
	@Autowired
	DatabaseFactory databaseFactory;

	@Value("${script.dbSuffix}")
	private String dbSuffix;

	private static final String USER_ID = "userid";

	/**
	 * Description: save and update company data
	 * 
	 * @param dtoCompany
	 * @return
	 */
	@Transactional
	public DtoCompany saveOrUpdateCompany(DtoCompany dtoCompany) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Company company = null;
		if (dtoCompany.getId() != null && dtoCompany.getId() > 0) {
			company = repositoryCompany.findByCompanyIdAndIsDeleted(dtoCompany.getId(), false);

		company.setUpdatedBy(loggedInUserId);
		} else {
			company = new Company();
			// String tenant =
			// codeGenerator.getGeneratedCode(BTICodeType.COMPANYCODE.name());
			String tenant = CommonUtils.removeNull(dtoCompany.getName()).replaceAll(" ", "_");

			company.setCompanyCode(tenant);
			tenant += dbSuffix; // "_algoras";
			tenant = tenant.toLowerCase();
			company.setTenantId(tenant);
		//	company.setCreatedBy(loggedInUserId);
			company.setIsActive(true);
			//boolean databaseStatus = databaseFactory.createTenantDatabase(tenant);
			/*
			 * if (!databaseStatus) { return null; }
			 */
			/*
			 * if(dtoCompany.getDatabaseIP()!=null &&
			 * !dtoCompany.getDatabaseIP().trim().isEmpty()){ databaseStatus =
			 * databaseFactory.createTenantDatabase(tenant,dtoCompany.getDatabaseIP(),
			 * dtoCompany.getPort(),dtoCompany.getUsername(),dtoCompany.getPassword()); }
			 * if(!databaseStatus){ return null; }else{ databaseStatus =
			 * databaseFactory.createTenantDatabaseTable(tenant,dtoCompany.getDatabaseIP(),
			 * dtoCompany.getPort(),dtoCompany.getUsername(),dtoCompany.getPassword());
			 * if(!databaseStatus){ return null; } }
			 */
		}
		company.setDatabaseIP(dtoCompany.getDatabaseIP());
		company.setDatabasePort(dtoCompany.getPort());
		company.setDatabaseUsername(dtoCompany.getUsername());
		company.setDatabasePassword(dtoCompany.getPassword());
		company.setName(dtoCompany.getName());
		company.setAddress(dtoCompany.getAddress());
		company.setFax(dtoCompany.getFax());
		company.setPhone(dtoCompany.getPhone());
		company.setZipcode(dtoCompany.getZipCode());
		company.setEmail(dtoCompany.getEmail());
		company.setWebAddress(dtoCompany.getWebAddress());
		company.setLatitude(dtoCompany.getLatitude());
		company.setLongitude(dtoCompany.getLongitude());
		company.setCompanyCode(dtoCompany.getCompanyCode());
		company.setStateMaster(repositoryStateMaster.findOne(dtoCompany.getStateId()));
		company.setCityMaster(repositoryCityMaster.findOne(dtoCompany.getCityId()));
		company.setCountryMaster(
				repositoryCountryMaster.findByCountryIdAndIsDeletedAndIsActive(dtoCompany.getCountryId(), false, true));
		company.setCountryCode(dtoCompany.getCountryCode());
		company = repositoryCompany.saveAndFlush(company);

		//dtoCompany = new DtoCompany(company);
		return dtoCompany;
	}

	
	public DtoSearch getAllCompany(DtoCompany dtoCompany) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoCompany.getPageNumber());
		dtoSearch.setPageSize(dtoCompany.getPageSize());
		dtoSearch.setTotalCount(repositoryCompany.getCountOfTotalCompanies());
		List<Company> companyList = null;
		if (dtoCompany.getPageNumber() != null && dtoCompany.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoCompany.getPageNumber(), dtoCompany.getPageSize(), Direction.DESC, "createdDate");
			companyList = repositoryCompany.findByIsDeleted(false, pageable);
		} else {
			companyList = repositoryCompany.findByIsDeletedOrderByCreatedDateDesc(false);
		}
		
		List<DtoCompany> dtoCompanies=new ArrayList<>();
		if(companyList!=null && !companyList.isEmpty())
		{
			for (Company company : companyList) 
			{
			 dtoCompany=new DtoCompany(company);
				 if(dtoCompany.getIsActive()){
					 dtoCompany.setCompanyStatus(serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
					}
					else
					{
						dtoCompany.setCompanyStatus(serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
					}
				 
				 dtoCompanies.add(dtoCompany);
			}
			dtoSearch.setRecords(dtoCompanies);
		}
		return dtoSearch;
	}
	
	public DtoCompany deleteCompany(List<Integer> ids) {
		DtoCompany dtoCompany = new DtoCompany();
		dtoCompany.setDeleteMessage(serviceResponse.getStringMessageByShortAndIsDeleted("COMPANY_DELETED", false));
		dtoCompany.setAssociateMessage(
				serviceResponse.getStringMessageByShortAndIsDeleted("COMPANIES_ASSOCIATED", false));
		List<DtoCompany> associateCompanies = new ArrayList<>();
		List<DtoCompany> deleteCompanies = new ArrayList<>();
		//int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		try {
			for (Integer companyId : ids) {
				Company company = repositoryCompany.findOne(companyId);
				DtoCompany dtoCompany2 = new DtoCompany();
				dtoCompany2.setCompanyId(companyId);
				dtoCompany2.setName(company.getName());
				int count = repositoryUserCompanyRelation.getCountOfTotalCompaniesUsers(companyId);
				if (count > 0) {
					associateCompanies.add(dtoCompany2);
				} else 
				{
					repositoryCompany.deleteSingleCompany(true, 2/*loggedInUserId*/, companyId);
					deleteCompanies.add(dtoCompany2);

				}
			}
			dtoCompany.setAssociateCompanies(associateCompanies);
			dtoCompany.setDeleteCompanies(deleteCompanies);
			 
		} catch (NumberFormatException e) {
			log.info(Arrays.toString(e.getStackTrace()));
		}
		return dtoCompany;
	}
	
	public DtoCompany getCompanyByCompanyId(int companyId) {
		DtoCompany dtoCompany = new DtoCompany();
		if (companyId > 0) {
			Company company = repositoryCompany.findByCompanyIdAndIsDeleted(companyId, false);
			if (company != null) {
				dtoCompany = new DtoCompany(company);
			} else {
				dtoCompany.setMessageType("COMPANY_NOT_GETTING");

			}
		} else {
			dtoCompany.setMessageType("INVALID_COMPANY_ID");

		}
		return dtoCompany;
	}
	
	
	public DtoCompany getCompanyByTenantId(DtoCompany dtoCompany) {
		if(dtoCompany.getTenantId() != "" || dtoCompany.getTenantId() != null) {
		Company company = repositoryCompany.findByTenantIdAndIsDeleted(dtoCompany.getTenantId(), false);
		if(company != null) {
			dtoCompany = new DtoCompany();
			dtoCompany.setName(company.getName());
		}
		return dtoCompany;
		}else {
			return null;
		}
	}

	public boolean getCompanyByCompanyName(String companyName) {
		   List<Company> list = repositoryCompany.findByNameAndIsDeleted(companyName, false);
		   return list!=null && !list.isEmpty();
	}
	
	
	public DtoSearch getAllCompanyListForDropDown(DtoCompany dtoCompany) {
		DtoSearch dtoSearch = new DtoSearch();
		dtoSearch.setPageNumber(dtoCompany.getPageNumber());
		dtoSearch.setPageSize(dtoCompany.getPageSize());
		dtoSearch.setTotalCount(repositoryCompany.getCountOfTotalCompaniesIsActive());
		List<Company> companyList = null;
		if (dtoCompany.getPageNumber() != null && dtoCompany.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoCompany.getPageNumber(), dtoCompany.getPageSize());
			companyList = repositoryCompany.findByIsDeletedAndIsActive(false, pageable, true);
		} else {
			companyList = repositoryCompany.findByIsDeletedAndIsActive(false, true);
		}

		List<DtoCompany> dtoCompanies = new ArrayList<>();
		if (companyList != null && !companyList.isEmpty()) {
			for (Company company : companyList) {
				dtoCompany = new DtoCompany();
				dtoCompany.setId(company.getCompanyId());
				dtoCompany.setName(company.getName());
				dtoCompanies.add(dtoCompany);
			}
		}
		dtoSearch.setRecords(dtoCompanies);
		return dtoSearch;
	}

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoCompany blockUnblockCompany(DtoCompany dtoCompany) {
		Company company = this.repositoryCompany.findByCompanyIdAndIsDeleted(dtoCompany.getId(), false);
		if (company != null) {
			company.setIsActive(dtoCompany.getIsActive());
			this.repositoryCompany.saveAndFlush(company);
		}
		return dtoCompany;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchCompanies(DtoSearch dtoSearch)
	{
		if(dtoSearch != null)
		{
			dtoSearch.setTotalCount(this.repositoryCompany.predictiveCompanySearchTotalCount(dtoSearch.getSearchKeyword()));
			List<Company> companyList =null;
			if(dtoSearch.getPageNumber()!=null && dtoSearch.getPageSize()!=null){
				companyList= this.repositoryCompany.predictiveCompanySearchWithPagination(dtoSearch.getSearchKeyword() , new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"));
			}
			else{
				companyList= this.repositoryCompany.predictiveCompanySearchWithPagination(dtoSearch.getSearchKeyword());
			}
			
			if(companyList != null && !companyList.isEmpty()){
				List<DtoCompany> dtoCompanyList = new ArrayList<>();
				for (Company company : companyList) {
					DtoCompany dtoCompany = new DtoCompany(company);
					if (dtoCompany.getIsActive()) {
						dtoCompany.setCompanyStatus(
								serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
					} else {
						dtoCompany.setCompanyStatus(
								serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
					}
					dtoCompanyList.add(dtoCompany);
				}
				dtoSearch.setRecords(dtoCompanyList);
			}
		}
		return dtoSearch;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<DtoCompany> getTotalCompaniesWithTotalUser() {
		List<DtoCompany> dtoCompanyList = new ArrayList<>();
		List<Company> companyList = this.repositoryCompany.findByIsDeletedAndIsActive(false,true);
		if (companyList != null && !companyList.isEmpty()) {

			DtoCompany dtoCompany = null;
			for (Company company : companyList) {
				dtoCompany = new DtoCompany();
				dtoCompany.setId(company.getCompanyId());
				if (UtilRandomKey.isNotBlank(company.getName())) {
					dtoCompany.setName(company.getName());
				} else {
					dtoCompany.setName("");
				}
				dtoCompany.setTotalUsers(String
						.valueOf(repositoryUserCompanyRelation.getCountOfTotalCompaniesUsers(company.getCompanyId())));
				dtoCompany.setValue(String
						.valueOf(repositoryUserCompanyRelation.getCountOfTotalCompaniesUsers(company.getCompanyId())));
				dtoCompanyList.add(dtoCompany);
			}
		}
		return dtoCompanyList;
	}

	
	public DtoCompanyStats getCompanyStats(Integer companyId) {
		Company company = this.repositoryCompany.findByCompanyIdAndIsDeleted(companyId, false);
		if (company != null) {
			DtoCompanyStats dtoCompanyStats = new DtoCompanyStats();

			dtoCompanyStats
					.setActiveUserCount(this.repositoryUserCompanyRelation.getActiveUserCountForCompany(companyId));  // sets active user count
			dtoCompanyStats
					.setInActiveUserCount(this.repositoryUserCompanyRelation.getInActiveUserCountForCompany(companyId));  // sets inactive user count
			// user group count
			Integer userGroupCount = this.repositoryUserCompanyRelation.getUserGroupCountForCompany(companyId);
			dtoCompanyStats.setUserGroupCount(userGroupCount);	// sets user group count
			// if there is no user group, then role group count and role count would be 0
			if (userGroupCount > 0) {
				// For role group count, need to fetch user groups and then role group count
				List<UserCompanyRelation> userCompanyRelationList = this.repositoryUserCompanyRelation
						.getUserGroupsForCompany(companyId);
				if (userCompanyRelationList != null && !userCompanyRelationList.isEmpty()) {
					List<Integer> userGroupIdList = new ArrayList<>();
					for (UserCompanyRelation userCompanyRelation : userCompanyRelationList) {
						if (userCompanyRelation.getUserGroup() != null) {
							userGroupIdList.add(userCompanyRelation.getUserGroup().getUserGroupId());
						}
					}
					// now fetch user group role group relations
					List<UserGroupRoleGroup> userGroupRoleGroupList = this.repositoryUserGroupRoleGroup
							.getRoleGroupsForCompany(userGroupIdList);
					if (userGroupRoleGroupList != null && !userGroupRoleGroupList.isEmpty()) {
						Map<String, RoleGroup> roleGroupMap = new HashMap<>();
						RoleGroup roleGroup = null;
						for (UserGroupRoleGroup userGroupRoleGroup : userGroupRoleGroupList) {
							roleGroup = userGroupRoleGroup.getRoleGroup();
							if (roleGroup != null
									&& !roleGroupMap.containsKey(String.valueOf(roleGroup.getRoleGroupId()))) {
								roleGroupMap.put(String.valueOf(roleGroup.getRoleGroupId()), roleGroup);
							}
						}
						// set role group count
						if (!roleGroupMap.isEmpty()) {
							dtoCompanyStats.setRoleGroupCount(roleGroupMap.size());
							// convert map keys to list
							List<Integer> roleGroupIdList = new ArrayList<>();
							Iterator<String> iterator = roleGroupMap.keySet().iterator();
							while (iterator.hasNext()) {
								try {
									roleGroupIdList.add(Integer.parseInt(iterator.next()));
								} catch (NumberFormatException e) {
									log.info(Arrays.toString(e.getStackTrace()));
								}
							}
							// get 
							dtoCompanyStats.setRoleCount(
									this.repositoryRoleGroupAccessRole.getUserGroupCountForCompany(roleGroupIdList));
						} else {
							dtoCompanyStats.setRoleCount(0);	// sets user group count
						}
					}
				} else {
					dtoCompanyStats.setRoleGroupCount(0);	// sets user group count
					dtoCompanyStats.setRoleCount(0);	// sets user group count
				}
			} else {
				dtoCompanyStats.setRoleGroupCount(0);	// sets user group count
				dtoCompanyStats.setRoleCount(0);	// sets user group count
			}
			return dtoCompanyStats;
		}
		return null;
	}
	
	
	
}

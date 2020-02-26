/**SmartSoftware User - Service */
/**
 * Description: Service Company
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.bti.repository.RepositoryCityMaster;
//import com.bti.repository.RepositoryCountryMaster;
//import com.bti.repository.RepositoryReportMaster;
//import com.bti.repository.RepositoryRoleGroupAccessRole;
//import com.bti.repository.RepositoryStateMaster;
//import com.bti.repository.RepositoryUserCompanyRelation;
//import com.bti.repository.RepositoryUserGroupRoleGroup;
import com.ss.model.Company;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryException;
import com.ss.util.CommonUtils;
import com.ss.util.DatabaseFactory;



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

//	@Autowired
//	RepositoryCountryMaster repositoryCountryMaster;
//
//	@Autowired
//	RepositoryCityMaster repositoryCityMaster;
//
//	@Autowired
//	RepositoryStateMaster repositoryStateMaster;
//
//	@Autowired
//	RepositoryUserCompanyRelation repositoryUserCompanyRelation;
//
//	@Autowired
//	RepositoryUserGroupRoleGroup repositoryUserGroupRoleGroup;

//	@Autowired
//	RepositoryRoleGroupAccessRole repositoryRoleGroupAccessRole;

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
	//	int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		Company company = null;
		if (dtoCompany.getId() != null && dtoCompany.getId() > 0) {
			company = repositoryCompany.findByCompanyIdAndIsDeleted(dtoCompany.getId(), false);

		//	company.setUpdatedBy(loggedInUserId);
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
//		company.setStateMaster(repositoryStateMaster.findOne(dtoCompany.getStateId()));
//		company.setCityMaster(repositoryCityMaster.findOne(dtoCompany.getCityId()));
//		company.setCountryMaster(
//				repositoryCountryMaster.findByCountryIdAndIsDeletedAndIsActive(dtoCompany.getCountryId(), false, true));
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
				//int count = repositoryUserCompanyRelation.getCountOfTotalCompaniesUsers(companyId);
//				if (count > 0) {
//					associateCompanies.add(dtoCompany2);
//				} else 
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
	
	
}

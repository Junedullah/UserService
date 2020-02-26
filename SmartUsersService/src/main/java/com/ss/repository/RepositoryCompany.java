/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryCompany 
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Company;

@Repository("repositoryCompany")
public interface RepositoryCompany extends JpaRepository<Company, Integer> {

	/**
	 * @param companyId
	 * @param deleted
	 * @return
	 */
	public Company findByCompanyIdAndIsDeleted(int companyId, boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	public List<Company> findByIsDeleted(Boolean deleted);

	/**
	 * @return
	 */
	@Query("select count(*) from Company c where c.isDeleted=false")
	public Integer getCountOfTotalCompanies();

	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<Company> findByIsDeleted(Boolean deleted, Pageable pageable);

	/**
	 * @param deleted
	 * @param idList
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Company c set c.isDeleted =:deleted, c.updatedBy=:updateById where c.companyId IN (:idList)")
	public void deleteCompanyies(@Param("deleted") Boolean deleted, @Param("idList") List<Integer> idList,
			@Param("updateById") Integer updateById);

	/**
	 * @param deleted
	 * @param updateById
	 * @param companyId
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Company c set c.isDeleted =:deleted , c.updatedBy =:updateById where c.companyId =:companyId ")
	public void deleteSingleCompany(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
			@Param("companyId") Integer companyId);

	/**
	 * @return
	 */
	public Company findTop1ByOrderByCompanyIdDesc();

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @return
	 */
	@Query("select c from Company c where (c.name like %:searchKeyWord% or c.companyCode like %:searchKeyWord% or c.email like %:searchKeyWord% or c.countryMaster.countryName like %:searchKeyWord% or c.phone like %:searchKeyWord% ) and c.isDeleted=false")
	public List<Company> predictiveCompanySearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
			Pageable pageable);

	/**
	 * @param searchKeyWord
	 * @return
	 */
	@Query("select count(*) from Company c where (c.name like %:searchKeyWord% or c.companyCode like %:searchKeyWord% or c.email like %:searchKeyWord% or c.countryMaster.countryName like %:searchKeyWord%  or c.phone like %:searchKeyWord%  ) and c.isDeleted=false")
	public Integer predictiveCompanySearchTotalCount(@Param("searchKeyWord") String searchKeyWord);

	/**
	 * @return
	 */
	@Query("select count(*) from Company c where c.isDeleted=false and c.isActive=true")
	public Integer getCountOfTotalCompaniesIsActive();

	/**
	 * @param deleted
	 * @param isActive
	 * @return
	 */
	public List<Company> findByIsDeletedAndIsActive(Boolean deleted, Boolean isActive);

	/**
	 * @param deleted
	 * @param pageable
	 * @param isActive
	 * @return
	 */
	public List<Company> findByIsDeletedAndIsActive(Boolean deleted, Pageable pageable, Boolean isActive);
	
	
	public List<Company> findByIsDeletedOrderByCreatedDateDesc(Boolean deleted);
	
	public Company findTop1ByIsDeletedAndIsActiveAndName(Boolean deleted, Boolean isActive, String name);
	
	
	public Company findByTenantIdAndIsDeleted(String companyTenantId, boolean deleted);
	
	@Query("select c from Company c where (c.name like %:searchKeyWord% or c.companyCode like %:searchKeyWord% or c.email like %:searchKeyWord% or c.countryMaster.countryName like %:searchKeyWord% or c.phone like %:searchKeyWord% ) and c.isDeleted=false")
	public List<Company> predictiveCompanySearchWithPagination(@Param("searchKeyWord") String searchKeyWord);

	public Company findTop1ByTenantIdAndIsDeleted(String companyTanentId, boolean b);
	
	public List<Company> findByNameAndIsDeleted(String name, boolean deleted);

}

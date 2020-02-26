/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryAuthorizationSetting 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
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

import com.ss.model.AuthorizationSetting;


@Repository("repositoryAuthorizationSetting")
public interface RepositoryAuthorizationSetting extends JpaRepository<AuthorizationSetting, Integer> {

	/**
	 * @param deleted
	 * @return
	 */
	List<AuthorizationSetting> findByIsDeleted(Boolean deleted);
	
	List<AuthorizationSetting> findByIsDeleted(Boolean deleted,Pageable pageable);
	
	@Query("select count(*) from AuthorizationSetting au where au.isDeleted=false")
	public Integer getCountOfTotalAuthSettings();
	
	
	/*@Query("select ud from UserDetail ud where (ud.firstName like %:searchKeyWord% or ud.lastName like %:searchKeyWord% or ud.middleName like %:searchKeyWord%) and ud.isDeleted=false and  ud.user.role.roleId=:roleId")
	List<AuthorizationSetting> searchAuthSettings(Boolean deleted,Pageable pageable);*/
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update AuthorizationSetting ast set ast.isDeleted =:deleted, ast.updatedBy=:updateById where ast.authSettingId IN (:idList)")
	void deleteAuthSettings(@Param("idList") List<Integer> idList, @Param("deleted") boolean deleted,
			@Param("updateById") Integer updateById);
	
	
	//ME
	//@Query("select count(*) from AuthorizationSetting au where au.isDeleted=false and (au.)")
	//List<AuthorizationSetting> PredictiveSearch(Boolean deleted);
	
	//@Query("select count(*) from AuthorizationSetting au where au.isDeleted=false")
	//List<AuthorizationSetting> PredictiveSearchWithPagination(Boolean deleted,Pageable pageable);
	
	//@Query("select count(*) from AuthorizationSetting au where au.isDeleted=false")
	//public Integer CountForPredictiveSearch();
	
}

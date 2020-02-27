/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserDraft 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.UserDraft;

@Repository("repositoryUserDraft")
public interface RepositoryUserDraft extends JpaRepository<UserDraft, Integer> 
{
	List<UserDraft> findByUserUserId(int userId);
	
	List<UserDraft> findByUserUserIdAndCompanyCompanyId(int userId,int companyId);
	
	/*@Query("select u from UserDraft u where u.user.userId !=:userId and u.screen.screenCode=:screenCode and u.company is null")
	UserDraft checkScreenIsBlockByAnotherUserOrNot(@Param("screenCode") String screenCode, 
			@Param("userId") int userId);
	
	@Query("select u from UserDraft u where u.user.userId !=:userId and u.screen.screenCode=:screenCode and u.company.companyId=:companyId")
	UserDraft checkScreenIsBlockByAnotherUserOrNotByCompany(@Param("screenCode") String screenCode, 
			@Param("userId") int userId,@Param("companyId") int companyId);
	*/
}

/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserCompanyRelation 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.UserCompanyRelation;

@Repository("repositoryUserCompanyRelation")
public interface RepositoryUserCompanyRelation extends JpaRepository<UserCompanyRelation, Integer> {

	/**
	 * @param id
	 * @param deleted
	 * @return
	 */
	public UserCompanyRelation findByUserCompanyRelationIdAndIsDeleted(Integer id, Boolean deleted);

	/**
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public List<UserCompanyRelation> findByUserUserIdAndIsDeleted(Integer userId, Boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	public List<UserCompanyRelation> findByIsDeleted(Boolean deleted);

	/**
	 * @param companyId
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public UserCompanyRelation findByCompanyCompanyIdAndUserUserIdAndIsDeleted(int companyId, int userId,
			boolean deleted);

	/**
	 * @param companyId
	 * @param userId
	 * @param deleted
	 * @param authSettingId
	 * @return
	 */
	public UserCompanyRelation findByCompanyCompanyIdAndUserUserIdAndIsDeletedAndAuthorizationSettingAuthSettingId(
			int companyId, int userId, boolean deleted, int authSettingId);

	/**
	 * @param companyId
	 * @param deleted
	 * @return
	 */
	public List<UserCompanyRelation> findByCompanyCompanyIdAndIsDeleted(Integer companyId, Boolean deleted);

	/**
	 * @param deleted
	 * @param userGroupId
	 * @return
	 */
	List<UserCompanyRelation> findByIsDeletedAndUserGroupUserGroupId(Boolean deleted, Integer userGroupId);

	/**
	 * @param userGroupId
	 * @return
	 */
	@Query("SELECT ud.user.userId FROM UserCompanyRelation ud WHERE ud.userGroup.userGroupId=:userGroupId")
	List<Integer> getUseridsByUserGroupId(@Param("userGroupId") int userGroupId);

	/**
	 * @param companyId
	 * @return
	 */
	@Query("select DISTINCT count(c.user.userId) from UserCompanyRelation c where c.company.companyId=:companyId and c.user.isDeleted=false and c.isDeleted=false")
	public Integer getCountOfTotalCompaniesUsers(@Param("companyId") int companyId);

	/**
	 * @param deleted
	 * @param userGroupId
	 * @param isActive
	 * @return
	 */
	List<UserCompanyRelation> findByIsDeletedAndUserGroupUserGroupIdAndUserIsActive(Boolean deleted,
			Integer userGroupId, Boolean isActive);

	/**
	 * @param companyId
	 * @return
	 */
	@Query("select count(c.user.userId) from UserCompanyRelation c where c.company.companyId=:companyId and c.user.isDeleted=false and c.user.isActive=true and c.isDeleted=false and c.company.isDeleted=false")
	public Integer getActiveUserCountForCompany(@Param("companyId") int companyId);

	/**
	 * @param companyId
	 * @return
	 */
	@Query("select count(c.user.userId) from UserCompanyRelation c where c.company.companyId=:companyId and c.user.isDeleted=false and c.user.isActive=false and c.isDeleted=false and c.company.isDeleted=false")
	public Integer getInActiveUserCountForCompany(@Param("companyId") int companyId);

	/**
	 * @param companyId
	 * @return
	 */
	@Query("select count( DISTINCT c.userGroup.userGroupId) from UserCompanyRelation c where c.company.companyId=:companyId and c.isDeleted=false and c.company.isDeleted=false and c.userGroup.isDeleted=false")
	public Integer getUserGroupCountForCompany(@Param("companyId") int companyId);

	/**
	 * @param companyId
	 * @return
	 */
	@Query("select c from UserCompanyRelation c where c.company.companyId=:companyId and c.isDeleted=false and c.company.isDeleted=false and c.userGroup.isDeleted=false")
	public List<UserCompanyRelation> getUserGroupsForCompany(@Param("companyId") int companyId);

	/**
	 * @param deleted
	 * @param userGroupId
	 * @param isActive
	 * @param companyId
	 * @param userDeleted
	 * @return
	 */
	List<UserCompanyRelation> findByIsDeletedAndUserGroupUserGroupIdAndUserIsActiveAndCompanyCompanyIdAndUserIsDeleted(
			Boolean deleted, Integer userGroupId, Boolean isActive, Integer companyId, Boolean userDeleted);
	
	/**
	 * @param idList
	 * @param updateById
	 * @param authorization
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update UserCompanyRelation ucr set ucr.authorizationSetting=:authorization,ucr.updatedBy=:updateById where ucr.authorizationSetting.authSettingId IN (:idList)")
	public void setNullAuthoriZationSettinfByAuthSettingId(@Param("idList") List<Integer> idList ,@Param("updateById") int updateById, @Param("authorization") Integer authorization);
	
	/**
	 * @param deleted
	 * @param authSettingId
	 * @return
	 */
	public List<UserCompanyRelation> findByIsDeletedAndAuthorizationSettingAuthSettingId(
			 boolean deleted, int authSettingId);
	
	/**
	 * @param userId
	 * @return
	 */
	@Query("select  DISTINCT c.userGroup.userGroupId from UserCompanyRelation c where c.user.userId=:userId and c.isDeleted=false and c.user.isDeleted=false and c.userGroup.isDeleted=false")
	public Integer getUserGroupByUserId(@Param("userId") int userId);
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("delete from UserCompanyRelation ucr where ucr.company.companyId NOT IN (:idList) and ucr.user.userId=:userId ")
	public void deleteUserCompanyRelation(@Param("idList") List<Integer> idList,@Param("userId") int userId);
	
	@Query("select  DISTINCT c.userGroup.userGroupId from UserCompanyRelation c where "
			+ " c.user.userId=:userId and c.isDeleted=false and c.company.companyId=:companyId and "
			+ " c.user.isDeleted=false and c.userGroup.isDeleted=false ")
	public Integer getUserGroupByUserIdAndCompanyId(@Param("userId") int userId,@Param("companyId") int companyId);

}

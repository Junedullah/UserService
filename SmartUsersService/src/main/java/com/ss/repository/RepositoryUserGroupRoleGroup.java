/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserGroupRoleGroup 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.UserGroupRoleGroup;


@Repository("repositoryUserGroupRoleGroup")
public interface RepositoryUserGroupRoleGroup extends JpaRepository<UserGroupRoleGroup, Integer> {

	/**
	 * @param userGroupId
	 * @param deleted
	 * @return
	 */
	public List<UserGroupRoleGroup> findByUserGroupUserGroupIdAndIsDeleted(int userGroupId, boolean deleted);

	/**
	 * @param id
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update UserGroupRoleGroup ugrg set ugrg.isDeleted = true, ugrg.updatedBy=:updateById where ugrg.userGroup.userGroupId= :id")
	void deleteAllUserGroupRoleGroups(@Param("id") Integer id, @Param("updateById") Integer updateById);

	/**
	 * @param userGroupId
	 * @param roleGroupId
	 * @return
	 */
	public UserGroupRoleGroup findByUserGroupUserGroupIdAndRoleGroupRoleGroupId(int userGroupId, int roleGroupId);

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update UserGroupRoleGroup ugrg set ugrg.isDeleted =:deleted, ugrg.updatedBy=:updateById where ugrg.userGroup.userGroupId IN (:idList)")
	public void deleteAllRoleGroupUserGroupForMultipleUserGroups(@Param("idList") List<Integer> idList,
			@Param("deleted") boolean deleted, @Param("updateById") Integer updateById);

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update UserGroupRoleGroup ugrg set ugrg.isDeleted =:deleted, ugrg.updatedBy=:updateById where ugrg.roleGroup.roleGroupId IN (:idList)")
	public void deleteAllRoleGroupUserGroupForMultipleRoleGroups(@Param("idList") List<Integer> idList,
			@Param("deleted") boolean deleted, @Param("updateById") Integer updateById);

	/**
	 * @param idList
	 * @return
	 */
	@Query("select ugrl from UserGroupRoleGroup ugrl where ugrl.isDeleted=false and ugrl.roleGroup.isDeleted=false and ugrl.userGroup.userGroupId IN(:idList)")
	public List<UserGroupRoleGroup> getRoleGroupsForCompany(@Param("idList") List<Integer> idList);

	/**
	 * @param idList
	 * @return
	 */
	@Query("select count( DISTINCT ugrl.roleGroup.roleGroupId ) from UserGroupRoleGroup ugrl where ugrl.isDeleted=false and ugrl.roleGroup.isDeleted=false and ugrl.userGroup.userGroupId IN(:idList)")
	public Integer getRoleGroupCountForCompany(@Param("idList") List<Integer> idList);

}

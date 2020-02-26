
/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryRoleGroupAccessRole 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
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

import com.ss.model.RoleGroupAccessRole;


@Repository("repositoryRoleGroupAccessRole")
public interface RepositoryRoleGroupAccessRole extends JpaRepository<RoleGroupAccessRole, Integer> {

	/**
	 * @param id
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update RoleGroupAccessRole agar set agar.isDeleted = true, agar.updatedBy=:updateById where agar.roleGroup.roleGroupId= :id")
	void deleteAllRoleGroupAccessRoles(@Param("id") Integer id, @Param("updateById") Integer updateById);

	/**
	 * @param roleId
	 * @param roleGroupId
	 * @return
	 */
	public RoleGroupAccessRole findByAccessRoleAccessRoleIdAndRoleGroupRoleGroupId(Integer roleId, Integer roleGroupId);

	/**
	 * @param roleGroupId
	 * @param deleted
	 * @return
	 */
	public List<RoleGroupAccessRole> findByRoleGroupRoleGroupIdAndIsDeleted(Integer roleGroupId, Boolean deleted);

	/**
	 * @param idList
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update RoleGroupAccessRole agar set agar.isDeleted = true, agar.updatedBy=:updateById where agar.roleGroup.roleGroupId IN (:idList)")
	void deleteAllRoleGroupAccessRolesForMultipleRoleGroups(@Param("idList") List<Integer> idList,
			@Param("updateById") Integer updateById);

	/**
	 * @param idList
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update RoleGroupAccessRole agar set agar.isDeleted = true, agar.updatedBy=:updateById where agar.accessRole.accessRoleId IN (:idList)")
	void deleteAllRoleGroupAccessRolesForMultipleAccessRole(@Param("idList") List<Integer> idList,
			@Param("updateById") Integer updateById);

	/**
	 * @param idList
	 * @return
	 */
	@Query("select count( DISTINCT rgal.accessRole.accessRoleId) from RoleGroupAccessRole rgal where rgal.isDeleted=false and rgal.accessRole.isDeleted=false and rgal.roleGroup.roleGroupId IN (:idList)")
	public Integer getUserGroupCountForCompany(@Param("idList") List<Integer> idList);

}

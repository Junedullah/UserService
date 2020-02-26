/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryAccessRole 
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.AccessRole;


@Repository("repositoryAccessRole")
public interface RepositoryAccessRole extends JpaRepository<AccessRole, Integer> {

	/**
	 * @param deleted
	 * @return
	 */
	public List<AccessRole> findByIsDeleted(Boolean deleted);

	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<AccessRole> findByIsDeleted(Boolean deleted, Pageable pageable);

	/**
	 * @return
	 */
	@Query("select count(*) from AccessRole a where a.isDeleted=false")
	public Integer getCountOfTotalAccessRoles();

	/**
	 * @param id
	 * @param deleted
	 * @return
	 */
	public AccessRole findByAccessRoleIdAndIsDeleted(Integer id, Boolean deleted);

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update AccessRole ar set ar.isDeleted =:deleted, ar.updatedBy=:updateById where ar.accessRoleId IN (:idList)")
	void deleteRoles(@Param("idList") List<Integer> idList, @Param("deleted") Boolean deleted,
			@Param("updateById") Integer updateById);

	/**
	 * @return
	 */
	public AccessRole findTop1ByOrderByAccessRoleIdDesc();

	/**
	 * @param searchKeyWord
	 * @return
	 */
	@Query("select count(*) from AccessRole ar where (ar.roleName like %:searchKeyWord% or ar.roleCode like %:searchKeyWord% or ar.roleDescription like %:searchKeyWord% ) and ar.isDeleted=false")
	public Integer predictiveAccessRoleSearchTotalCount(@Param("searchKeyWord") String searchKeyWord);

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @return
	 */
	@Query("select ar from AccessRole ar where (ar.roleName like %:searchKeyWord% or ar.roleCode like %:searchKeyWord% or ar.roleDescription like %:searchKeyWord% ) and ar.isDeleted=false")
	public List<AccessRole> predictiveAccessRoleSearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
			Pageable pageable);
	
	
	public List<AccessRole> findByIsDeletedOrderByCreatedDateDesc(Boolean deleted);
	
	
	public AccessRole findByRoleNameAndIsDeleted(String accessRoleName, Boolean deleted);
	
	@Query("select ar from AccessRole ar where ar.roleName=:accessRoleName and ar.isDeleted=false and ar.accessRoleId !=:accessRoleId")
	public AccessRole findByRoleNameAndAndIdNotEqual( @Param("accessRoleName") String accessRoleName,@Param("accessRoleId") int accessRoleId);

}

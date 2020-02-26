/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryRoleGroup 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
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

import com.ss.model.RoleGroup;


@Repository("repositoryRoleGroup")
public interface RepositoryRoleGroup extends JpaRepository<RoleGroup, Integer> {

	/**
	 * @param id
	 * @param deleted
	 * @return
	 */
	public RoleGroup findByRoleGroupIdAndIsDeleted(Integer id, Boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	public List<RoleGroup> findByIsDeleted(Boolean deleted);

	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<RoleGroup> findByIsDeleted(Boolean deleted, Pageable pageable);

	/**
	 * @return
	 */
	@Query("select count(*) from RoleGroup r where r.isDeleted=false")
	public Integer getCountOfTotalRoleGroups();

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update RoleGroup rg set rg.isDeleted =:deleted, rg.updatedBy=:updateById where rg.roleGroupId IN (:idList)")
	void deleteMultipleRoleGroups(@Param("idList") List<Integer> idList, @Param("deleted") Boolean deleted,
			@Param("updateById") Integer updateById);

	/**
	 * @return
	 */
	public RoleGroup findTop1ByOrderByRoleGroupIdDesc();

	/**
	 * @param searchKeyWord
	 * @return
	 */
	@Query("select count(*) from RoleGroup rg where (rg.roleGroupName like %:searchKeyWord% or rg.roleGroupCode like %:searchKeyWord% or rg.roleGroupDescription like %:searchKeyWord% ) and rg.isDeleted=false")
	public Integer predictiveRoleGroupSearchTotalCount(@Param("searchKeyWord") String searchKeyWord);

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @return
	 */
	@Query("select rg from RoleGroup rg where (rg.roleGroupName like %:searchKeyWord% or rg.roleGroupCode like %:searchKeyWord% or rg.roleGroupDescription like %:searchKeyWord% ) and rg.isDeleted=false")
	public List<RoleGroup> predictiveRoleGroupSearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
			Pageable pageable);
	
	/**
	 * @param deleted
	 * @return
	 */
	public List<RoleGroup> findByIsDeletedOrderByCreatedDateDesc(Boolean deleted);
	
	
	public RoleGroup findByRoleGroupNameAndIsDeleted(String roleGroupName, Boolean deleted);
	
	
	@Query("select rg from RoleGroup rg where  rg.isDeleted=false and rg.roleGroupName=:roleGroupName and rg.roleGroupId !=:roleGroupId ")
	public RoleGroup getByRoleGroupNameAndIdNotEqual(@Param("roleGroupName") String roleGroupName , @Param("roleGroupId") int roleGroupId);

}

/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserGroup 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
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

import com.ss.model.UserGroup;


@Repository("repositoryUserGroup")
public interface RepositoryUserGroup extends JpaRepository<UserGroup, Integer> {
	/**
	 * @param groupId
	 * @param deleted
	 * @return
	 */
	public UserGroup findByUserGroupIdAndIsDeleted(int groupId, boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	public List<UserGroup> findByIsDeleted(Boolean deleted);

	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<UserGroup> findByIsDeleted(Boolean deleted, Pageable pageable);

	/**
	 * @return
	 */
	@Query("select count(*) from UserGroup u where u.isDeleted=false")
	public Integer getCountOfTotalUserGroup();

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Query("update UserGroup g set g.isDeleted =:deleted, g.updatedBy=:updateById where g.userGroupId IN (:idList)")
	void deleteUserGroups(@Param("idList") List<Integer> idList, @Param("deleted") boolean deleted,
			@Param("updateById") Integer updateById);

	/**
	 * @return
	 */
	public UserGroup findTop1ByOrderByUserGroupIdDesc();

	/**
	 * @param searchKeyWord
	 * @return
	 */
	@Query("select count(*) from UserGroup ug where (ug.groupName like %:searchKeyWord% or ug.groupCode like %:searchKeyWord% or ug.groupDesc like %:searchKeyWord% ) and ug.isDeleted=false")
	public Integer predictiveUserGroupSearchTotalCount(@Param("searchKeyWord") String searchKeyWord);

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @return
	 */
	
	@Query("select ug from UserGroup ug where (ug.groupName like %:searchKeyWord% or ug.groupCode like %:searchKeyWord% or ug.groupDesc like %:searchKeyWord% ) and ug.isDeleted=false")
	public List<UserGroup> predictiveUserGroupSearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
			Pageable pageable);
	
	/**
	 * @param deleted
	 * @return
	 */
	public List<UserGroup> findByIsDeletedOrderByCreatedDateDesc(Boolean deleted);
	
	/**
	 * @param groupName
	 * @param deleted
	 * @return
	 */
	@Query("select ug from UserGroup ug where  ug.isDeleted=false and ug.groupName=:groupName and ug.userGroupId != :userGroupId")
	public UserGroup findByGroupNameAndDeleted(@Param("groupName") String groupName,@Param("userGroupId") int userGroupId);
	
	public UserGroup findByGroupNameAndIsDeleted(String groupName, boolean deleted);
	
}
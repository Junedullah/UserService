/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserDetail 
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 4:30:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.UserDetail;



@Repository("repositoryUserDetail")
public interface RepositoryUserDetail extends JpaRepository<UserDetail, Integer> {

	/**
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public UserDetail findByUserUserIdAndIsDeleted(int userId, boolean deleted);

	/**
	 * @param userId
	 * @return
	 */
	public UserDetail findByUserUserId(int userId);

	/**
	 * @param deleted
	 * @return
	 */
	List<UserDetail> findByIsDeletedOrderByUdIdDesc(Boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	List<UserDetail> findByIsDeleted(Boolean deleted);
	
	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	Page<UserDetail> findByIsDeletedOrderByUdIdDesc(Boolean deleted, Pageable pageable);

	/**
	 * @return
	 */
	public UserDetail findTop1ByOrderByUdIdDesc();

	/**
	 * @param idList
	 * @return
	 */
	@Query("SELECT ud FROM UserDetail ud WHERE ud.user.userId IN (:idList)")
	List<UserDetail> searcchUsers(@Param("idList") List<Integer> idList);

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @param roleId
	 * @return
	 */
	@Query("select ud from UserDetail ud where (ud.firstName like %:searchKeyWord% or ud.lastName like %:searchKeyWord% or ud.middleName like %:searchKeyWord% or ud.email like %:searchKeyWord% or ud.phone like %:searchKeyWord% or ud.secondaryFirstName like %:searchKeyWord% or ud.secondaryLastName like %:searchKeyWord% or ud.secondaryMiddleName like %:searchKeyWord% ) and ud.isDeleted=false and  ud.user.role.roleId=:roleId")
	public List<UserDetail> predictiveUserSearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
			Pageable pageable, @Param("roleId") Integer roleId);

	/**
	 * @param searchKeyWord
	 * @param roleId
	 * @return
	 */
	@Query("select count(*) from UserDetail ud where (ud.firstName like %:searchKeyWord% or ud.lastName like %:searchKeyWord% or ud.middleName like %:searchKeyWord% or ud.email like %:searchKeyWord% or ud.phone like %:searchKeyWord% or ud.secondaryFirstName like %:searchKeyWord% or ud.secondaryLastName like %:searchKeyWord% or ud.secondaryMiddleName like %:searchKeyWord% ) and ud.isDeleted=false and ud.user.role.roleId=:roleId")
	public Integer predictiveUserSearchTotalCount(@Param("searchKeyWord") String searchKeyWord,
			@Param("roleId") Integer roleId);

}
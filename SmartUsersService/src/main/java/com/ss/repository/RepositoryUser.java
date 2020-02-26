/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUser 
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.User;


@Repository("repositoryUser")
public interface RepositoryUser extends JpaRepository<User, Integer> {

	/**
	 * @param userName
	 * @param deleted
	 * @return
	 */
	public User findByusernameAndIsDeleted(String userName, boolean deleted);

	/**
	 * @param userName
	 * @return
	 */
	public User findByusername(String userName);

	/**
	 * @param password
	 * @return
	 */
	public User findByPassword(String password);

	/**
	 * @param userId
	 * @return
	 */
	public User findByUserId(int userId);

	/**
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public User findByUserIdAndIsDeleted(int userId, Boolean deleted);

	/**
	 * @param delete
	 * @return
	 */
	public List<User> findByIsDeleted(Boolean delete);

	/**
	 * @param roleId
	 * @param delete
	 * @return
	 */
	public List<User> findByRoleRoleIdAndIsDeleted(int roleId, Boolean delete);

	/**
	 * @param roleId
	 * @param delete
	 * @param pageable
	 * @return
	 */
	public List<User> findByRoleRoleIdAndIsDeleted(int roleId, Boolean delete, Pageable pageable);

	/**
	 * @param email
	 * @param deleted
	 * @return
	 */
	public User findByEmailAndIsDeleted(String email, boolean deleted);

	/**
	 * @param username
	 * @param password
	 * @param deleted
	 * @return
	 */
	public User findByUsernameAndPasswordAndIsDeleted(String username, String password, boolean deleted);

	/**
	 * @param deleted
	 * @param email
	 * @return
	 */
	public List<User> findByIsDeletedAndEmail( boolean deleted, String email);


	/**
	 * @return
	 */
	public User findTop1ByOrderByUserIdDesc();

	/**
	 * @return
	 */
	@Query("select count(*) from User u where u.isDeleted=false")
	public Integer getCountOfTotalUsers();

	/**
	 * @param delete
	 * @param pageable
	 * @return
	 */
	public List<User> findByIsDeleted(Boolean delete, Pageable pageable);

	/**
	 * @param roleId
	 * @param delete
	 * @param isActive
	 * @return
	 */
	public List<User> findByRoleRoleIdAndIsDeletedAndIsActive(int roleId, Boolean delete, Boolean isActive);

	/**
	 * @param roleId
	 * @param delete
	 * @param pageable
	 * @param isActive
	 * @return
	 */
	public List<User> findByRoleRoleIdAndIsDeletedAndIsActive(int roleId, Boolean delete, Pageable pageable,Boolean isActive);

	/**
	 * @return
	 */
	@Query("select count(*) from User u where u.isDeleted=false and u.isActive=true")
	public Integer getCountOfTotalUsersIsActive();
	
 List<User> findByRoleRoleIdAndIsDeletedOrderByCreatedDateDesc(int roleId, Boolean delete);

}
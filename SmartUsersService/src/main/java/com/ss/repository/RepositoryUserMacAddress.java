/**
 * SST - SMART SOFTWARE TECH.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.repository;

import com.ss.model.UserMacAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Description: Interface for RepositoryUserIp 
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Repository("repositoryUserMacAddress")
public interface RepositoryUserMacAddress extends JpaRepository<UserMacAddress, Integer> {
	
	public List<UserMacAddress> findByIsDeleted(Boolean isDeleted);
	
	public List<UserMacAddress> findByMacAddressAndUserUserIdAndIsActiveAndIsDeleted(String macAddress, int userId,
                                                                                     Boolean isActive, Boolean isDeleted);

	public List<UserMacAddress> findByMacAddressAndIsActiveAndIsDeleted(String macAddress, Boolean isActive, Boolean isDeleted);

	public UserMacAddress findByMacAddressAndUserUserId(String macAddress, int userId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update UserMacAddress u set u.isDeleted =:deleted, u.updatedBy=:updateById where u.user.userId=:userId")
	public void deleteUserMacAddressByUserId(@Param("deleted") Boolean deleted, @Param("userId") Integer userId,
                                             @Param("updateById") Integer updateById);
	
}

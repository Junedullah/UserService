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

import com.ss.model.LoginOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Description: Interface for RepositoryLoginOtp
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */

@Repository("repositoryLoginOtp")
public interface RepositoryLoginOtp extends JpaRepository<LoginOtp,Integer> {
	
	/**
	 * @param code
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public LoginOtp findByCodeAndUserUserIdAndIsDeleted(String code, int userId, boolean deleted);

	/**
	 * @param userId
	 * @param deleted
	 * @return
	 */
	public LoginOtp findByUserUserIdAndIsDeleted(int userId, boolean deleted);

	/**
	 * @param userId
	 * @return
	 */
	public LoginOtp findByUserUserId(int userId);

}
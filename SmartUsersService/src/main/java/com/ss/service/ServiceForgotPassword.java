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
package com.ss.service;

import com.ss.model.User;
import com.ss.repository.RepositoryUser;
import com.ss.util.UtilRandomKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Description: Service Forgot Password
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Service(value = "serviceForgotPassword")
public class ServiceForgotPassword {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ServiceForgotPassword.class);

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	 /**
	 * Description: Reset password and send email for the same
	 * @param email
	 * @return
	 */
	public boolean resetPasswordAndSendEmail(User userExist) {
		if (userExist != null && userExist.getEmail() != null) {
			String uniqueRandomKey = getUniQueRandomKeyByCompareUserPasswords();
			userExist.setPassword(passwordEncoder.encode(uniqueRandomKey));
			
			repositoryUser.saveAndFlush(userExist);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getUniQueRandomKeyByCompareUserPasswords() {
		String randomKey = new UtilRandomKey().nextRandomKey();
		User user = repositoryUser.findByPassword(randomKey);
		if (user == null) {
			return randomKey;
		} else {
			getUniQueRandomKeyByCompareUserPasswords();
			return null;
		}
	}
}

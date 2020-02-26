/**SmartSoftware User - Service */
/**
 * Description: ServiceAuthentication
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ss.repository.RepositoryUser;

@Service
public class ServiceAuthentication implements UserDetailsService {

	static Logger log = Logger.getLogger(ServiceAuthentication.class.getName());

	@Autowired
	RepositoryUser repositoryUser;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		com.ss.model.User userTo = repositoryUser.findByusername(username);
		if (userTo == null)
			return null;

		GrantedAuthority authority = new SimpleGrantedAuthority("SUPERADMIN");
		 
		return new User(userTo.getUserId() + "", userTo.getPassword(),
				Arrays.asList(authority));
	}

}
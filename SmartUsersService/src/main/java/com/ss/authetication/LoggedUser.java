/**SmartSoftware User - Service */
/**
 * Description: LoggedUser
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.authetication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class LoggedUser extends User {

	private static final long serialVersionUID = -4277741497117352526L;

	long userId;

	/**
	 * @param userId
	 * @param username
	 * @param password
	 * @param enabled
	 * @param accountNonExpired
	 * @param credentialsNonExpired
	 * @param accountNonLocked
	 * @param authorities
	 */
	public LoggedUser(long userId, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
	}

	/**
	 * @return
	 */
	public long getUserId() {
		return userId;
	}

}

/**SmartSoftware User - Service */
/**
 * Description: UserDetailsServiceImpl
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.authetication;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.User;
import com.ss.repository.RepositoryUser;



@SuppressWarnings("deprecation")
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	RepositoryUser repositoryUser;

	private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

	/** (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username){
		LOGGER.info("inside user detail service, username = " + username);
		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("user not found");
		}
		User user = repositoryUser.findByusername(username);
		if (user == null)
			throw new UsernameNotFoundException("user not found");
		return buildUserFromUserEntity(user);
	}
	 
	/**
	 * @param user
	 * @return
	 */
	private LoggedUser buildUserFromUserEntity(User user) {
		long userId = user.getUserId();
		String username = Long.toString(userId);
		String password = user.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl(user.getRole().getRoleName()));
		return new LoggedUser(userId, username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	 
	}
}

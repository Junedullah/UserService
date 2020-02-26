/**SmartSoftware User - Service */
/**
 * Description: SmartLogoutSuccessHandler
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.authetication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.ss.repository.RepositoryUserSession;

public class SmartLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	RepositoryUserSession repositoryUserSession;

	 /**(non-Javadoc)
	  @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
	}

}

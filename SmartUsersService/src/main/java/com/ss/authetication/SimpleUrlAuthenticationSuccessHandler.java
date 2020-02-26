/**SmartSoftware User - Service *//*
*//**
 * Description: Simple Url Authentication SuccessHandler
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 *//*
package com.ss.authetication;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.ss.constant.SmartRoles;
import com.ss.model.LoginOtp;
import com.ss.model.UserSession;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserSession;
import com.ss.util.UtilFindIPAddress;
import com.ss.util.UtilRandomKey;


public class SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	RepositoryUser repositoryUser;
	@Autowired
	RepositoryUserSession repositoryUserSession;
	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;
	@Autowired
	RepositoryWhiteListIp repositoryWhiteListIp;
 */
	/** (non-Javadoc)
	  @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	*/ 
	/*@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}
    
	*//**
	 * it checks if user ip is allowed
	 * @param request
	 * @return
	 *//*
	@SuppressWarnings("unused")
	private boolean allowedIpRequest(HttpServletRequest request) 
	{
		boolean result = false;
		String userIpAddress = UtilFindIPAddress.getUserIp(request);
		List<WhitelistIp> whitelistIp = repositoryWhiteListIp.findByIsDeleted(false);
		if (whitelistIp!=null && !whitelistIp.isEmpty()) {
			for (WhitelistIp whitelistIp2 : whitelistIp) {
				if (whitelistIp2.getIpAddress().equals(userIpAddress)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	*//**
     * it saves session of logged user in userSession table
     * @param request
     *//*
	@Transactional
	public int createUserSession(HttpServletRequest request) {
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.smartsoft.model.User user = repositoryUser.findByUserId(Integer.parseInt(authUser.getUsername()));
		int userId = 0;
		if (user != null) {
			userId = user.getUserId();
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			 
				UserSession userSession = new UserSession();
				userSession.setIsDeleted(false);
				userSession.setCreatedDate(new Date());
				userSession.setUpdatedDate(new Date());
				repositoryUserSession.save(userSession);
		 
		} else {
			request.getSession().invalidate();
			throw new UsernameNotFoundException("user not found");
		}
		return userId;
	}
 
    *//**
     * it sends otp to user for authentication
     * @param userId
     *//*
	@Transactional
	public void sendOTPtoUserForAuthentication(int userId) 
	{
		LoginOtp loginSavedOtp = repositoryLoginOtp.findByUserUserId(userId);
		if (loginSavedOtp == null) 
		{
			LoginOtp loginOtp = new LoginOtp();
			loginOtp.setCode(UtilRandomKey.getRandomOrderNumber());
			loginOtp.setUser(repositoryUser.findByUserId(userId));
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			loginOtp.setExpireDate(new Timestamp(cal.getTime().getTime()));
			loginOtp.setIsDeleted(false);
			loginOtp.setUpdatedDate(new Date());
			loginOtp.setIsUsed("N");
			loginOtp.setCreatedDate(new Date());
			loginOtp.setAttempts(0);
			repositoryLoginOtp.save(loginOtp);
		}
		else 
		{
			loginSavedOtp.setCode(UtilRandomKey.getRandomOrderNumber());
			loginSavedOtp.setUpdatedDate(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			loginSavedOtp.setExpireDate(new Timestamp(cal.getTime().getTime()));
			loginSavedOtp.setIsDeleted(false);
			loginSavedOtp.setIsUsed("N");
			loginSavedOtp.setAttempts(0);
			repositoryLoginOtp.save(loginSavedOtp);
		}
	}

	*//**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     *//*
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication, request);
		if (response.isCommitted()) {
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
 
    *//** Builds the target URL according to the logic defined in the main class Javadoc. *//*
    *//**
     * @param authentication
     * @param request
     * @return
     *//*
	protected String determineTargetUrl(Authentication authentication, HttpServletRequest request) {
		String url = request.getHeader("Referer");
		boolean isSuperAdmin = false;
		boolean isUser = false;
		HttpSession session = request.getSession();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {

			if (grantedAuthority.getAuthority().equals(SmartRoles.SUPERADMIN.name())) {
				isSuperAdmin = true;
				session.setAttribute("userRole", SmartRoles.SUPERADMIN.name());
				break;
			}
			if (grantedAuthority.getAuthority().equals(SmartRoles.USER.name())) {
				isUser = true;
				session.setAttribute("userRole", SmartRoles.USER.name());
				break;
			}
		}
		if (session.getAttribute("ipNotAllowed") != null && session.getAttribute("ipNotAllowed").equals(true)) {
			return "/login";
		}
		if (isSuperAdmin) {
			return "/otpAuthentication";
		} else if (isUser) {
			return "/otpAuthentication";
		}
		else {
			 
			session.invalidate();
			session = request.getSession();
			session.setAttribute("loginFail", true);
			return url;
		}
	}
 
    *//**
     * @param request
     *//*
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
 
    *//**
     * @param redirectStrategy
     *//*
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	*//**
	 * @return
	 *//*
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	
	
}*/
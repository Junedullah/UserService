/**
 *  SMART SOFTWARE - user.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ss.constant.SmartRoles;
import com.ss.model.LoginOtp;
import com.ss.model.dto.DtoUserLogin;
import com.ss.repository.RepositoryLoginOtp;

/**
 * Description: Service Login Otp
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Service("serviceLoginOtp")
public class ServiceLoginOtp {


	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;
	
	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	/**
	 * Description: Verification for the user OTP
	 * @param request
	 * @param userId 
	 * @param userRole 
	 * @param otp 
	 * @param modelAndView 
	 * @param session 
	 * @return
	 */
	public ModelAndView verifyUserOtp(HttpServletRequest request, String userRole, Integer userId, String otp,
			ModelAndView modelAndView, HttpSession session) {
		LoginOtp loginOtp = repositoryLoginOtp.findByUserUserIdAndIsDeleted(userId, false);
		if (loginOtp != null) {
			LoginOtp loginVerifiedOtp = repositoryLoginOtp.findByCodeAndUserUserIdAndIsDeleted(otp, userId, false);
			if (loginVerifiedOtp != null) {
				Date expiredDate = loginVerifiedOtp.getExpireDate();
				if (expiredDate.before(new Date())) {
					session.setAttribute("OTPexpired", true);
					session.removeAttribute("userRole");
					session.removeAttribute("userId");
					modelAndView.setViewName("redirect:/login");
					return modelAndView;
				}
				if (userRole.equals(SmartRoles.USER.name())) {
					modelAndView.setViewName("redirect:/user/dashboard");
				} else if (userRole.equals(SmartRoles.SUPERADMIN.name())) {
					modelAndView.setViewName("redirect:/admin/dashboard");
				}
				loginVerifiedOtp.setIsUsed("Y");
				loginVerifiedOtp.setIsDeleted(true);
				repositoryLoginOtp.save(loginVerifiedOtp);
			} else {
				//attempt failed
				int previousAttempts = loginOtp.getAttempts();
				if (previousAttempts < 2) {
					session.setAttribute("invalidOtp", true);
					loginOtp.setAttempts(++previousAttempts);
					repositoryLoginOtp.save(loginOtp);
					modelAndView.setViewName("redirect:/otpAuthentication");
				} else {
					session.setAttribute("otpMaxLimitReached", true);
					session.removeAttribute("userRole");
					session.removeAttribute("userId");
					modelAndView.setViewName("redirect:/login");
				}
			}
		} else {
			session.removeAttribute("userRole");
			session.removeAttribute("userId");
			modelAndView.setViewName("redirect:/login");
		}
		return modelAndView;
	}

	/**
	 * Description: Validate user OTP
	 * @param dtoLoginUser
	 * @return
	 */
	public DtoUserLogin validateUserOTP(DtoUserLogin dtoLoginUser) {
		LoginOtp loginOtp = repositoryLoginOtp.findByUserUserIdAndIsDeleted(dtoLoginUser.getUserId(), false);
		if (loginOtp != null) {
			/*if (loginOtp.getUser().isResetPassword()) {
				dtoLoginUser.setIsResetPassword("Y");
			} else {
				dtoLoginUser.setIsResetPassword("N");
			}*/

			if (loginOtp.getCode().equals(dtoLoginUser.getOtp())) {
				Date expiredDate = loginOtp.getExpireDate();

				if (expiredDate.before(new Date())) {
					dtoLoginUser.setMessageType("USER_OTP_EXPIRED");
				}
				if (loginOtp.getIsUsed().equalsIgnoreCase("Y")) {
					dtoLoginUser.setMessageType("USER_OTP_EXPIRED");
				}
				loginOtp.setIsUsed("Y");
				loginOtp.setIsDeleted(true);
				repositoryLoginOtp.save(loginOtp);
				dtoLoginUser.setDeleted(true);
				dtoLoginUser.setOtpMatched(true);
			} else {
				//attempt failed
				int previousAttempts = loginOtp.getAttempts();
				int newAttempts = previousAttempts + 1;
				if (previousAttempts < 2) {
					dtoLoginUser.setWrongAttempts(newAttempts);
					dtoLoginUser.setOtpMatched(false);
					loginOtp.setAttempts(newAttempts);
					repositoryLoginOtp.save(loginOtp);
				} else {
					newAttempts = 3;
					dtoLoginUser.setWrongAttempts(newAttempts);
					dtoLoginUser.setOtpMaxLimitReached(true);
					loginOtp.setAttempts(newAttempts);
					loginOtp.setIsUsed("Y");
					loginOtp.setIsDeleted(true);
					repositoryLoginOtp.save(loginOtp);
				}
			}
		} else {
			dtoLoginUser.setMessageType("INVALID_OTP");
		}
		return dtoLoginUser;
	}

}

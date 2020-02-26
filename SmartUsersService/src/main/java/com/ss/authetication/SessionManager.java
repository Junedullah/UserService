/**SmartSoftware User - Service */
/**
 * Description: SessionManager
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.authetication;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.constant.SmartRoles;
import com.ss.model.Company;
import com.ss.model.User;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoUser;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserSession;
import com.ss.util.UtilRandomKey;


@Component(value = "sessionManager")
public class SessionManager {

	@Autowired
	RepositoryUserSession repositoryUserSession;

	@Autowired
	RepositoryUser repositoryUser;
	
	@Autowired
	RepositoryCompany repositoryCompany;
	
	/*@Autowired
	ServiceLogin serviceLogin;
	
	@Autowired
	RepositoryUserDraft repositoryUserDraft;
	*/
	@Autowired (required=false)
	HttpServletRequest httpRequest;
	
	private static final Logger LOGGER = Logger.getLogger(SessionManager.class);
	
	/** 
	 * Description: get Unique SessionId
	 * @param userId
	 */
	public String getUniqueSessionId(Integer userId,String localIPAddress) {
		LOGGER.info("inside getUniqueSessionId method");
		String sessionId = UtilRandomKey.generateSessionKey();
		LOGGER.info(sessionId + ":sessionId");
		UserSession session = repositoryUserSession.findBySessionAndIsDeleted(sessionId, false);
		if(session != null) {
			getUniqueSessionId(userId,localIPAddress);
		}
		
		session = repositoryUserSession.findByUserUserIdAndIsDeleted(userId, false);
		if (session == null) {
			LOGGER.info("SessionLog is null as user id does not exist");
			session = new UserSession();
		}
		
		session.setUser(repositoryUser.findByUserId(userId));
		session.setCreatedDate(new Date());
		session.setUpdatedDate(new Date());
		session.setExpireDate(addOneDay(new Date()));
		session.setSession(sessionId);
		session.setIsDeleted(false);
		session.setUserIpAddress(localIPAddress);
		repositoryUserSession.save(session);
		return sessionId;
	}
	  
	/**
	 * @param date
	 * @return new date plus one day increment
	 */
	public Timestamp addOneDay(Date oldDate) {
		long timeadj = 24 * 60 * 60 * 1000;
		Date newDate = new Date(oldDate.getTime() + timeadj);
		return new Timestamp(newDate.getTime());
	}

	/**
	 * @param idCurrentUser 
	 * @param request
	 * @return
	 */
public UserSession validateUserSessionId(HttpServletRequest request) {
		String sessionId = getSessionId(request);
		int idCurrentUser = getUserId(request);
		String companyTenantId=getTenantId(request);
		LOGGER.info(sessionId + ":sessionId of request");
		if (sessionId == null) {
			return null;
		}
		UserSession session = repositoryUserSession.findByUserUserIdAndSessionAndIsDeleted(idCurrentUser, sessionId,
				false);
		if (session != null) 
		{
			if(session.getUser().getRole().getRoleId()==SmartRoles.SUPERADMIN.getIndex()){
				return session;
			}
		
			else
			{
				
			Company company=repositoryCompany.findByTenantIdAndIsDeleted(companyTenantId,false);
			//Boolean flag= serviceLogin.checkValidCompanyAccess(company.getCompanyId(), idCurrentUser);			
			Boolean	flag=true;
				if(flag){
					return session;
				}
				else
				{
					return null;
				}
			}
		} else {
			return null;
		}
	}
	
	/**
	 * @param idCurrentUser 
	 * @param request
	 * @return
	 */
	public UserSession validateUserSessionIdBeforeCompanySelection(HttpServletRequest request) {
		String sessionId = getSessionId(request);
		int idCurrentUser = getUserId(request);
		LOGGER.info(sessionId + ":sessionId of request");
		if (sessionId == null) {
			return null;
		}
		UserSession session = repositoryUserSession.findByUserUserIdAndSessionAndIsDeleted(idCurrentUser, sessionId,
				false);
		if (session != null) 
		{
			return session;
		} else {
			return null;
		}
	}
	/**
	 * @param request
	 * @return
	 */
	public String getSessionId(HttpServletRequest request) {
		return request.getHeader("session");
	}

	/**
	 * @param request
	 * @return
	 */
	public int getUserId(HttpServletRequest request) {
		return Integer.parseInt(request.getHeader("userid"));
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	public String getTenantId(HttpServletRequest request) {
		return request.getHeader("tenantid");
	}

	/**
	 * @param userId
	 * @return
	 */
	/*public boolean clearSessionLog(Integer userId) {
		UserSession session = repositoryUserSession.findByUserUserIdAndIsDeleted(userId, false);
		if (session != null) 
		{
			User user = session.getUser();
			if(user.getRole().getRoleName().equalsIgnoreCase(SmartRoles.SUPERADMIN.name()))
			{
				List<UserDraft> userDraftList= repositoryUserDraft.findByUserUserId(user.getUserId());
				if(userDraftList!=null && !userDraftList.isEmpty()){
					repositoryUserDraft.deleteInBatch(userDraftList);
				}
			}
			else if(user.getRole().getRoleName().equalsIgnoreCase(SmartRoles.USER.name()))
			{
				String tenantId = session.getCompnayTenantId();
				Company company=repositoryCompany.findByTenantIdAndIsDeleted(tenantId, false);
				if(company!=null){
					List<UserDraft> userDraftList= repositoryUserDraft.findByUserUserIdAndCompanyCompanyId(user.getUserId(),company.getCompanyId());
					if(userDraftList!=null && !userDraftList.isEmpty()){
						repositoryUserDraft.deleteInBatch(userDraftList);
					}
				}
			}
			repositoryUserSession.delete(session);
			return true;
		} else {
			return false;
		}
	}*/

	/**
	 * @param dtoUser
	 * @return
	 */
	public Boolean validateUserSession(DtoUser dtoUser) {
		boolean flag = false;
		User user = repositoryUser.findByusernameAndIsDeleted(dtoUser.getUserName(), false);
		UserSession session = repositoryUserSession.findByUserUserIdAndIsDeleted(user.getUserId(), false);
		if (session != null) {
			if (session.getExpireDate().before(new Date())) {
				repositoryUserSession.delete(session);
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = true;
		}

		return flag;
	}
	
	/**
	 * @param dtoUser
	 * @return
	 */
	public Boolean validateUserSessionExistOrNotByIp(DtoUser dtoUser) {
		boolean flag = false;
		User user = repositoryUser.findByusernameAndIsDeleted(dtoUser.getUserName(), false);
		UserSession session = repositoryUserSession.findByUserUserIdAndIsDeleted(user.getUserId(), false);
		if (session != null) 
		{
			if (session.getExpireDate().before(new Date())) {
				repositoryUserSession.delete(session);
				flag = true;
			} 
			else 
			{
				String ipAddress= dtoUser.getIpAddress();
				if(ipAddress!=null && session.getUserIpAddress()!=null &&  ipAddress.equalsIgnoreCase(session.getUserIpAddress())){
					repositoryUserSession.delete(session);
					return true;
				}
				else
				{
					flag = false;
				}
			}
		} 
		else 
		{
			flag = true;
		}
		return flag;
	}
}
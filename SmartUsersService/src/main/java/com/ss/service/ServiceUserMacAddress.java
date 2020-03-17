
/**SmartSoftware User - Service */
/**
 * Description: Service UserIp
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
package com.ss.service;

import com.ss.model.WhitelistIp;
import com.ss.model.User;
import com.ss.model.UserMacAddress;
import com.ss.model.WhitelistIp;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserMacAddress;
import com.ss.repository.RepositoryWhiteListIp;
import com.ss.util.UtilFindIPAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Service("serviceUserIp")
@Transactional
public class ServiceUserMacAddress {

	@Autowired
	RepositoryWhiteListIp repositoryWhiteListIp;

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	RepositoryUserMacAddress repositoryUserMacAddress;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	/**
	 * @param request
	 * @param userName
	 * @return
	 */
	public boolean checkAllowedUserIpRequest(HttpServletRequest request, String userName) 
	{
		String userIpAddress = UtilFindIPAddress.getUserIp(request);
		String macAddress = httpServletRequest.getHeader("Mac-Address");
		User user = repositoryUser.findByusernameAndIsDeleted(userName, false);
		List<WhitelistIp> whitelistIp;
		whitelistIp = repositoryWhiteListIp.findByIpAddressAndIsActiveAndIsDeleted(userIpAddress, true, false);
		
		if (whitelistIp != null && !whitelistIp.isEmpty()) 
		{
			return true;
		} 
		else 
		{
			if (user != null) {
				List<UserMacAddress> userIpList = repositoryUserMacAddress.findByMacAddressAndUserUserIdAndIsActiveAndIsDeleted(
						macAddress, user.getUserId(), true, false);
				if (userIpList != null && !userIpList.isEmpty()) {
					return true;
				}  
			} 
			return false;
		}
	}

	/**
	 * @param userIp
	 * @return
	 */
	public boolean allowedIpRequest(String userIp) 
	{
		String macAddress = httpServletRequest.getHeader("Mac-Address");
		List<WhitelistIp> whitelistIpList = repositoryWhiteListIp.findByIpAddressAndIsActiveAndIsDeleted(userIp, true,false);
		if (whitelistIpList != null && !whitelistIpList.isEmpty()) {
			return true;
		} else {
			List<UserMacAddress> userIpList = repositoryUserMacAddress.findByMacAddressAndIsActiveAndIsDeleted(macAddress, true, false);
			return userIpList != null && !userIpList.isEmpty();
		}
	}
	
}

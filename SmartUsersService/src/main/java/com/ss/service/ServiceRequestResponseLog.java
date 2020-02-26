/**SmartSoftware User - Service */
/**
 * Description: ServiceRequestResponseLog
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.RequestResponseLog;
import com.ss.model.dto.DtoRequestResponseLog;
import com.ss.repository.RepositoryRequestResponseLog;


@Service("ServiceRequestResponseLog")
public class ServiceRequestResponseLog {

	private static final Logger LOGGER = Logger.getLogger(ServiceRequestResponseLog.class);


	@Autowired
	RepositoryRequestResponseLog repositoryRequestResponseLog;



	/**
	 * Description: search role group by search keyword
	 * @param dtoSearch
	 * @return
	 */
	@Transactional
	public void logRequest(DtoRequestResponseLog dtoRequestResponseLog) {
		
		RequestResponseLog requestResponseLog = new RequestResponseLog();
		
		requestResponseLog.setRequestId(dtoRequestResponseLog.getRequestId());
		requestResponseLog.setHost(dtoRequestResponseLog.getHost());
		requestResponseLog.setLangId(dtoRequestResponseLog.getLangId());
		requestResponseLog.setOrigin(dtoRequestResponseLog.getOrigin());
		requestResponseLog.setReferer(dtoRequestResponseLog.getReferer());
		requestResponseLog.setSession(dtoRequestResponseLog.getSession());
		requestResponseLog.setTenantId(dtoRequestResponseLog.getTenantId());
		requestResponseLog.setUserId(dtoRequestResponseLog.getUserId());
		requestResponseLog.setRequestJson(dtoRequestResponseLog.getRequestJson());
		requestResponseLog.setCreated(dtoRequestResponseLog.getCreated());
		
		repositoryRequestResponseLog.saveAndFlush(requestResponseLog);
	}
	
	@Transactional
	public void logResponse(DtoRequestResponseLog dtoRequestResponseLog) {
		
		boolean isSuccess = false;
		long rows = 0;
		try {
			rows = this.repositoryRequestResponseLog.logResponse(dtoRequestResponseLog.getResponseJson(), dtoRequestResponseLog.getUpdated(), dtoRequestResponseLog.getRequestId());
		} catch (Exception ex) {
			isSuccess = false;
		}
		
		if (rows <= 0 || !isSuccess) {
			
			RequestResponseLog requestResponseLog = new RequestResponseLog();
			
			requestResponseLog.setRequestId(dtoRequestResponseLog.getRequestId());
			requestResponseLog.setHost(dtoRequestResponseLog.getHost());
			requestResponseLog.setLangId(dtoRequestResponseLog.getLangId());
			requestResponseLog.setOrigin(dtoRequestResponseLog.getOrigin());
			requestResponseLog.setReferer(dtoRequestResponseLog.getReferer());
			requestResponseLog.setSession(dtoRequestResponseLog.getSession());
			requestResponseLog.setTenantId(dtoRequestResponseLog.getTenantId());
			requestResponseLog.setUserId(dtoRequestResponseLog.getUserId());
			requestResponseLog.setRequestJson(dtoRequestResponseLog.getRequestJson());
			requestResponseLog.setCreated(dtoRequestResponseLog.getCreated());

			requestResponseLog.setResponseJson(dtoRequestResponseLog.getResponseJson());
			requestResponseLog.setUpdated(dtoRequestResponseLog.getUpdated());
			
			this.repositoryRequestResponseLog.saveAndFlush(requestResponseLog);
			
		}
	}
	

}

package com.ss.util;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.model.dto.DtoRequestResponseLog;
import com.ss.service.ServiceRequestResponseLog;

public class RequestResponseLogger {

	static RequestResponseLogger requestResponseLogger = null;
	
	private RequestResponseLogger() {
		
	}
	
	public static RequestResponseLogger getInstance() {
		if (requestResponseLogger == null) {
			requestResponseLogger = new RequestResponseLogger();
		}
		return requestResponseLogger;
	}
	
	static Logger LOGGER = Logger.getLogger("requestResponseLog");
	
	@Autowired
	ServiceRequestResponseLog service;	

	public static DtoRequestResponseLog logRequest(HttpServletRequest request, Object dtoObject) {
		DtoRequestResponseLog dtoRequestResponseLog = new DtoRequestResponseLog();

		try {
			
			System.out.println("request.toString(): " + request.toString());
			
			String Host = request.getHeader("Host");
			String langid = request.getHeader("langid");
			String Origin = request.getHeader("Origin");
			String Referer = request.getHeader("Referer");
			String session = request.getHeader("session");
			String tenantid = request.getHeader("tenantid");
			String userid = request.getHeader("userid");
			
			System.out.println("Host: " + Host);
			System.out.println("langid: " + langid);
			System.out.println("Origin: " + Origin);
			System.out.println("Referer: " + Referer);
			System.out.println("session: " + session);
			System.out.println("tenantid: " + tenantid);
			System.out.println("userid: " + userid);
			
			
			ObjectMapper om = new ObjectMapper();
			
			String json = om.writeValueAsString(dtoObject);
			
			System.out.println("dto JSON: " + json);
			
			String requestId = session + ":" + userid + ":" + new Date().getTime();
			dtoRequestResponseLog.setRequestId(requestId);
			dtoRequestResponseLog.setLangId(CommonUtils.parseInteger(langid));
			dtoRequestResponseLog.setOrigin(Origin);
			dtoRequestResponseLog.setReferer(Referer);
			dtoRequestResponseLog.setSession(session);
			dtoRequestResponseLog.setTenantId(tenantid);
			dtoRequestResponseLog.setUserId(CommonUtils.parseInteger(userid));
			dtoRequestResponseLog.setRequestJson(json);
			dtoRequestResponseLog.setCreated(new Date());
				
			LOGGER.debug(dtoRequestResponseLog);
//			service.logRequest(dtoRequestResponseLog);
		
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoRequestResponseLog;

	}
	
	static public void logResponse(DtoRequestResponseLog dtoRequestResponseLog, Object responseMessage) {
		try {
			
			
			ObjectMapper om = new ObjectMapper();
			
			String json = om.writeValueAsString(responseMessage);
			
			System.out.println("dto JSON: " + json);
			
			dtoRequestResponseLog.setResponseJson(json);
			dtoRequestResponseLog.setUpdated(new Date());
			LOGGER.debug(dtoRequestResponseLog);
//			service.logResponse(dtoRequestResponseLog);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

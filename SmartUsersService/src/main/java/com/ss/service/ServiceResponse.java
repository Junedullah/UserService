/**SmartSoftware User - Service */
/**
 * Description: ServiceResponse
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.model.SmartMessage;
import com.ss.model.dto.DtosmartMessage;
import com.ss.repository.RepositoryException;


@Service("ServiceResponse")
public class ServiceResponse {

	static Logger log = Logger.getLogger(ServiceResponse.class.getName());

	@Autowired
	RepositoryException repositoryException;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	/**
	 * @param message
	 * @param b
	 * @return
	 */
	public DtosmartMessage getMessageByShortAndIsDeleted(String message, boolean b) {
		String langId = httpServletRequest.getHeader("langId");
		SmartMessage exceptionMessage = repositoryException.findByMessageShortAndIsDeletedAndLanguageLanguageId(message, b,Integer.parseInt(langId));
		return new DtosmartMessage(exceptionMessage);
	}

	/**
	 * @param message
	 * @param b
	 * @return
	 */
	public String getStringMessageByShortAndIsDeleted(String message, boolean b) 
	{
		String responseMessage = "";
		String langId = httpServletRequest.getHeader("langId");
		SmartMessage exceptionMessage = repositoryException.findByMessageShortAndIsDeletedAndLanguageLanguageId(message, b,Integer.parseInt(langId));
		if(exceptionMessage!=null)
		{
			responseMessage = exceptionMessage.getMessage();
		}
		else
		{
			responseMessage="N/A";
		}
		return responseMessage;
	}

}

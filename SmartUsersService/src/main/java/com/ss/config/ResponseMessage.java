/**SmartSoftware User - Service */
/**
 * Description: ResponseMessage
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.dto.DtosmartMessage;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

	private int code;
	private org.springframework.http.HttpStatus status;
	private String message;
	private Object result;
	private Object smartMessage;

	public ResponseMessage() {
	}

	/**
	 * @param code
	 * @param status
	 * @param message
	 */
	public ResponseMessage(int code, org.springframework.http.HttpStatus status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	/**
	 * @param code
	 * @param status
	 * @param message
	 */
	public ResponseMessage(int code, org.springframework.http.HttpStatus status, DtosmartMessage message) {
		this.code = code;
		this.status = status;
		this.smartMessage = message;
	}

	/**
	 * @param code
	 * @param status
	 * @param message
	 * @param result
	 */
	public ResponseMessage(int code, org.springframework.http.HttpStatus status, DtosmartMessage message, Object result) {
		this.code = code;
		this.status = status;
		this.smartMessage = message;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public org.springframework.http.HttpStatus getStatus() {
		return status;
	}

	public void setStatus(org.springframework.http.HttpStatus status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getSmartMessage() {
		return smartMessage;
	}

	public void setSmartMessage(Object smartMessage) {
		this.smartMessage = smartMessage;
	}

	
}


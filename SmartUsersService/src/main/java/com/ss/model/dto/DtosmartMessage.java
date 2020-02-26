/**SmartSoftware User - Service */
/**
 * Description: Dto smart Message class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.SmartMessage;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtosmartMessage {
	private String messagePrimary;
	private String messageSecondary;
	private String message;
	private String messageShort;

	public DtosmartMessage(SmartMessage message) {
		if (message != null) {
			this.setMessageShort(message.getMessageShort());
			this.message = message.getMessage();
		} else {
			this.setMessageShort("N/A");
			this.message = "N/A";
		}
	}

	public String getMessagePrimary() {
		return messagePrimary;
	}

	public void setMessagePrimary(String messagePrimary) {
		this.messagePrimary = messagePrimary;
	}

	public String getMessageSecondary() {
		return messageSecondary;
	}

	public void setMessageSecondary(String messageSecondary) {
		this.messageSecondary = messageSecondary;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageShort() {
		return messageShort;
	}

	public void setMessageShort(String messageShort) {
		this.messageShort = messageShort;
	}

}

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the smart_message database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "smart_message")
@NamedQuery(name = "SmartMessage.findAll", query = "SELECT b FROM SmartMessage b")
public class SmartMessage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "smart_message_id")
	private int smartMessageId;

	@Column(name="message")
	private String message;

	@Column(name = "message_short")
	private String messageShort;

	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

	public int getSmartMessageId() {
		return smartMessageId;
	}

	public void setSmartMessageId(int smartMessageId) {
		this.smartMessageId = smartMessageId;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
 
	
}
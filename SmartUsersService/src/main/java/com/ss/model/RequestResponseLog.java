/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the request_response_log database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "request_response_log")
public class RequestResponseLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "request_id")
	private String requestId;

	@Column(name = "host")
	private String host;

	@Column(name = "lang_id")
	private int langId;

	@Column(name = "origin")
	private String origin;

	@Column(name = "referer")
	private String referer;

	@Column(name = "session")
	private String session;

	@Column(name = "tenant_id")
	private String tenantId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "request_json")
	private String requestJson;

	@Column(name = "response_json")
	private String responseJson;

	@Column(name = "created")
	private Date created;

	@Column(name = "updated")
	private Date updated;
	  
	  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRequestJson() {
		return requestJson;
	}
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}
	public String getResponseJson() {
		return responseJson;
	}
	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}

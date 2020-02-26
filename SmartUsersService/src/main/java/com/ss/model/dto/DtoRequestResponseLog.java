/**SmartSoftware User - Service */
/**
 * Description: Dto Request Response Log class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoRequestResponseLog {

	  private int id;
	  private String requestId;
	  private String host;
	  private int langId;
	  private String origin;
	  private String referer;
	  private String session;
	  private String tenantId;
	  private int userId;
	  private String requestJson;
	  private String responseJson;
	  private Date created;
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
	
	@Override
	public String toString() {
		return "DtoRequestResponseLog [id=" + id + ", requestId=" + requestId + ", host=" + host + ", langId=" + langId
				+ ", origin=" + origin + ", referer=" + referer + ", session=" + session + ", tenantId=" + tenantId
				+ ", userId=" + userId + ", requestJson=" + requestJson + ", responseJson=" + responseJson
				+ ", created=" + created + ", updated=" + updated + "]";
	}

}

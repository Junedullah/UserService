/**SmartSoftware User - Service */
/**
 * Description: Dto Transaction Type  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.TransactionType;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoTransactionType {

	private int transactionTypeId;
	private String transactionTypeName;
	private String transactionTypeDescription;
	private Boolean viewAccess;
	private Boolean postAccess;
	private Boolean deleteAccess;
	private Boolean emailAccess;
	private Boolean exportAccess;
	private Integer pageNumber;
	private Integer pageSize;
	private List<DtoTransaction> transactionsList;
	private int accessRoleId;

	public DtoTransactionType() {

	}

	/**
	 * @param transactionType
	 */
	public DtoTransactionType(TransactionType transactionType) {
		this.transactionTypeId = transactionType.getTransactionTypeId();
		if (UtilRandomKey.isNotBlank(transactionType.getTransactionTypeName())) {
			this.transactionTypeName = transactionType.getTransactionTypeName();
		} else {
			this.transactionTypeName = "";
		}

		if (UtilRandomKey.isNotBlank(transactionType.getTransactionTypeDescription())) {
			this.transactionTypeDescription = transactionType.getTransactionTypeDescription();
		} else {
			this.transactionTypeDescription = "";
		}
	}

	public int getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(int transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	public String getTransactionTypeDescription() {
		return transactionTypeDescription;
	}

	public void setTransactionTypeDescription(String transactionTypeDescription) {
		this.transactionTypeDescription = transactionTypeDescription;
	}

	public Boolean getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(Boolean viewAccess) {
		this.viewAccess = viewAccess;
	}

	public Boolean getPostAccess() {
		return postAccess;
	}

	public void setPostAccess(Boolean postAccess) {
		this.postAccess = postAccess;
	}

	public Boolean getDeleteAccess() {
		return deleteAccess;
	}

	public void setDeleteAccess(Boolean deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public Boolean getEmailAccess() {
		return emailAccess;
	}

	public void setEmailAccess(Boolean emailAccess) {
		this.emailAccess = emailAccess;
	}

	public Boolean getExportAccess() {
		return exportAccess;
	}

	public void setExportAccess(Boolean exportAccess) {
		this.exportAccess = exportAccess;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<DtoTransaction> getTransactionsList() {
		return transactionsList;
	}

	public void setTransactionsList(List<DtoTransaction> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public int getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(int accessRoleId) {
		this.accessRoleId = accessRoleId;
	}

}

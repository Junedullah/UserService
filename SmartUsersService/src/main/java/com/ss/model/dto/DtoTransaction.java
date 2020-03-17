/**SmartSoftware User - Service */
/**
 * Description: DtoTransaction  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Transactions;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoTransaction {

	private int transactionId;
	private String transactionName;
	private String transactionDescription;
	private List<DtoTransactionType> transactionTypesList;
	private Boolean viewAccess;
	private Boolean emailAccess;
	private Boolean exportAccess;
	private Boolean postAccess;
	private Boolean deleteAccess;
	private Integer pageNumber;
	private Integer pageSize;

	public DtoTransaction() {

	}

	/**
	 * @param transactions
	 */
	public DtoTransaction(Transactions transactions) {
		this.transactionId = transactions.getTransactionId();
		if (UtilRandomKey.isNotBlank(transactions.getTransactionName())) {
			this.transactionName = transactions.getTransactionName();
		} else {
			this.transactionName = "";
		}

		if (UtilRandomKey.isNotBlank(transactions.getTransactionDescription())) {
			this.transactionDescription = transactions.getTransactionDescription();
		} else {
			this.transactionDescription = "";
		}
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public List<DtoTransactionType> getTransactionTypesList() {
		return transactionTypesList;
	}

	public void setTransactionTypesList(List<DtoTransactionType> transactionTypesList) {
		this.transactionTypesList = transactionTypesList;
	}

	public Boolean getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(Boolean viewAccess) {
		this.viewAccess = viewAccess;
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

}

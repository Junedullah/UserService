/**SmartSoftware User - Service */
/**
 * Description: Dto Report Category  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.ReportCategory;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoReportCategory {

	private int reportCategoryId;
	private String reportCategoryName;
	private String reportCategoryDescription;
	private Boolean viewAccess;
	private Boolean emailAccess;
	private Boolean exportAccess;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean postAccess;
	private Boolean deleteAccess;
	List<DtoReport> reportList;
	private int accessRoleId;

	public DtoReportCategory() {

	}

	public DtoReportCategory(ReportCategory reportCategory) {
		this.reportCategoryId = reportCategory.getReportCategoryId();
		if (UtilRandomKey.isNotBlank(reportCategory.getReportCategoryName())) {
			this.reportCategoryName = reportCategory.getReportCategoryName();
		} else {
			this.reportCategoryName = "";
		}

		if (UtilRandomKey.isNotBlank(reportCategory.getReportCategoryDescription())) {
			this.reportCategoryDescription = reportCategory.getReportCategoryDescription();
		} else {
			this.reportCategoryDescription = "";
		}
	}

	public int getReportCategoryId() {
		return reportCategoryId;
	}

	public void setReportCategoryId(int reportCategoryId) {
		this.reportCategoryId = reportCategoryId;
	}

	public String getReportCategoryName() {
		return reportCategoryName;
	}

	public void setReportCategoryName(String reportCategoryName) {
		this.reportCategoryName = reportCategoryName;
	}

	public String getReportCategoryDescription() {
		return reportCategoryDescription;
	}

	public void setReportCategoryDescription(String reportCategoryDescription) {
		this.reportCategoryDescription = reportCategoryDescription;
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

	public List<DtoReport> getReportList() {
		return reportList;
	}

	public void setReportList(List<DtoReport> reportList) {
		this.reportList = reportList;
	}

	public int getAccessRoleId() {
		return accessRoleId;
	}

	public void setAccessRoleId(int accessRoleId) {
		this.accessRoleId = accessRoleId;
	}
}

/**SmartSoftware User - Service */
/**
 * Description: DTO Report Detail  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Reports;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoReport {

	private int reportId;
	private String reportName;
	private String reporDescription;
	private List<DtoReportCategory> reportCategoriesList;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean viewAccess;
	private Boolean emailAccess;
	private Boolean exportAccess;
	private Boolean postAccess;
	private Boolean deleteAccess;

	public DtoReport() {

	}

	public DtoReport(Reports reports) {
		this.reportId = reports.getReportId();
		if (UtilRandomKey.isNotBlank(reports.getReportName())) {
			this.reportName = reports.getReportName();
		} else {
			this.reportName = "";
		}

		if (UtilRandomKey.isNotBlank(reports.getReportDescription())) {
			this.reporDescription = reports.getReportDescription();
		} else {
			this.reporDescription = "";
		}
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReporDescription() {
		return reporDescription;
	}

	public void setReporDescription(String reporDescription) {
		this.reporDescription = reporDescription;
	}

	public List<DtoReportCategory> getReportCategoriesList() {
		return reportCategoriesList;
	}

	public void setReportCategoriesList(List<DtoReportCategory> reportCategoriesList) {
		this.reportCategoriesList = reportCategoriesList;
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

}

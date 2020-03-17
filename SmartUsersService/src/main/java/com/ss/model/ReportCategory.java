/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Report Category database table.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
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
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "report_category")
// @NamedQuery(name="Reports.findAll", query="SELECT t FROM ReportDetail t")
public class ReportCategory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_category_id")
	private int reportCategoryId;

	@Column(name = "report_category_name")
	private String reportCategoryName;

	@Column(name = "report_category_description")
	private String reportCategoryDescription;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
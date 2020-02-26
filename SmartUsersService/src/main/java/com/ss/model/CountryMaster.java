/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the country_master database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "country_master")
@NamedQuery(name = "CountryMaster.findAll", query = "SELECT c FROM CountryMaster c")
public class CountryMaster extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	private int countryId;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "short_name")
	private String shortName;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

/*	// bi-directional many-to-one association to Company
	@OneToMany(mappedBy = "countryMaster")
	private List<Company> companies;
*/
	// bi-directional many-to-one association to StateMaster
	@OneToMany(mappedBy = "countryMaster")
	private List<StateMaster> stateMasters;

	// bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy = "countryMaster")
	private List<UserDetail> userDetails;

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
/*
	public List<Company> getCompanies() {
		return this.companies;
	}*/

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	/*public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company addCompany(Company company) {
		getCompanies().add(company);
		company.setCountryMaster(this);

		return company;
	}

	public Company removeCompany(Company company) {
		getCompanies().remove(company);
		company.setCountryMaster(null);

		return company;
	}*/

	public List<StateMaster> getStateMasters() {
		return this.stateMasters;
	}

	public void setStateMasters(List<StateMaster> stateMasters) {
		this.stateMasters = stateMasters;
	}

	public StateMaster addStateMaster(StateMaster stateMaster) {
		getStateMasters().add(stateMaster);
		stateMaster.setCountryMaster(this);

		return stateMaster;
	}

	public StateMaster removeStateMaster(StateMaster stateMaster) {
		getStateMasters().remove(stateMaster);
		stateMaster.setCountryMaster(null);

		return stateMaster;
	}

	public List<UserDetail> getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

/*	public UserDetail addUserDetail(UserDetail userDetail) {
		getUserDetails().add(userDetail);
		userDetail.setCountryMaster(this);

		return userDetail;
	}

	public UserDetail removeUserDetail(UserDetail userDetail) {
		getUserDetails().remove(userDetail);
		userDetail.setCountryMaster(null);

		return userDetail;
	}
*/
}
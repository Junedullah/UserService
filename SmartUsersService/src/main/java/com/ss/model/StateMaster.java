/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the state_master database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
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
@Table(name = "state_master")
@NamedQuery(name = "StateMaster.findAll", query = "SELECT s FROM StateMaster s")
public class StateMaster extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private int stateId;

	@Column(name = "state_name")
	private String stateName;
	
	@Column(name = "state_code")
	private String stateCode;

	// bi-directional many-to-one association to CityMaster
	@OneToMany(mappedBy = "stateMaster")
	private List<CityMaster> cityMasters;

	/*// bi-directional many-to-one association to Company
	@OneToMany(mappedBy = "stateMaster")
	private List<Company> companies;*/

	// bi-directional many-to-one association to CountryMaster
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryMaster countryMaster;

	// bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy = "stateMaster")
	private List<UserDetail> userDetails;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

	public StateMaster() {
	}

	public int getStateId() {
		return this.stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public List<CityMaster> getCityMasters() {
		return this.cityMasters;
	}

	public void setCityMasters(List<CityMaster> cityMasters) {
		this.cityMasters = cityMasters;
	}

	public CityMaster addCityMaster(CityMaster cityMaster) {
		getCityMasters().add(cityMaster);
		cityMaster.setStateMaster(this);

		return cityMaster;
	}

	public CityMaster removeCityMaster(CityMaster cityMaster) {
		getCityMasters().remove(cityMaster);
		cityMaster.setStateMaster(null);

		return cityMaster;
	}

	/*public List<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company addCompany(Company company) {
		getCompanies().add(company);
		company.setStateMaster(this);

		return company;
	}

	public Company removeCompany(Company company) {
		getCompanies().remove(company);
		company.setStateMaster(null);

		return company;
	}
*/
	public CountryMaster getCountryMaster() {
		return this.countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	public List<UserDetail> getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

	/*public UserDetail addUserDetail(UserDetail userDetail) {
		getUserDetails().add(userDetail);
		userDetail.setStateMaster(this);

		return userDetail;
	}

	public UserDetail removeUserDetail(UserDetail userDetail) {
		getUserDetails().remove(userDetail);
		userDetail.setStateMaster(null);

		return userDetail;
	}
*/
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	

}
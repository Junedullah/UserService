/**SmartSoftware User - Service */
/**
 * Description: Company model
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
public class Company extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private int companyId;

	private String address;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "database_ip")
	private String databaseIP;
	
	@Column(name = "database_port")
	private String databasePort;
	
	@Column(name = "database_username")
	private String databaseUsername;
	
	@Column(name = "database_password")
	private String databasePassword;

	/*
	 * @ManyToMany(mappedBy = "companyId")
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE) private List<Field> fieldId;
	 * 
	 * // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	 * "company") // @Where(clause = "is_deleted = false") // private List<Company>
	 * companyList;
	 * 
	 * @ManyToMany(cascade = { CascadeType.ALL })
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE) private List<ReportMaster>
	 * reportMaster;
	 */
	public String getDatabaseIP() {
		return databaseIP;
	}

	public void setDatabaseIP(String databaseIP) {
		this.databaseIP = databaseIP;
	}

	public String getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	private String description;

	private String email;

	private String fax;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "licence_code")
	private String licenceCode;

	private String name;

	private String phone;

	private String zipcode;
	
	@Column(name = "web_address")
	private String webAddress;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;

	// bi-directional many-to-one association to StateMaster
	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateMaster stateMaster;

	// bi-directional many-to-one association to CityMaster
	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityMaster cityMaster;

	// bi-directional many-to-one association to CountryMaster
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryMaster countryMaster;
	/*
	 * // bi-directional many-to-one association to UserCompanyRelation
	 * 
	 * @OneToMany(mappedBy = "company") private List<UserCompanyRelation>
	 * userCompanyRelations;
	 */
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "field_id") private Field field;
	 */

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLicenceCode() {
		return this.licenceCode;
	}

	public void setLicenceCode(String licenceCode) {
		this.licenceCode = licenceCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public StateMaster getStateMaster() {
		return this.stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	public CityMaster getCityMaster() {
		return this.cityMaster;
	}

	public void setCityMaster(CityMaster cityMaster) {
		this.cityMaster = cityMaster;
	}

	public CountryMaster getCountryMaster() {
		return this.countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	/*
	 * public List<UserCompanyRelation> getUserCompanyRelations() { return
	 * this.userCompanyRelations; }
	 * 
	 * public void setUserCompanyRelations(List<UserCompanyRelation>
	 * userCompanyRelations) { this.userCompanyRelations = userCompanyRelations; }
	 */

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/*
	 * public UserCompanyRelation addUserCompanyRelation(UserCompanyRelation
	 * userCompanyRelation) { getUserCompanyRelations().add(userCompanyRelation);
	 * userCompanyRelation.setCompany(this);
	 * 
	 * return userCompanyRelation; }
	 * 
	 * public UserCompanyRelation removeUserCompanyRelation(UserCompanyRelation
	 * userCompanyRelation) { getUserCompanyRelations().remove(userCompanyRelation);
	 * userCompanyRelation.setCompany(null);
	 * 
	 * return userCompanyRelation; }
	 * 
	 * public Field getField() { return field; }
	 * 
	 * public void setField(Field field) { this.field = field; }
	 * 
	 * public List<Field> getFieldId() { return fieldId; }
	 * 
	 * public void setFieldId(List<Field> fieldId) { this.fieldId = fieldId; }
	 * 
	 * public List<ReportMaster> getReportMaster() { return reportMaster; }
	 * 
	 * public void setReportMaster(List<ReportMaster> reportMaster) {
	 * this.reportMaster = reportMaster; }
	 */
}
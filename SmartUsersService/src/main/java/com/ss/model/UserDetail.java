/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "user_detail")
@NamedQuery(name = "UserDetail.findAll", query = "SELECT u FROM UserDetail u")
public class UserDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ud_id")
	private int udId;

	private String address;

	@Column(name = "country_code")
	private String countryCode;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String email;

	@Column(name = "employee_code")
	private String employeeCode;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "secondary_first_name")
	private String secondaryFirstName;

	@Column(name = "secondary_last_name")
	private String secondaryLastName;

	@Column(name = "secondary_middle_name")
	private String secondaryMiddleName;
	
	private String phone;

	@Column(name = "session_duration")
	private String sessionDuration;

	private String zipcode;
	
	// bi-directional many-to-one association to User
		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		// bi-directional many-to-one association to StateMaster
		@ManyToOne
		@JoinColumn(name = "state_id")
		private StateMaster stateMaster;

		// bi-directional many-to-one association to CountryMaster
		@ManyToOne
		@JoinColumn(name = "country_id")
		private CountryMaster countryMaster;

		// bi-directional many-to-one association to CityMaster
		@ManyToOne
		@JoinColumn(name = "city_id")
		private CityMaster cityMaster;

	public UserDetail() {
		super();
	}

	public int getUdId() {
		return udId;
	}

	public void setUdId(int udId) {
		this.udId = udId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSecondaryFirstName() {
		return secondaryFirstName;
	}

	public void setSecondaryFirstName(String secondaryFirstName) {
		this.secondaryFirstName = secondaryFirstName;
	}

	public String getSecondaryLastName() {
		return secondaryLastName;
	}

	public void setSecondaryLastName(String secondaryLastName) {
		this.secondaryLastName = secondaryLastName;
	}

	public String getSecondaryMiddleName() {
		return secondaryMiddleName;
	}

	public void setSecondaryMiddleName(String secondaryMiddleName) {
		this.secondaryMiddleName = secondaryMiddleName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(String sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StateMaster getStateMaster() {
		return stateMaster;
	}

	public void setStateMaster(StateMaster stateMaster) {
		this.stateMaster = stateMaster;
	}

	public CountryMaster getCountryMaster() {
		return countryMaster;
	}

	public void setCountryMaster(CountryMaster countryMaster) {
		this.countryMaster = countryMaster;
	}

	public CityMaster getCityMaster() {
		return cityMaster;
	}

	public void setCityMaster(CityMaster cityMaster) {
		this.cityMaster = cityMaster;
	}
	
	

	
}

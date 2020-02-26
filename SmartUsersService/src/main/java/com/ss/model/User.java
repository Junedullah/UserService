/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Feb 13, 2020
 * Modified on: Feb 13, 2020 11:19:38 AM
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

@Entity
@Table(name="user")
@org.hibernate.annotations.Entity(dynamicInsert = true)
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	private String email;

	@Column(name = "employee_code")
	private String employeeCode;

	@Column(name = "is_active", columnDefinition = "tinyint(0) default 0")
	private boolean isActive;

	@Column(name = "is_reset_password", columnDefinition = "tinyint(0) default 0")
	private boolean isResetPassword;
	
	@Column(name = "ip_checked", columnDefinition = "tinyint(0) default 1")
	protected Boolean ipChecked;

	private String password;

	private String username;
	
	// bi-directional many-to-one association to LoginOtp
	@OneToMany(mappedBy = "user")
	private List<LoginOtp> loginOtps;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

/*	// bi-directional many-to-one association to UserCompanyRelation
	@OneToMany(mappedBy = "user")
	private List<UserCompanyRelation> userCompanyRelations;
	@ManyToMany(cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ReportMaster> reportMaster;
	*/

	// bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy = "user")
	private List<UserDetail> userDetails;

/*	// bi-directional many-to-one association to UserSession
	@OneToMany(mappedBy = "user")
	private List<UserSession> userSessions;

	*/

	public User() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isResetPassword() {
		return isResetPassword;
	}

	public void setResetPassword(boolean isResetPassword) {
		this.isResetPassword = isResetPassword;
	}

	public Boolean getIpChecked() {
		return ipChecked;
	}

	public void setIpChecked(Boolean ipChecked) {
		this.ipChecked = ipChecked;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserDetail> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

	public List<LoginOtp> getLoginOtps() {
		return loginOtps;
	}

	public void setLoginOtps(List<LoginOtp> loginOtps) {
		this.loginOtps = loginOtps;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	
}

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_company_relation database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "user_company_relation")
@NamedQuery(name = "UserCompanyRelation.findAll", query = "SELECT u FROM UserCompanyRelation u")
public class UserCompanyRelation extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_company_relation_id")
	private int userCompanyRelationId;

	@Column(name = "default_company")
	private byte defaultCompany;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// bi-directional many-to-one association to AuthorizationSetting
	@ManyToOne
	@JoinColumn(name = "auth_setting_id")
	private AuthorizationSetting authorizationSetting;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	// bi-directional many-to-one association to UserGroup
	@ManyToOne
	@JoinColumn(name = "user_group_id")
	private UserGroup userGroup;

	public UserCompanyRelation() {
	}

	public int getUserCompanyRelationId() {
		return this.userCompanyRelationId;
	}

	public void setUserCompanyRelationId(int userCompanyRelationId) {
		this.userCompanyRelationId = userCompanyRelationId;
	}

	public byte getDefaultCompany() {
		return this.defaultCompany;
	}

	public void setDefaultCompany(byte defaultCompany) {
		this.defaultCompany = defaultCompany;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AuthorizationSetting getAuthorizationSetting() {
		return this.authorizationSetting;
	}

	public void setAuthorizationSetting(AuthorizationSetting authorizationSetting) {
		this.authorizationSetting = authorizationSetting;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

}
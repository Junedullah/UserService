/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the screens database table.
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
@Table(name = "screen")
@NamedQuery(name = "Screen.findAll", query = "SELECT s FROM Screen s")
public class Screen extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "screen_id")
	private int screenId;

	private String description;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "help_message")
	private String helpMessage;

	@Column(name = "sidemenu")
	private String sideMenu;
	
	@Column(name = "sidemenu_url")
	private String sideMenuURL;

	// bi-directional many-to-one association to AccessRoleModuleRelation
	@OneToMany(mappedBy = "screen")
	private List<AccessRoleScreenRelation> accessRoleModuleRelations;

	// bi-directional many-to-one association to Field
	@OneToMany(mappedBy = "screen")
	private List<Field> fields;

	/*// bi-directional many-to-one association to Screen
		@OneToMany(mappedBy = "module")
		private List<Company> companies;*/

	// bi-directional many-to-one association to Module
/*		@ManyToOne
		@JoinColumn(name = "company_id")
		private Company company;*/
		
	// bi-directional many-to-one association to Module
	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

	public int getScreenId() {
		return this.screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScreenCode() {
		return this.screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public List<AccessRoleScreenRelation> getAccessRoleModuleRelations() {
		return this.accessRoleModuleRelations;
	}

	public void setAccessRoleModuleRelations(List<AccessRoleScreenRelation> accessRoleModuleRelations) {
		this.accessRoleModuleRelations = accessRoleModuleRelations;
	}

	public AccessRoleScreenRelation addAccessRoleModuleRelation(AccessRoleScreenRelation accessRoleModuleRelation) {
		getAccessRoleModuleRelations().add(accessRoleModuleRelation);
		accessRoleModuleRelation.setScreen(this);

		return accessRoleModuleRelation;
	}

	public AccessRoleScreenRelation removeAccessRoleModuleRelation(AccessRoleScreenRelation accessRoleModuleRelation) {
		getAccessRoleModuleRelations().remove(accessRoleModuleRelation);
		accessRoleModuleRelation.setScreen(null);

		return accessRoleModuleRelation;
	}

	public List<Field> getFields() {
		return this.fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Field addField(Field field) {
		getFields().add(field);
		field.setScreen(this);

		return field;
	}

	public Field removeField(Field field) {
		getFields().remove(field);
		field.setScreen(null);

		return field;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getSideMenuURL() {
		return sideMenuURL;
	}

	public void setSideMenuURL(String sideMenuURL) {
		this.sideMenuURL = sideMenuURL;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public String getSideMenu() {
		return sideMenu;
	}

	public void setSideMenu(String sideMenu) {
		this.sideMenu = sideMenu;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	

}
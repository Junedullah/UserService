/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the screen_menu database table.
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


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "screen_menus")
@NamedQuery(name = "ScreenMenu.findAll", query = "SELECT sm FROM ScreenMenu sm")
public class ScreenMenu extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "screen_menu_id")
	private Integer screenMenuId;

	private String description;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "sideMenu_code")
	private String sideMenuCode;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "help_message")
	private String helpMessage;

	@Column(name = "sidemenu")
	private String sideMenu;

	@Column(name = "sidemenu_url")
	private String sideMenuURL;

	@ManyToOne
	@JoinColumn(name = "screen_category_id")
	private ScreenCategory screenCategory;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

	@ManyToOne
	@JoinColumn(name = "lang_id")
	private Language language;

	public Integer getScreenMenuId() {
		return screenMenuId;
	}

	public void setScreenMenuId(Integer screenMenuId) {
		this.screenMenuId = screenMenuId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getSideMenuCode() {
		return sideMenuCode;
	}

	public void setSideMenuCode(String sideMenuCode) {
		this.sideMenuCode = sideMenuCode;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
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

	public String getSideMenuURL() {
		return sideMenuURL;
	}

	public void setSideMenuURL(String sideMenuURL) {
		this.sideMenuURL = sideMenuURL;
	}

	public ScreenCategory getScreenCategory() {
		return screenCategory;
	}

	public void setScreenCategory(ScreenCategory screenCategory) {
		this.screenCategory = screenCategory;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}

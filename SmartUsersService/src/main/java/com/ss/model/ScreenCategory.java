/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the screen_category database table.
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
@Table(name = "screen_categorys")
@NamedQuery(name = "ScreenCategory.findAll", query = "SELECT sc FROM ScreenCategory sc")
public class ScreenCategory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "screen_category_id")
	private Integer screenCategoryId;

	@Column(name = "screen_category_name")
	private String screenCategoryName;

	@Column(name = "screen_category_code")
	private String screenCategoryCode;

	@Column(name = "help_message")
	private String helpMessage;
	
	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

	@ManyToOne
	@JoinColumn(name = "lang_id")
	private Language language;

	public Integer getScreenCategoryId() {
		return screenCategoryId;
	}

	public void setScreenCategoryId(Integer screenCategoryId) {
		this.screenCategoryId = screenCategoryId;
	}

	public String getScreenCategoryName() {
		return screenCategoryName;
	}

	public void setScreenCategoryName(String screenCategoryName) {
		this.screenCategoryName = screenCategoryName;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getScreenCategoryCode() {
		return screenCategoryCode;
	}

	public void setScreenCategoryCode(String screenCategoryCode) {
		this.screenCategoryCode = screenCategoryCode;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

}

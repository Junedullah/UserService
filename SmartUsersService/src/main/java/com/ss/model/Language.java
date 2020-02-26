package com.ss.model;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Language database table.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "language")
@NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l")
public class Language extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lang_id")
	private int languageId;

	@Column(name = "lang_name")
	private String languageName;

	@Column(name = "lang_orientation")
	private String languageOrientation;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "db_names")
	private String dbNames;

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getLanguageOrientation() {
		return languageOrientation;
	}

	public void setLanguageOrientation(String languageOrientation) {
		this.languageOrientation = languageOrientation;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDbNames() {
		return dbNames;
	}

	public void setDbNames(String dbNames) {
		this.dbNames = dbNames;
	}
	
}
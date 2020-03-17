/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the module database table..
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
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

@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
public class Module extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "module_id")
	private int moduleId;

	private String description;

	@Column(name = "module_code")
	private String moduleCode;

	@Column(name = "name")
	private String name;

	@Column(name = "help_message")
	private String helpMessage;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;

	// bi-directional many-to-one association to Screen
	@OneToMany(mappedBy = "module")
	private List<Screen> screens;

	public Module() {
	}

	public int getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public List<Screen> getScreens() {
		return this.screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}

	public Screen addScreen(Screen screen) {
		getScreens().add(screen);
		screen.setModule(this);

		return screen;
	}

	public Screen removeScreen(Screen screen) {
		getScreens().remove(screen);
		screen.setModule(null);

		return screen;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHelpMessage() {
		return helpMessage;
	}

	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
}
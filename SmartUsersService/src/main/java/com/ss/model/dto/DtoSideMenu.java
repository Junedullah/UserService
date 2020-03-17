/**SmartSoftware User - Service */
/**
 * Description: DTO Side Menu class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Screen;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoSideMenu {
	
	private Integer screenId;
	private String sideMenuName;
	private String helpMessagesideMenu;
	private String sideMenuURL;
	private String screenCategoryCode;
	private String companyId;
	private String userId;

	public DtoSideMenu() {

	}

	public DtoSideMenu(Screen screen) {
		   this.screenId = screen.getScreenId();
			if (UtilRandomKey.isNotBlank(screen.getHelpMessage())) {
				this.helpMessagesideMenu = screen.getHelpMessage();
			} else {
				this.helpMessagesideMenu = "";
			}

			if (UtilRandomKey.isNotBlank(screen.getSideMenu())) {
				this.sideMenuName = screen.getSideMenu();
			} else {
				this.sideMenuName = "";
			}
		   this.sideMenuURL=screen.getSideMenuURL();
	}

	public Integer getScreenId() {
		return screenId;
	}

	public void setScreenId(Integer screenId) {
		this.screenId = screenId;
	}

	public String getSideMenuName() {
		return sideMenuName;
	}

	public void setSideMenuName(String sideMenuName) {
		this.sideMenuName = sideMenuName;
	}

	public String getHelpMessagesideMenu() {
		return helpMessagesideMenu;
	}

	public void setHelpMessagesideMenu(String helpMessagesideMenu) {
		this.helpMessagesideMenu = helpMessagesideMenu;
	}

	public String getSideMenuURL() {
		return sideMenuURL;
	}

	public void setSideMenuURL(String sideMenuURL) {
		this.sideMenuURL = sideMenuURL;
	}

	public String getScreenCategoryCode() {
		return screenCategoryCode;
	}
	
	public void setScreenCategoryCode(String screenCategoryCode) {
		this.screenCategoryCode = screenCategoryCode;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}

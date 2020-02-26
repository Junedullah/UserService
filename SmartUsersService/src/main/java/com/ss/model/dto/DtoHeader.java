/**SmartSoftware User - Service */
/**
 * Description:Dto Header class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoHeader {
	
	private Integer moduleId;
//	private List<DtoSideMenu> sideMenuList;
	private String headerName;
	private String helpMessageHeader;
//	private List<DtoScreenCategory> screenCategoryList;

	public DtoHeader() {

	}

/*	public DtoHeader(Module module) {
		this.moduleId = module.getModuleId();
			if (UtilRandomKey.isNotBlank(module.getHelpMessage())) {
				this.helpMessageHeader = module.getHelpMessage();
			} else {
				this.helpMessageHeader = "";
			}

			if (UtilRandomKey.isNotBlank(module.getName())) {
				this.headerName = module.getName();
			} else {
				this.headerName = "";
			}
	}*/

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	
	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getHelpMessageHeader() {
		return helpMessageHeader;
	}

	public void setHelpMessageHeader(String helpMessageHeader) {
		this.helpMessageHeader = helpMessageHeader;
	}
	


}

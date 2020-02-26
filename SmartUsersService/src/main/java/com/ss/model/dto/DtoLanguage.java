package com.ss.model.dto;

/**SmartSoftware User - Service */
/**
 * Description: Dto Language class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.model.Language;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DtoLanguage {
	
	private int languageId;
	private String languageName;
	private String languageOrientation;
	private String languageStatus;
	private Boolean isActive;
	private MultipartFile file;
	List<String> dbNames;
	
	public DtoLanguage() {
		// TODO Auto-generated constructor stub
	}
	
	public DtoLanguage(Language language) {
		this.languageId=language.getLanguageId();
		this.languageName="";
		if(UtilRandomKey.isNotBlank(language.getLanguageName())){
			this.languageName=language.getLanguageName();
		}
		this.languageOrientation="";
		if(UtilRandomKey.isNotBlank(language.getLanguageOrientation())){
			this.languageOrientation=language.getLanguageOrientation();
		}
		
		this.isActive=language.getIsActive();
		dbNames= new ArrayList<>();
		if(UtilRandomKey.isNotBlank(language.getDbNames()))
		{
			String[] names= language.getDbNames().split(",");
			if(names.length>0){
				
				for (String name : names) {
					dbNames.add(name);
				}
			}
		}
	}
	
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

	public String getLanguageStatus() {
		return languageStatus;
	}

	public void setLanguageStatus(String languageStatus) {
		this.languageStatus = languageStatus;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}

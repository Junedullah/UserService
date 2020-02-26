/**SmartSoftware User - Service */
/**
 * Description: ServiceGrid
 * Name of Project: ServiceLanguage
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.model.Language;
import com.ss.model.dto.DtoLanguage;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryLanguage;

@Service("serviceLanguage")
public class ServiceLanguage {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ServiceLanguage.class);
	
	@Autowired
	RepositoryLanguage repositoryLanguage;

	
	public DtoSearch getAllLanguage() {
		DtoLanguage dtoLanguage;
		DtoSearch dtoSearch = new DtoSearch();
		List<Language> languageList = null;
		languageList = repositoryLanguage.findByIsDeletedAndIsActive(false, true);
		List<DtoLanguage> dtoLanguageList=new ArrayList<>();
		if(languageList!=null && languageList.size()>0)
		{
			for (Language language : languageList) 
			{
				dtoLanguage=new DtoLanguage(language);
				dtoLanguage.setLanguageId(language.getLanguageId());
				dtoLanguage.setLanguageName(language.getLanguageName());
				dtoLanguageList.add(dtoLanguage);
			}
			dtoSearch.setRecords(dtoLanguageList);
		}
		return dtoSearch;
	}

}

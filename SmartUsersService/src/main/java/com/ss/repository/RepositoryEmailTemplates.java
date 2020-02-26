/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryEmailTemplates 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.EmailTemplate;



@Repository("repositoryEmailTemplates")
public interface RepositoryEmailTemplates extends JpaRepository<EmailTemplate, Integer> {

	/**
	 * @param templateName
	 * @return
	 */
	public EmailTemplate findByTemplateName(String templateName);

}
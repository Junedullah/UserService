/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryValidationMessages 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.ValidationMessage;


@Repository("repositoryValidationMessages")
public interface RepositoryValidationMessages extends JpaRepository<ValidationMessage, Integer> {

	/**
	 * @param fieldId
	 * @param deleted
	 * @return
	 */
	ValidationMessage findByMessageShortAndLanguageLanguageId(String messageShort, int langId);

	List<ValidationMessage> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

}

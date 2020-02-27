/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryException 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.SmartMessage;


@Repository("repositoryException")
public interface RepositoryException extends JpaRepository<SmartMessage, Integer> {

	/**
	 * @param message
	 * @param deleted
	 * @return
	 */
	public SmartMessage findByMessageShortAndIsDeleted(String message, boolean deleted);
	
	public SmartMessage findByMessageShortAndIsDeletedAndLanguageLanguageId(String message, boolean deleted,int langId);
}

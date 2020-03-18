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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query("Select sm  from SmartMessage sm where sm.message like :message and sm.isDeleted like :deleted and sm.language.languageId like :langId")
	public SmartMessage findByMessageShortAndIsDeletedAndLanguageLanguageId(@Param("message") String message,@Param("deleted") boolean deleted, @Param("langId")int langId);
}

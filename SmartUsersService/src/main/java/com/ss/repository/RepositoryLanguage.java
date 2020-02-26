/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryLanguage 
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.model.Language;

@Repository("repositoryLanguage")
public interface RepositoryLanguage extends JpaRepository<Language, Integer> {
	
	@Query("select count(*) from Language")
	int getTotalCount();
	
	public Language findByLanguageIdAndIsDeleted(int langId, boolean deleted);
	
	List<Language> findByIsDeletedAndIsActive(Boolean deleted,Boolean isActive,Pageable pageable);
	
	List<Language> findByIsDeletedAndIsActive(Boolean deleted,Boolean isActive);
	
	List<Language> findByIsDeleted(Boolean deleted);
	List<Language> findByIsDeleted(Boolean deleted,Pageable pageable);

	Language findByLanguageNameAndIsDeleted(String languageName, boolean b);
}
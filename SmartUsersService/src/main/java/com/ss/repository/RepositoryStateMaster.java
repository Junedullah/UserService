/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryStateMaster 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.StateMaster;


@Repository("repositoryStateMaster")
public interface RepositoryStateMaster extends JpaRepository<StateMaster, Integer> {

	/**
	 * @param countryId
	 * @param deleted
	 * @return
	 */
	List<StateMaster> findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(int countryId, boolean deleted, int langId);

	List<StateMaster> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

	StateMaster findByStateIdAndIsDeleted(int parseInt, boolean b);
	
	StateMaster findByStateCodeAndIsDeletedAndLanguageLanguageId(String stateCode,boolean deleted,int langId);

	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);


}



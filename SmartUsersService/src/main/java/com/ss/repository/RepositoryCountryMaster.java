/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryCountryMaster 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.CountryMaster;


@Repository("repositoryCountryMaster")
public interface RepositoryCountryMaster extends JpaRepository<CountryMaster, Integer> {

	/**
	 * @param deleted
	 * @param isActive
	 * @return
	 */
	List<CountryMaster> findByIsDeletedAndIsActiveAndLanguageLanguageId(boolean deleted, boolean isActive, int langId);

	/**
	 * @param countryId
	 * @param deleted
	 * @param isActive
	 * @return
	 */
	CountryMaster findByCountryIdAndIsDeletedAndIsActive(int countryId, boolean deleted, boolean isActive);

	List<CountryMaster> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

	CountryMaster findByCountryIdAndIsDeleted(int countryId, boolean b);
	
	CountryMaster findByshortNameAndIsDeletedAndLanguageLanguageId(String shortName,boolean b, int i);

	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);
}



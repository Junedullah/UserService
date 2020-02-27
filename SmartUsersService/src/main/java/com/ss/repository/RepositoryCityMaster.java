/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserDetail 
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 4:30:38 PM
 * @author Shahnawaz
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.CityMaster;


@Repository("repositoryCityMaster")
public interface RepositoryCityMaster extends JpaRepository<CityMaster, Integer> {

	/**
	 * @param stateId
	 * @param deleted
	 * @return
	 */
	List<CityMaster> findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(int stateId, boolean deleted, int langId);

	List<CityMaster> findByIsDeletedAndLanguageLanguageId(boolean b, int i);
	
	CityMaster findByCityCodeAndIsDeletedAndLanguageLanguageId(String cityCode,boolean b, int i);
	
	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);
}



/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryAppConfigSetting 
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.AppConfigSetting;


@Repository("repositoryAppConfigSetting")
public interface RepositoryAppConfigSetting extends JpaRepository<AppConfigSetting, Integer> {

	/**
	 * @param appConfigName
	 * @param deleted
	 * @return
	 */
	AppConfigSetting findByConfigNameAndIsDeleted(String appConfigName, Boolean deleted);

}

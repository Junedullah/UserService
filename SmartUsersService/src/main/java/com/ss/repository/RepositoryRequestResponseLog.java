/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryRequestResponseLog 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.RequestResponseLog;;


@Repository("repositoryRequestResponseLog")
public interface RepositoryRequestResponseLog extends JpaRepository<RequestResponseLog, Integer> {

	/**
	 * @param responseJson
	 * @param updated
	 * @param requestId
	 */
	@Modifying(clearAutomatically = true)
	@Query("update RequestResponseLog rrl set rrl.responseJson = :responseJson, rrl.updated=:updated where rrl.requestId = :requestId")
	long logResponse(@Param("responseJson") String responseJson,
			@Param("updated") Date updated, @Param("requestId") String requestId);


}

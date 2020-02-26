/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryRole 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.Role;



@Repository("repositoryRole")
public interface RepositoryRole extends JpaRepository<Role, Integer> {

	/**
	 * @param roleId
	 * @return
	 */
	Role findByRoleId(int roleId);

}

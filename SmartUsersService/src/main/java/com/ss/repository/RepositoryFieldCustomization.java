/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryFieldCustomization 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.FieldCustomization;

@Repository("repositoryFieldCustomization")
public interface RepositoryFieldCustomization extends JpaRepository<FieldCustomization, Integer> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update FieldCustomization f set f.isDeleted =:deleted, f.updatedBy =:updatedById where f.id =:id ")
	public void deleteSingleFieldCustomization(@Param("deleted") Boolean deleted,
			@Param("updatedById") Integer updatedById, @Param("id") int id);

	/**
	 * @param name
	 * @param deleted
	 * @return
	 */
	@Query("select f from FieldCustomization f where f.code=:code and f.userId=:userId")
	public FieldCustomization findByCodeAndUser(@Param("code") String code, @Param("userId") int userId);

}

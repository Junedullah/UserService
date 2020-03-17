/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryFieldValidation 
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

import com.ss.model.FieldValidation;


@Repository("repositoryFieldValidation")
public interface RepositoryFieldValidation extends JpaRepository<FieldValidation, Integer> {

	/**
	 * @param fieldId
	 * @param deleted
	 * @return
	 */
	List<FieldValidation> findByFieldFieldIdAndIsDeleted(Integer fieldId, Boolean deleted);
	
	List<FieldValidation> findByFieldFieldCodeAndIsDeleted(String fieldCode, Boolean deleted);

}

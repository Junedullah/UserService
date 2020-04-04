/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Mar 30, 2020
 * Modified on: Mar 30, 2020 11:19:38 AM
 * @author Juned Baig
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.CommonConstant;

@Repository("repositoryCommonConstant")
public interface RepositoryCommonConstant extends JpaRepository<CommonConstant, Integer> {

	/**
	 * @param deleted
	 * @return
	 */
	public List<CommonConstant> findByIsDeletedAndLanguageLanguageId(Boolean deleted,Integer langId);
	
	public CommonConstant findByConstantShortAndIsDeletedAndLanguageLanguageId(String constantShort,Boolean deleted,Integer langId);

	Long countByIsDeletedAndLanguageLanguageId(Boolean deleted,Integer langId);
}

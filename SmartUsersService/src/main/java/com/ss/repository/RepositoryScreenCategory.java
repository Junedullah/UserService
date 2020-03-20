/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryScreenCategory 
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.model.ScreenCategory;

@Repository("repositoryScreenCategory")
public interface RepositoryScreenCategory extends JpaRepository<ScreenCategory, Integer> {
	
	List<ScreenCategory> findByIsDeleted(boolean isDeleted);
	
	List<ScreenCategory> findByModuleModuleIdAndIsDeleted(Integer moduleId, boolean isDeleted);

	@Query("Select count(*) from ScreenCategory")
	Integer getCountOfTotal();

	ScreenCategory findByScreenCategoryIdAndIsDeleted(Integer screenCategoryId, boolean b);
}

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

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Field;
import com.ss.model.ScreenCategory;
import com.ss.service.ServiceScreenCategory;

@Repository("repositoryScreenCategory")
public interface RepositoryScreenCategory extends JpaRepository<ScreenCategory, Integer> {
	
	List<ScreenCategory> findByIsDeleted(boolean isDeleted);
	
	List<ScreenCategory> findByModuleModuleIdAndIsDeleted(Integer moduleId, boolean isDeleted);


	 @Query("select sc from ScreenCategory sc where sc.isDeleted = false and sc.screenCategoryId =:screenCategoryId")
	    public ScreenCategory findByIdAndIsDeleted(@Param("screenCategoryId")Integer screenCategoryId);
    
		@Query("select count(*) from ScreenCategory sc where sc.isDeleted=false")
        Integer getCountOfTotalScreenCategory();

		List<ScreenCategory> findByIsDeleted(boolean b, Pageable pageable);

		List<ScreenCategory> findByIsDeletedOrderByCreatedDateDesc(boolean b);


		   @Query("select sc from ScreenCategory sc where sc.screenCategoryId=:screenCategoryId and sc.isDeleted=false")
		    public List<ScreenCategory> findBygridIdAndIsDeleted(@Param("screenCategoryId") List<Integer> fieldId);
		   
		    ScreenCategory findOne(Integer planId);
		    @Modifying(clearAutomatically = true)
		    @Transactional
		    @Query("update ScreenCategory sc set sc.isDeleted =:deleted ,sc.updatedBy =:updateById where sc.screenCategoryId =:screenCategoryId ")
		    public void deleteSingleScreenCategory(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
		                                    @Param("screenCategoryId") Integer screenCategoryId);

			    @Query("select sc from ScreenCategory sc where (sc.isDeleted = false or  sc.isDeleted = null) and screenCategoryId =:screenCategoryId")
	            public ScreenCategory findByAndIsDeleted(@Param("screenCategoryId")int screenCategoryId);
	    

}

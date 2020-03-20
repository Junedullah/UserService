/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryScreenMenu 
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.ScreenMenu;


@Repository("repositoryScreenMenu")
public interface RepositoryScreenMenu extends JpaRepository<ScreenMenu, Integer> {

	@Query (" select DISTINCT s.sideMenu, s.sideMenuURL, s.company.companyId, s.createdBy, s.screenCategory.screenCategoryCode from  ScreenMenu s  where s.isDeleted=false and "
			+ "s.module.moduleId=:moduleId and s.sideMenu is not null and s.language.languageId=:languageId and s.screenCategory.screenCategoryCode =:screenCategoryCode" )
	List<Object[]> getSideBarDetailsByModuleId(@Param("moduleId") Integer moduleId,@Param("languageId") int languageId, @Param("screenCategoryCode") String screenCategoryCode);
	
	@Query (" select DISTINCT s.sideMenu, s.sideMenuURL, s.company.companyId, s.createdBy, s.screenCategory.screenCategoryCode from  ScreenMenu s  where s.isDeleted=false and "
			+ "s.module.moduleId=:moduleId and s.sideMenu is not null and s.language.languageId=:languageId and s.screenCategory.screenCategoryCode =:screenCategoryCode and s.company.companyId =:companyId and s.createdBy =:userId" )
	List<Object[]> getSideBarDetailsByModuleIdAndCompanyIdAndUserId(@Param("moduleId") Integer moduleId, @Param("languageId") int languageId, @Param("screenCategoryCode") String screenCategoryCode, @Param("userId") int userId, @Param("companyId") int companyId);
	
	@Query (" select s from  ScreenMenu s  where s.isDeleted=false and "
			+ "s.module.moduleId=:moduleId and s.sideMenu is not null and s.language.languageId=:languageId and s.company.companyId =:companyId and s.user.userId =:userId and s.sideMenuURL =:sideMenuUrl" )
	ScreenMenu findScreenMenuByUserIdAndCompanyIdAndModuleIdAndLangId(@Param("moduleId") Integer moduleId, @Param("languageId") int languageId, @Param("userId") int userId, @Param("companyId") int companyId, @Param("sideMenuUrl") String sideMenuUrl);

	/**
	 * @param screenMenuId
	 * @param deleted
	 * @return
	 */
	ScreenMenu findByScreenMenuIdAndIsDeleted(Integer screenMenuId, boolean deleted);

	@Query("Select count(*) from ScreenMenu")
	Integer getCountOfTotal();

}

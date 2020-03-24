package com.ss.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Screen;
import com.ss.model.ScreenMenu;

/**
 * Description: Interface for RepositoryScreenMenu
 * Name of Project: BTI
 * Created on: July 23, 2018
 * Modified on: July 23, 2018 4:19:38 PM
 * @author Elegant
 * Version: 1.0
 */

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
	Integer getCountOfTotalScreen();

	
	List<ScreenMenu> findByIsDeleted(boolean b, Pageable pageable);

	List<ScreenMenu> findByIsDeletedOrderByCreatedDateDesc(boolean b);

	@Modifying(clearAutomatically = true)
    @Transactional
    @Query("update ScreenMenu sm set sm.isDeleted =:deleted ,sm.updatedBy =:userId where sm.screenMenuId =:screenMenuId ")
	void deleteSingleScreen(@Param("deleted") boolean b,@Param("userId") int userId,@Param("screenMenuId") Integer screenMenuId);

	@Query("select sm from ScreenMenu sm where sm.screenMenuId =:screenMenuId and sm.isDeleted=false")
	List<ScreenMenu> findByScreenMenuIdAndIsDeleted(@Param("screenMenuId")List<Integer> ids);

	@Query("select sm from ScreenMenu sm where sm.isDeleted = false and sm.screenMenuId =:screenMenuId")
	ScreenMenu findByAndIsDeleted(@Param("screenMenuId") Integer screenMenuId);


	

}

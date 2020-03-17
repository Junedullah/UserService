/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryScreen 
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

import com.ss.model.Screen;


@Repository("repositoryScreen")
public interface RepositoryScreen extends JpaRepository<Screen, Integer> {

	/**
	 * @param moduleId
	 * @param screenId
	 * @return
	 */
	Screen findByModuleModuleIdAndScreenId(int moduleId, int screenId);

	/**
	 * @param screenCode
	 * @param delted
	 * @param moduleId
	 * @return
	 */
	Screen findByScreenCodeAndIsDeletedAndModuleModuleId(String screenCode, boolean delted, int moduleId);

	/**
	 * @param isDelted
	 * @param moduleId
	 * @return
	 */
	List<Screen> findByIsDeletedAndModuleModuleId(boolean isDelted, int moduleId);
	
	@Query (" select DISTINCT s.sideMenu, s.sideMenuURL from  Screen s  where s.isDeleted=false and "
			+ "s.module.moduleId=:moduleId and s.sideMenu is not null and s.language.languageId=:languageId")
	List<Object[]> getSideBarDetailsByModuleId(@Param("moduleId") Integer moduleId,@Param("languageId") int languageId);
	 
	 @Query (" from  Screen s  where s.isDeleted=false and s.module.moduleId=:moduleId and s.sideMenu is not null")
//	 @Query (" from  Screen s  where s.isDeleted=false and s.module.moduleId=:moduleId and s.sideMenu is not null  group by s.sideMenu")
	List<Screen> getSideBarDetailsByModuleId(@Param("moduleId") Integer moduleId);
	 
	Screen findByScreenCodeAndIsDeletedAndLanguageLanguageId(String screenCode, boolean delted, int langId);

	List<Screen> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

	Screen findByScreenIdAndIsDeleted(int screenId, boolean b);

	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);

}

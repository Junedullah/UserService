/**
 * SST - SMART SOFTWARE TECH.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Module;



/**
 * Description: Interface for RepositoryModule 
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Repository("repositoryModule")
public interface RepositoryModule extends JpaRepository<Module, Integer> {

	/**
	 * @param moduleCode
	 * @param deleted
	 * @return
	 */
	public Module findByModuleCodeAndIsDeleted(String moduleCode, Boolean deleted);

	/**
	 * @param deleted
	 * @return
	 */
	List<Module> findByIsDeleted(Boolean deleted);

	/**
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	List<Module> findByIsDeleted(Boolean deleted, Pageable pageable);
	
	List<Module> findByIsDeletedAndIsActive(Boolean deleted, Boolean isActive);

	List<Module> findByIsDeletedAndIsActive(Boolean deleted, Boolean isActive, Pageable pageable);

	List<Module> findByIsDeletedAndIsActiveAndLanguageLanguageId(Boolean deleted, Boolean isActive, int langId, Pageable pageable);

	List<Module> findByIsDeletedAndIsActiveAndLanguageLanguageId(Boolean deleted, Boolean isActive, int languageId);

	public abstract Module findByModuleIdAndIsDeletedAndIsActive(Integer paramInteger, Boolean deleted, Boolean isActive);

	public Module findByModuleCodeAndIsDeletedAndLanguageLanguageId(String moduleCode, Boolean deleted, int langId);

	public Module findByModuleCodeAndIsDeletedAndIsActiveAndLanguageLanguageId(String moduleCode, Boolean deleted, Boolean isActive, int languageId);

	public List<Module> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

	public Module findByModuleIdAndIsDeleted(int parseInt, boolean b);

	public Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);

	@Query("select m from Module m where m.isDeleted = false and m.moduleId =:moduleId")
	public Module findByModuleIdAndIsDeleted(@Param("moduleId")Integer moduleId);
	

	@Query("select count(*) from Module m")
	public Integer getCountOfTotalModule();

	public List<Module> findByIsDeletedOrderByCreatedDateDesc(boolean b);

}

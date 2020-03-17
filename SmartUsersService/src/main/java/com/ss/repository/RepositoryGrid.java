/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryGrid 
 * Name of Project: SmartSoftware
 * Created on: March 21, 2020
 * Modified on: March 21, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Grid;

@Repository("repositoryGrid")
public interface RepositoryGrid extends JpaRepository<Grid, Integer> {

	@Query("select g from Grid g where g.gridId =:gridId and g.isDeleted=false")
	public List<Grid> findByGridIdAndIsDeleted(@Param("gridId") Integer gridId);
	
	public List<Grid> findByIsDeleted(Boolean deleted, Pageable pageable);
	
	@Query("select count(*) from Grid g where g.isDeleted=false")
	public Integer getCountOfTotalGrid();
	
	@Query("select g from Grid g where g.screenId.id =:screenId and g.moduleId.id =:moduleId and g.isDeleted=false")
	public List<Grid> findByScreenIdAndModuleId(@Param("screenId") Integer screenId, @Param("moduleId") Integer moduleId);
	
}

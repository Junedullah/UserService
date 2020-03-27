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

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.Grid;
import com.ss.model.Module;

@Repository("repositoryGrid")
public interface RepositoryGrid extends JpaRepository<Grid, Integer> {

	@Query("select g from Grid g where g.gridId =:gridId and g.isDeleted=false")
	public List<Grid> findByGridIdAndIsDeleted(@Param("gridId") Integer gridId);
	
	public List<Grid> findByIsDeleted(Boolean deleted, Pageable pageable);
	
	@Query("select count(*) from Grid g where g.isDeleted=false")
	public Integer getCountOfTotalGrid();
	
	
	
	 @Query("select g from Grid g where g.isDeleted = false and g.id =:id")
	    public Grid findByIdAndIsDeleted(@Param("id")Integer id);

	public List<Grid> findByIsDeletedOrderByCreatedDateDesc(boolean b);
	
	 @Query("select g from Grid g where g.gridId=:gridId and g.isDeleted=false")
	    public List<Grid> findBygridIdAndIsDeleted(@Param("gridId") List<Integer> gridId);
	 
	    Grid findOne(Integer planId);
	    @Modifying(clearAutomatically = true)
	    @Transactional
	    @Query("update Grid g set g.isDeleted =:deleted ,g.updatedBy =:updateById where g.gridId =:gridId ")
	    public void deleteSingleGrids(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
	                                    @Param("gridId") Integer gridId);

	    @Query("select g from Grid g where (g.isDeleted = false or  g.isDeleted = null) and id =:id")
	    public	Grid findByAndIsDeleted(@Param("id")int id);
	    
	 

	 
}

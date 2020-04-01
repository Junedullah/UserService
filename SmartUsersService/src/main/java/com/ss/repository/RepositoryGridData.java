/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryGridData 
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

import com.google.protobuf.Field;
import com.ss.model.Grid;
import com.ss.model.GridData;

@Repository("repositoryGridData")
public interface RepositoryGridData extends JpaRepository<GridData, Integer> {

	public GridData findByGridDataIdAndIsDeleted(int id, boolean deleted);
	
	/*@Query("select gd from GridData gd where gd.id =:id and gd.isDeleted=false")
	public GridData findByFieldFieldIdAndIsDeleted(@Param("id") Integer id);*/

	public List<GridData> findByIsDeleted(Boolean deleted, Pageable pageable);

	@Query("select count(*) from GridData gd where gd.isDeleted=false")
	public Integer getCountOfTotalGridData();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update GridData gd set gd.isVisible =:visible, gd.updatedBy =:updateById, gd.colOrder =:colOrder where gd.field.fieldId =:fieldId and gd.createdBy =:userId")
	void changeStatus(@Param("visible") Boolean deleted, @Param("updateById") Integer updateById, @Param("fieldId") Integer fieldId, @Param("colOrder") Integer colOrder, @Param("userId") Integer userId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update GridData gd set gd.isReset = false where gd.field.fieldId =:fieldId and gd.createdBy =:userId")
	void changeStatusReset(@Param("fieldId") Integer fieldId, @Param("userId") Integer userId);
	
	@Query("select gd from GridData gd where gd.grid.id =:gridId and gd.createdBy =:userId and gd.isReset = false")
	public List<GridData> findByGridIdAndIsReset(@Param("gridId") Integer gridId, @Param("userId") Integer userId);
	
	@Query("select g.field from GridData g where g.grid.gridId =:gridId")
	public List<Field> getAllFiledByGridId(@Param("gridId")Integer gridId);
	
	@Query("select gd from GridData gd where gd.field.fieldId =:fieldId and gd.createdBy =:userId and gd.isDeleted=false")
	public GridData findByFieldFieldIdAndIsDeleted(@Param("fieldId") Integer fieldId, @Param("userId") Integer userId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update GridData gd set gd.isVisible = false where gd.grid.id =:gridId and gd.createdBy =:createdBy")
	void hideAllColumns(@Param("createdBy") Integer createdBy, @Param("gridId") Integer gridId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update GridData gd set gd.isVisible = true where gd.grid.id =:gridId and gd.createdBy =:createdBy")
	void showAllColumns(@Param("createdBy") Integer createdBy, @Param("gridId") Integer gridId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update GridData gd set gd.isReset = true where gd.grid.id =:gridId and gd.createdBy =:createdBy")
	void resetGrid(@Param("createdBy") Integer createdBy, @Param("gridId") Integer gridId);

	@Query("select gd from GridData gd where gd.screen.screenId =:screenId and gd.screen.isDeleted=false and gd.module.moduleId =:moduleId and gd.module.isDeleted=false and gd.grid.id =:gridId and gd.isReset=false")
	public List<GridData> findByScreenIdAndModuleIdAndGridIdAndIsReset(@Param("screenId") Integer screenId, @Param("moduleId") Integer moduleId, @Param("gridId") Integer gridId);

	 @Query("select gd from GridData gd where gd.isDeleted = false and gd.gridDataId =:gridDataId")
	    public GridData findByIdAndIsDeleted(@Param("gridDataId")Integer gridDataId);

		@Query("select count(*) from GridData gd where gd.isDeleted=false")
		public Integer getCountOfTotalGridDta();

		public List<GridData> findByIsDeletedOrderByCreatedDateDesc(boolean b);

		 @Query("select gd from GridData gd where gd.gridDataId=:gridDataId and gd.isDeleted=false")
		    public List<GridData> findBygridIdAndIsDeleted(@Param("gridDataId") List<Integer> gridDataId);

		    GridData findOne(Integer planId);
		    @Modifying(clearAutomatically = true)
		    @Transactional
		    @Query("update GridData gd set gd.isDeleted =:deleted ,gd.updatedBy =:updateById where gd.gridDataId =:gridDataId ")
		    public void deleteSingleGridData(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
		                                    @Param("gridDataId") Integer gridDataId);

		    @Query("select gd from GridData gd where (gd.isDeleted = false or  gd.isDeleted = null) and gridDataId =:gridDataId")
		    public	GridData findByAndIsDeleted(@Param("gridDataId")int gridDataId);
		 
		 		}
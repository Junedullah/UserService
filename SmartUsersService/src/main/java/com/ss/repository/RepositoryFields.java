/**
/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryFields 
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

import com.ss.model.Field;



@Repository("repositoryFields")
public interface RepositoryFields extends JpaRepository<Field, Integer> {

	/**
	 * @param screenId
	 * @param screenDeleted
	 * @param fieldDeleted
	 * @return
	 */
	List<Field> findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(int screenId, boolean screenDeleted,
			boolean fieldDeleted);

	List<Field> findByGridGridIdAndIsDeleted(Integer gridId, boolean deleted);
	
	Field findByFieldCodeAndLanguageLanguageId(String fieldCode, int langId);

	List<Field> findByIsDeletedAndLanguageLanguageId(boolean b, int i);

	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);

	public Field findByFieldIdAndIsDeleted(int id, boolean deleted);

	public List<Field> findByIsDeleted(Boolean deleted, Pageable pageable);

	@Query("select count(*) from Field d where d.isDeleted=false")
	public Integer getCountOfTotalField();

	public List<Field> findByIsDeletedOrderByCreatedDateDesc(Boolean deleted);

	@Query(value = "select * from fields where fields.field_id in"
			+ "(select field_id from field_company where field_company.company_id=:companyIds) and is_deleted=false", nativeQuery = true)
	List<Field> findByCompanyId(@Param("companyIds") int companyIds);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Field d set d.isMandatory =:deleted ,d.updatedBy =:updateById where d.id =:id ")
	void changeStatus(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
			@Param("id") Integer id);

	@Query("select d from Field d where d.id not in (:fieldIds) and is_deleted=false")
	List<Field> findByRemainFieldId(@Param("fieldIds")List<Integer> fieldIds);

	/**
	 * @param isDelted
	 * @param moduleId
	 * @return
	 */
	@Query(value = "select * from fields where fields.screen_id =:screenId and fields.field_id in"
			+ "(select field_id from field_company where field_company.company_id=:companyId) and is_deleted=:isDeleted and is_mandatory IS NOT NULL", nativeQuery = true)
	List<Field> findByIsDeletedAndScreenScreenIdAndCompanyId(@Param("isDeleted") Boolean isDeleted, @Param("screenId") int screenId, @Param("companyId") int companyId);
	
	@Query("select f from Field f where f.grid.id =:gridId and f.isDeleted = false and f.fieldId NOT IN (select g.fieldId.fieldId from GridData g where g.isReset = false and g.createdBy =:userId)")
	List<Field> findByGridIdAndNotInGridData(@Param("gridId") Integer gridId, @Param("userId") Integer userId);
}

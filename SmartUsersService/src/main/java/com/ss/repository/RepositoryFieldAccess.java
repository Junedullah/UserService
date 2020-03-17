/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryFieldAccess 
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

import com.ss.model.FieldAccess;


@Repository("repositoryFieldAccess")
public interface RepositoryFieldAccess extends JpaRepository<FieldAccess, Integer> {

	/**
	 * 
	 * @param id
	 * @param deleted
	 * @return
	 */
	public FieldAccess findByFieldAccessIdAndIsDeleted(int id, boolean deleted);

	/**
	 * 
	 * @param company
	 * @param deleted
	 * @return
	 *//*
	public FieldAccess findByCompanyAndIsDeleted(int id, boolean deleted);*/
	/**
	 * 
	 * @param deleted
	 * @return
	 */
	public List<FieldAccess> findByIsDeleted(Boolean deleted);

	/**
	 * 
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<FieldAccess> findByIsDeleted(Boolean deleted, Pageable pageable);

	@Query("select p from FieldAccess p where (p.company.id =:companyIds) and p.isDeleted=false")
	public List<FieldAccess> findFieldIdByCompanyId(@Param("companyIds") int companyIds);
	
	/**
	 * 
	 * @return
	 */
	@Query("select count(*) from FieldAccess a where a.isDeleted=false")
	public Integer getCountOfTotalFieldAccess();

	/**
	 * 
	 * @param deleted
	 * @param idList
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update FieldAccess d set d.isDeleted =:deleted, d.updatedBy=:updateById where d.id IN (:idList)")
	public void deleteFieldAccess(@Param("deleted") Boolean deleted, @Param("idList") List<Integer> idList,
			@Param("updateById") Integer updateById);

	/**
	 * 
	 * @param deleted
	 * @param updateById
	 * @param id
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update FieldAccess d set d.isDeleted =:deleted ,d.updatedBy =:updateById where d.id =:id ")
	public void deleteSingleFieldAccess(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
			@Param("id") Integer id);

	/**
	 * 
	 * @return
	 */
	public FieldAccess findTop1ByOrderByFieldAccessIdDesc();

	@Query("select p from FieldAccess p where (p.fieldAccessId =:fieldAccessId) and p.isDeleted=false")
	public List<FieldAccess> findByFieldAccessId(@Param("fieldAccessId") int fieldAccessId);

	/*@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update FieldAccess d set d.mandatory =:deleted or d.language =:langId ,d.updatedBy =:updateById where d.id =:id ")
	public void changeStatus(@Param("deleted") Boolean deleted, @Param("updateById") Integer updateById,
			@Param("id") Integer id, @Param("langId") Integer langId);*/

	
	/**
	 * @param screenId
	 * @param screenDeleted
	 * @param fieldDeleted
	 * @return
	 */
	List<FieldAccess> findByScreenScreenIdAndScreenIsDeletedAndIsDeleted(int screenId, boolean screenDeleted,
			boolean fieldDeleted);
	
	@Query("select p from FieldAccess p where (p.company.companyId =:companyId) and p.isDeleted=false")
	List<FieldAccess> findByCompanyCompanyIdAndScreenScreenIdAndScreenIsDeletedAndIsDeleted(@Param("companyId")Integer companyId);
	
	
	public FieldAccess findByCompanyCompanyIdAndFieldFieldIdAndIsDeleted(int companyId, int fieldId, boolean deleted);
	
	public FieldAccess findByModuleModuleIdAndCompanyCompanyIdAndFieldFieldIdAndLanguageLanguageIdAndIsDeleted(int moduleId, int companyId, int fieldId, int languageId, boolean deleted);
	
	public FieldAccess findByModuleModuleIdAndScreenScreenIdAndCompanyCompanyIdAndFieldFieldIdAndLanguageLanguageIdAndIsDeleted(int moduleId, int screenId, int companyId, int fieldId, int languageId, boolean deleted);

	@Query("select p from FieldAccess p where (p.company.companyId =:companyId) and p.isDeleted=false")
	public List<FieldAccess> findbyCompanyId(@Param("companyId")Integer companyId);
	
	@Query("select p from FieldAccess p where (p.company.companyId =:companyId and p.screen.screenId =:screenId) and p.isDeleted=false")
	public List<FieldAccess> findScreenIdbyCompanyId(@Param("companyId")Integer companyId, @Param("screenId")Integer screenId);
	
//	@Query("select p from FieldAccess p where (p.company.companyId =:companyId and p.screen.screenId =:screenId) and p.isDeleted=false")
//	public List<FieldAccess> findFieldbyFieldIdCompanyId(@Param("fieldId")Integer fieldId, @Param("companyId")Integer companyId);
	
}

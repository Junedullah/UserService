/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryAccessRoleScreenRelation 
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
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

import com.ss.model.AccessRoleScreenRelation;


@Repository("repositoryAccessRoleScreenRelation")
public interface RepositoryAccessRoleScreenRelation extends JpaRepository<AccessRoleScreenRelation, Integer> {
	/**
	 * @param deleted
	 * @return
	 */
	public List<AccessRoleScreenRelation> findByIsDeleted(Boolean deleted);

	/**
	 * @param acessRoleId
	 * @param moduleId
	 * @param screenId
	 * @param fieldId
	 * @return
	 */
	public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdAndFieldFieldId(
			int acessRoleId, int moduleId, int screenId, int fieldId);

	/**
	 * @param moduleId
	 * @param accessRoleId
	 * @param screenId
	 * @param fieldId
	 * @param deleted
	 * @return
	 */
	public AccessRoleScreenRelation findByAccessRoleModuleRelationModuleModuleIdAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndScreenScreenIdAndFieldFieldIdAndIsDeleted(
			int moduleId, int accessRoleId, int screenId, int fieldId, boolean deleted);

	/**
	 * @param screenId
	 * @param readAccess
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<AccessRoleScreenRelation> findByScreenScreenIdAndReadAccessAndIsDeleted(int screenId,
			boolean readAccess, boolean deleted, Pageable pageable);

	/**
	 * @param screenId
	 * @param writeAccess
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<AccessRoleScreenRelation> findByScreenScreenIdAndWriteAccessAndIsDeleted(int screenId,
			boolean writeAccess, boolean deleted, Pageable pageable);

	/**
	 * @param screenId
	 * @param deleteAccess
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	public List<AccessRoleScreenRelation> findByScreenScreenIdAndDeleteAccessAndIsDeleted(int screenId,
			boolean deleteAccess, boolean deleted, Pageable pageable);
	
	public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdIsNullAndFieldFieldIdIsNull(
			int acessRoleId, int moduleId);
	
	public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleCodeAndScreenScreenIdIsNullAndFieldFieldIdIsNull(
			int acessRoleId, String moduleCode);
	
	/*public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleId(
			int acessRoleId, int moduleId);*/
	
	/*public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenId(
			int acessRoleId, int moduleId, int screenId);*/
	
	public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleIdAndScreenScreenIdAndFieldFieldIdIsNull(
			int acessRoleId, int moduleId, int screenId);
	
	public AccessRoleScreenRelation findByAccessRoleModuleRelationAccessRoleAccessRoleIdAndAccessRoleModuleRelationModuleModuleCodeAndScreenScreenCodeAndFieldFieldIdIsNull(
			int acessRoleId, String moduleCode, String screenCode);
	
	@Query("Select DISTINCT arsr.screen.screenId from AccessRoleScreenRelation arsr where arsr.isDeleted=false and arsr.screen.screenCode=:screenCode and arsr.accessRoleModuleRelation.accessRoleModuleRelationId=:accessRoleModuleRelationId")
	public Integer getDistnictScreenIdByAccessRoleModuleRelationIdAndScreenCode(@Param("accessRoleModuleRelationId") Integer accessRoleModuleRelationId,@Param("screenCode") String screenCode);
	
	@Query("Select DISTINCT arsr.screen.screenId from AccessRoleScreenRelation arsr where arsr.isDeleted=false and arsr.screen.screenCode=:screenCode and arsr.accessRoleModuleRelation.accessRoleModuleRelationId=:accessRoleModuleRelationId and arsr.screen.module.moduleCode=:moduleCode")
	public Integer getDistnictScreenIdByAccessRoleModuleRelationIdAndScreenCodeAndModuleCode(@Param("accessRoleModuleRelationId") Integer accessRoleModuleRelationId,@Param("screenCode") String screenCode , @Param("moduleCode") String moduleCode);
	
	@Query("from AccessRoleScreenRelation arsr where arsr.isDeleted=false and arsr.screen.screenId=:screenId and arsr.accessRoleModuleRelation.accessRoleModuleRelationId=:accessRoleModuleRelationId ")
	public List<AccessRoleScreenRelation> getListOfFieldByAccessRoleModuleRelationIdAndScreenId(@Param("accessRoleModuleRelationId") Integer accessRoleModuleRelationId , @Param("screenId") Integer screenId );
	
	@Query("from AccessRoleScreenRelation arsr where arsr.isDeleted=false and arsr.screen.screenCode=:screenCode and arsr.accessRoleModuleRelation.accessRoleModuleRelationId=:accessRoleModuleRelationId ")
	public List<AccessRoleScreenRelation> getListOfFieldByAccessRoleModuleRelationIdAndScreenCode(@Param("accessRoleModuleRelationId") Integer accessRoleModuleRelationId , @Param("screenCode") String screenCode );
	@Modifying
	@Transactional
	@Query("delete from AccessRoleScreenRelation arsr where arsr.accessRoleModuleRelation.accessRoleModuleRelationId=:accessRoleModuleRelationId ")
	public void  deleteByAccessRoleModuleRelation(@Param("accessRoleModuleRelationId") Integer accessRoleModuleRelationId );

	/*public AccessRoleScreenRelation findByAccessRoleModuleRelationModuleModuleCodeAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndIsDeleted(
			String moduleCode, int accessRoleId, boolean deleted);*/
	
	public AccessRoleScreenRelation findByAccessRoleModuleRelationModuleModuleCodeAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndScreenScreenCodeAndFieldFieldCodeAndIsDeleted(
			String moduleCode, int accessRoleId, String screenCode, String fieldCode, boolean deleted);

	public List<AccessRoleScreenRelation> findByAccessRoleModuleRelationModuleModuleCodeAndAccessRoleModuleRelationAccessRoleAccessRoleIdAndIsDeleted(
			String moduleCode, int accessRoleId, boolean b);
	
}

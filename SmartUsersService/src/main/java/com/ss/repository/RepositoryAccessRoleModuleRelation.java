/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryAccessRoleModuleRelation 
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ss.model.AccessRoleModuleRelation;



@Repository("repositoryAccessRoleModuleRelation")
public interface RepositoryAccessRoleModuleRelation extends JpaRepository<AccessRoleModuleRelation, Integer> {
	/**
	 * @param accessroleId
	 * @param moduleId
	 * @param deleted
	 * @return
	 */
	  AccessRoleModuleRelation findByAccessRoleAccessRoleIdAndModuleModuleIdAndIsDeleted(int accessroleId, int moduleId,
			boolean deleted);
	  
	  
	  AccessRoleModuleRelation findByAccessRoleAccessRoleIdAndModuleModuleCodeAndIsDeleted(int accessroleId, String moduleCode,
				boolean deleted);
	
	  @Query (" from  AccessRoleModuleRelation armr  where armr.isDeleted=false and armr.module.moduleCode=:moduleCode and armr.accessRole.accessRoleId In "
		  		+ "( Select rgar.accessRole.accessRoleId from RoleGroupAccessRole rgar where "
		  		+ " rgar.isDeleted=false and rgar.accessRole.isDeleted=false and rgar.roleGroup.isDeleted=false and rgar.roleGroup.roleGroupId IN  "
		  		+ "( Select DISTINCT ugrg.roleGroup.roleGroupId  from UserGroupRoleGroup  ugrg where "
		  		+ " ugrg.isDeleted=false and ugrg.roleGroup.isDeleted=false and ugrg.userGroup.isDeleted=false  and "
		  		+ " ugrg.userGroup.userGroupId =:userGroupId )) ")
	      AccessRoleModuleRelation getModulesByAccessRolesOfUserAndModuleCode(@Param("userGroupId") Integer userGroupId,@Param("moduleCode") String moduleCode);
		  
	  @Query (" from  AccessRoleModuleRelation armr  where armr.isDeleted=false and armr.isActive=true and armr.module.isActive=true and armr.accessRole.accessRoleId In  "
		  		+ "( Select rgar.accessRole.accessRoleId from RoleGroupAccessRole rgar where "
		  		+ " rgar.isDeleted=false and rgar.accessRole.isDeleted=false and rgar.roleGroup.isDeleted=false and rgar.roleGroup.roleGroupId IN  "
		  		+ "( Select DISTINCT ugrg.roleGroup.roleGroupId  from UserGroupRoleGroup  ugrg where "
		  		+ " ugrg.isDeleted=false and ugrg.roleGroup.isDeleted=false and ugrg.userGroup.isDeleted=false  and "
		  		+ " ugrg.userGroup.userGroupId =:userGroupId )) ")
	     List<AccessRoleModuleRelation> getModulesByAccessRolesOfUserAndIsActive(@Param("userGroupId") Integer userGroupId);
	  
}

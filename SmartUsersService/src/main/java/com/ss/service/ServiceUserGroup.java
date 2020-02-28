/**SmartSoftware User - Service */
/**
 * Description: ServiceUserGroup
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.constant.SmartCodeType;
import com.ss.model.AccessRole;
import com.ss.model.RoleGroup;
import com.ss.model.RoleGroupAccessRole;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserDetail;
import com.ss.model.UserGroup;
import com.ss.model.UserGroupRoleGroup;
import com.ss.model.dto.DtoAccessRole;
import com.ss.model.dto.DtoRoleGroup;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.model.dto.DtoUserGroup;
import com.ss.repository.RepositoryRoleGroup;
import com.ss.repository.RepositoryRoleGroupAccessRole;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserGroup;
import com.ss.repository.RepositoryUserGroupRoleGroup;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;


@Service("serviceUserGroup")
@Transactional
public class ServiceUserGroup {

	private static final Logger LOGGER = Logger.getLogger(ServiceUserGroup.class);
	@Autowired
	RepositoryUserGroup repositoryUserGroup;

	@Autowired
	RepositoryUserGroupRoleGroup repositoryUserGroupRoleGroup;

	@Autowired
	RepositoryRoleGroup repositoryRoleGroup;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired
	RepositoryRoleGroupAccessRole repositoryRoleGroupAccessRole;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;
	
	private static final String USER_ID = "userid";

	/**
	 * Description: get all user group detail list
	 * 
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getAllUserGroup(DtoSearch dtoSearch) {
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryUserGroup.getCountOfTotalUserGroup());
		List<UserGroup> listUserGroup = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC,
					"createdDate");
			listUserGroup = this.repositoryUserGroup.findByIsDeleted(false, pageable);
		} else {
			listUserGroup = repositoryUserGroup.findByIsDeletedOrderByCreatedDateDesc(false);
		}

		List<DtoUserGroup> dtoUserGroups = new ArrayList<>();
		DtoUserGroup dtoUserGroup = null;
		if (listUserGroup != null) {
			for (UserGroup userGroup : listUserGroup) {
				List<Integer> id = new ArrayList<>();
				List<String> name = new ArrayList<>();
				dtoUserGroup = new DtoUserGroup(userGroup);
				List<UserGroupRoleGroup> userGroupRoleGroupList = repositoryUserGroupRoleGroup
						.findByUserGroupUserGroupIdAndIsDeleted(userGroup.getUserGroupId(), false);
				if (userGroupRoleGroupList != null) {
					List<DtoRoleGroup> listDtoRoleGroups = new ArrayList<>();
					for (UserGroupRoleGroup userGroupRoleGroup : userGroupRoleGroupList) {
						id.add(userGroupRoleGroup.getRoleGroup().getRoleGroupId());
						name.add(userGroupRoleGroup.getRoleGroup().getRoleGroupName());
						DtoRoleGroup dtoRoleGroup = new DtoRoleGroup(userGroupRoleGroup.getRoleGroup());
						List<DtoAccessRole> roleGroupRoleList = new ArrayList<>();
						DtoAccessRole dtoApplicationRole = null;
						AccessRole accessRole = null;
						List<RoleGroupAccessRole> roleGroupApplicationRoleList = this.repositoryRoleGroupAccessRole
								.findByRoleGroupRoleGroupIdAndIsDeleted(
										userGroupRoleGroup.getRoleGroup().getRoleGroupId(), false);
						for (RoleGroupAccessRole roleGroupApplicationRole : roleGroupApplicationRoleList) {
							accessRole = roleGroupApplicationRole.getAccessRole();
							if (accessRole != null) {
								dtoApplicationRole = new DtoAccessRole(accessRole);
								roleGroupRoleList.add(dtoApplicationRole);

							}
							dtoRoleGroup.setRoleGroupRoleList(roleGroupRoleList);
						}
						listDtoRoleGroups.add(dtoRoleGroup);
					}
					dtoUserGroup.setListRoleGroup(listDtoRoleGroups);
				}
				dtoUserGroup.setRoleGroupIds(id);
				dtoUserGroup.setRoleGroupNames(name);
				dtoUserGroups.add(dtoUserGroup);
			}
		}
		dtoSearch2.setRecords(dtoUserGroups);
		return dtoSearch2;
	}

	/**
	 * Description: get all user group list for drop down
	 * @param dtoSearch
	 * @return
	 */
	public DtoSearch getAllUserGroupForDropDown(DtoSearch dtoSearch) {
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoSearch.getPageNumber());
		dtoSearch2.setPageSize(dtoSearch.getPageSize());
		dtoSearch2.setTotalCount(repositoryUserGroup.getCountOfTotalUserGroup());
		List<UserGroup> listUserGroup = null;
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) {
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			listUserGroup = this.repositoryUserGroup.findByIsDeleted(false, pageable);
		} else {
			listUserGroup = repositoryUserGroup.findByIsDeleted(false);
		}

		List<DtoUserGroup> dtoUserGroups = new ArrayList<>();
		DtoUserGroup dtoUserGroup = null;
		if (listUserGroup != null) {
			for (UserGroup userGroup : listUserGroup) {
				dtoUserGroup = new DtoUserGroup();
				dtoUserGroup.setId(userGroup.getUserGroupId());
				if (UtilRandomKey.isNotBlank(userGroup.getGroupName())) {
					dtoUserGroup.setGroupName(userGroup.getGroupName());
				} else {
					dtoUserGroup.setGroupName("N/A");
				}
				dtoUserGroups.add(dtoUserGroup);
			}
		}
		dtoSearch2.setRecords(dtoUserGroups);
		return dtoSearch2;
	}

	/**
	 * Description: save user group detail
	 * @param dtoUserGroup
	 * @return
	 */
	public DtoUserGroup saveUserGroup(DtoUserGroup dtoUserGroup) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		UserGroup userGroup = new UserGroup();
		userGroup.setCreatedBy(loggedInUserId);
		String groupCode = codeGenerator.getGeneratedCode(SmartCodeType.USERGROUP.name());
		userGroup.setGroupCode(groupCode);
		userGroup.setGroupDesc(dtoUserGroup.getGroupDesc());
		userGroup.setGroupName(dtoUserGroup.getGroupName());

		userGroup = repositoryUserGroup.saveAndFlush(userGroup);
		StringBuilder roleGroupIds = new StringBuilder("") ;
		if (dtoUserGroup.getRoleGroupList() != null && !dtoUserGroup.getRoleGroupList().isEmpty()) {

			UserGroupRoleGroup userGroupRoleGroup = null;
			for (Integer roleId : dtoUserGroup.getRoleGroupList()) {

				userGroupRoleGroup = repositoryUserGroupRoleGroup
						.findByUserGroupUserGroupIdAndRoleGroupRoleGroupId(userGroup.getUserGroupId(), roleId);
				if (userGroupRoleGroup != null) {
					userGroupRoleGroup.setIsDeleted(false);
					userGroupRoleGroup.setUpdatedBy(loggedInUserId);
					repositoryUserGroupRoleGroup.saveAndFlush(userGroupRoleGroup);
				} else {
					RoleGroup roleGroup2 = repositoryRoleGroup.findByRoleGroupIdAndIsDeleted(roleId, false);
					userGroupRoleGroup = new UserGroupRoleGroup();
					userGroupRoleGroup.setUserGroup(userGroup);
					userGroupRoleGroup.setRoleGroup(roleGroup2);
					userGroupRoleGroup.setCreatedBy(loggedInUserId);
					repositoryUserGroupRoleGroup.saveAndFlush(userGroupRoleGroup);
				}

				// need comma separated ids
				if (roleGroupIds.toString().isEmpty()) {
					roleGroupIds.append(roleId.toString());
				} else {
					roleGroupIds.append("," + roleId.toString());
				}

			}

			dtoUserGroup.setId(userGroup.getUserGroupId());
			dtoUserGroup.setGroupCode(userGroup.getGroupCode());
		}
		 
		return dtoUserGroup;
	}

	/**
	 * Description: get user group detail by user group id
	 * @param groupId
	 * @return
	 */
	public DtoUserGroup getUserGroupDetailByGroupId(int groupId) {
		UserGroup userGroup = repositoryUserGroup.findByUserGroupIdAndIsDeleted(groupId, false);
		DtoUserGroup dtoUserGroup = null;
		List<Integer> roleGroupIds = new ArrayList<>();
		List<String> roleGroupNames = new ArrayList<>();
		if (userGroup != null) {
			dtoUserGroup = new DtoUserGroup(userGroup);
			List<UserGroupRoleGroup> userGroupRoleGroupList = repositoryUserGroupRoleGroup
					.findByUserGroupUserGroupIdAndIsDeleted(userGroup.getUserGroupId(), false);
			if (userGroupRoleGroupList != null) {
				List<DtoRoleGroup> listDtoRoleGroups = new ArrayList<>();
				for (UserGroupRoleGroup userGroupRoleGroup : userGroupRoleGroupList) {
					roleGroupIds.add(userGroupRoleGroup.getRoleGroup().getRoleGroupId());
					roleGroupNames.add(userGroupRoleGroup.getRoleGroup().getRoleGroupName());
					DtoRoleGroup dtoRoleGroup = new DtoRoleGroup(userGroupRoleGroup.getRoleGroup());
					List<DtoAccessRole> roleGroupRoleList = new ArrayList<>();
					DtoAccessRole dtoApplicationRole = null;
					AccessRole accessRole = null;
					List<RoleGroupAccessRole> roleGroupApplicationRoleList = this.repositoryRoleGroupAccessRole
							.findByRoleGroupRoleGroupIdAndIsDeleted(userGroupRoleGroup.getRoleGroup().getRoleGroupId(),
									false);
					for (RoleGroupAccessRole roleGroupApplicationRole : roleGroupApplicationRoleList) {
						accessRole = roleGroupApplicationRole.getAccessRole();
						if (accessRole != null) {
							dtoApplicationRole = new DtoAccessRole(accessRole);
							roleGroupRoleList.add(dtoApplicationRole);

						}
						dtoRoleGroup.setRoleGroupRoleList(roleGroupRoleList);
					}
					listDtoRoleGroups.add(dtoRoleGroup);
				}
				dtoUserGroup.setRoleGroupIds(roleGroupIds);
				dtoUserGroup.setRoleGroupNames(roleGroupNames);
				dtoUserGroup.setListRoleGroup(listDtoRoleGroups);
			}
		}
		return dtoUserGroup;
	}

	/**
	 * Description: deete user group detail (one or more)
	 * @param deleteGroup
	 */
	public boolean deleteUserGroup(List<Integer> deleteGroup) {
		boolean status = false;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		try {
			this.repositoryUserGroup.deleteUserGroups(deleteGroup, true, loggedInUserId);

			this.repositoryUserGroupRoleGroup.deleteAllRoleGroupUserGroupForMultipleUserGroups(deleteGroup, true,
					loggedInUserId);
 
			status = true;
		} catch (NumberFormatException e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}
		return status;
	}

	/**
	 * Description: update user group detail
	 * @param dtoUserGroup
	 * @return
	 */
	public boolean updateUserGroup(DtoUserGroup dtoUserGroup) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		UserGroup userGroup;
		boolean status = false;
		if (dtoUserGroup.getId() > 0) {
			userGroup = repositoryUserGroup.findByUserGroupIdAndIsDeleted(dtoUserGroup.getId(), false);
			if (userGroup != null) {
				userGroup.setGroupDesc(dtoUserGroup.getGroupDesc());
				userGroup.setGroupName(dtoUserGroup.getGroupName());
				userGroup.setUpdatedBy(loggedInUserId);
				userGroup = repositoryUserGroup.saveAndFlush(userGroup);
				StringBuilder roleGroupIds = new StringBuilder("");
				if (dtoUserGroup.getRoleGroupList() != null && !dtoUserGroup.getRoleGroupList().isEmpty()) {
					repositoryUserGroupRoleGroup.deleteAllUserGroupRoleGroups(userGroup.getUserGroupId(),
							loggedInUserId);
					UserGroupRoleGroup userGroupRoleGroup = null;
					for (Integer roleId : dtoUserGroup.getRoleGroupList()) {
						userGroupRoleGroup = repositoryUserGroupRoleGroup
								.findByUserGroupUserGroupIdAndRoleGroupRoleGroupId(userGroup.getUserGroupId(), roleId);
						if (userGroupRoleGroup != null) {
							userGroupRoleGroup.setIsDeleted(false);
							userGroupRoleGroup.setUpdatedBy(loggedInUserId);
							repositoryUserGroupRoleGroup.saveAndFlush(userGroupRoleGroup);
						} else {
							RoleGroup roleGroup2 = repositoryRoleGroup.findByRoleGroupIdAndIsDeleted(roleId, false);
							userGroupRoleGroup = new UserGroupRoleGroup();
							userGroupRoleGroup.setUserGroup(userGroup);
							userGroupRoleGroup.setRoleGroup(roleGroup2);
							userGroupRoleGroup.setCreatedBy(loggedInUserId);
							repositoryUserGroupRoleGroup.saveAndFlush(userGroupRoleGroup);
						}

						// need comma separated ids
						if (roleGroupIds.toString().isEmpty()) {
							roleGroupIds.append(roleId.toString());
						} else {
							roleGroupIds.append("," + roleId.toString());
						}
					}
				}
			 				status = true;
			}
		}
		return status;
	}
	
	/**
	 * Description: get user group detail list by company id
	 * @param companyId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<DtoUserGroup> getUserGroupListByCompanyId(Integer companyId) 
	{
		List<DtoUserGroup> dtoUserGroupList = null;
		Map<String, DtoUserGroup> userGroupMap = new HashMap<>();
		List<UserCompanyRelation> userCompanyRelationList = this.repositoryUserCompanyRelation
				.findByCompanyCompanyIdAndIsDeleted(companyId, false);
		if (userCompanyRelationList != null && !userCompanyRelationList.isEmpty()) 
		{
			UserGroup userGroup = null;
			for (UserCompanyRelation userCompanyRelation : userCompanyRelationList) 
			{
				userGroup = userCompanyRelation.getUserGroup();
				if (userGroup!=null && !userGroup.getIsDeleted())
				{
					userGroupMap.put(String.valueOf(userGroup.getUserGroupId()), new DtoUserGroup(userGroup));
				}
			}
		}
		
		dtoUserGroupList = new ArrayList<>(userGroupMap.values());
		return dtoUserGroupList;
	}

	/**
	 * Description: get all user list by user group id
	 * @param userGroupId
	 * @return
	 */
	public List<DtoUser> getUserListByUserGroupId(Integer userGroupId) {
		List<DtoUser> dtoUserList = null;
		List<UserCompanyRelation> userCompanyRelationsList = this.repositoryUserCompanyRelation
				.findByIsDeletedAndUserGroupUserGroupIdAndUserIsActive(false, userGroupId, true);
		if (userCompanyRelationsList != null && !userCompanyRelationsList.isEmpty()) {
			dtoUserList = new ArrayList<>();
			DtoUser dtoUser = null;
			for (UserCompanyRelation userCompanyRelation : userCompanyRelationsList) {
				User user = userCompanyRelation.getUser();
				UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				dtoUser = new DtoUser();
				dtoUser.setUserId(user.getUserId());
				if (userDetail != null) {
					if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
						dtoUser.setFirstName(userDetail.getFirstName());
					} else {
						dtoUser.setFirstName("");
					}

					if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
						dtoUser.setLastName(userDetail.getLastName());
					} else {
						dtoUser.setLastName("");
					}

					if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
						dtoUser.setMiddleName(userDetail.getMiddleName());
					} else {
						dtoUser.setMiddleName("");
					}

					dtoUserList.add(dtoUser);
				}
			}
		}
		return dtoUserList;
	}

	/**
	 * Description: search user group detail by search keyword
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchUserGroup(DtoSearch dtoSearch) {
		if (dtoSearch != null) {
			dtoSearch.setTotalCount(
					this.repositoryUserGroup.predictiveUserGroupSearchTotalCount(dtoSearch.getSearchKeyword()));
			List<UserGroup> userGroupList = this.repositoryUserGroup.predictiveUserGroupSearchWithPagination(
					dtoSearch.getSearchKeyword(),
					new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"));
			if (userGroupList != null && !userGroupList.isEmpty()) {
				List<DtoUserGroup> dtoUserGroupList = new ArrayList<>();
				DtoUserGroup dtoUserGroup = null;
				for (UserGroup userGroup : userGroupList) {
					List<Integer> roleGroupIds = new ArrayList<>();
					List<String> roleGroupNames = new ArrayList<>();
					dtoUserGroup = new DtoUserGroup(userGroup);
					List<UserGroupRoleGroup> userGroupRoleGroupList = repositoryUserGroupRoleGroup
							.findByUserGroupUserGroupIdAndIsDeleted(userGroup.getUserGroupId(), false);
					if (userGroupRoleGroupList != null) {
						List<DtoRoleGroup> listDtoRoleGroups = new ArrayList<>();
						for (UserGroupRoleGroup userGroupRoleGroup : userGroupRoleGroupList) 
						{
							DtoRoleGroup dtoRoleGroup =null;
							if(userGroupRoleGroup.getRoleGroup()!=null)
							{
								 roleGroupIds.add(userGroupRoleGroup.getRoleGroup().getRoleGroupId());
								 roleGroupNames.add(userGroupRoleGroup.getRoleGroup().getRoleGroupName());
								 dtoRoleGroup = new DtoRoleGroup(userGroupRoleGroup.getRoleGroup());
								 List<DtoAccessRole> roleGroupRoleList = new ArrayList<>();
								 DtoAccessRole dtoApplicationRole = null;
								 AccessRole accessRole = null;
								 List<RoleGroupAccessRole> roleGroupApplicationRoleList = this.repositoryRoleGroupAccessRole
										.findByRoleGroupRoleGroupIdAndIsDeleted(
													userGroupRoleGroup.getRoleGroup().getRoleGroupId(), false);
									for (RoleGroupAccessRole roleGroupApplicationRole : roleGroupApplicationRoleList) {
										accessRole = roleGroupApplicationRole.getAccessRole();
										if (accessRole != null) {
											dtoApplicationRole = new DtoAccessRole(accessRole);
											roleGroupRoleList.add(dtoApplicationRole);

										}
										dtoRoleGroup.setRoleGroupRoleList(roleGroupRoleList);
									}
									listDtoRoleGroups.add(dtoRoleGroup);
							}
						}
						
						dtoUserGroup.setListRoleGroup(listDtoRoleGroups);
						dtoUserGroup.setRoleGroupNames(roleGroupNames);
						dtoUserGroup.setRoleGroupIds(roleGroupIds);
					}
					dtoUserGroupList.add(dtoUserGroup);
				}
				dtoSearch.setRecords(dtoUserGroupList);
			}
		}
		return dtoSearch;
	}

	/**
	 * Description: get user list by company id and user group id
	 * @param dtoUserGroup
	 * @return
	 */
	public List<DtoUser> getUserListByCompanyAndUserGroup(DtoUserGroup dtoUserGroup) {
		List<DtoUser> dtoUserList = null;
		List<UserCompanyRelation> userCompanyRelationsList = this.repositoryUserCompanyRelation
				.findByIsDeletedAndUserGroupUserGroupIdAndUserIsActiveAndCompanyCompanyIdAndUserIsDeleted(false,
						dtoUserGroup.getId(), true, dtoUserGroup.getCompanyId(), false);
		if (userCompanyRelationsList != null && !userCompanyRelationsList.isEmpty()) {
			dtoUserList = new ArrayList<>();
			DtoUser dtoUser = null;
			for (UserCompanyRelation userCompanyRelation : userCompanyRelationsList) {
				User user = userCompanyRelation.getUser();
				UserDetail userDetail = repositoryUserDetail.findByUserUserIdAndIsDeleted(user.getUserId(), false);
				dtoUser = new DtoUser();
				dtoUser.setUserId(user.getUserId());
				if (userDetail != null) {
					if (UtilRandomKey.isNotBlank(userDetail.getFirstName())) {
						dtoUser.setFirstName(userDetail.getFirstName());
					} else {
						dtoUser.setFirstName("");
					}

					if (UtilRandomKey.isNotBlank(userDetail.getLastName())) {
						dtoUser.setLastName(userDetail.getLastName());
					} else {
						dtoUser.setLastName("");
					}

					if (UtilRandomKey.isNotBlank(userDetail.getMiddleName())) {
						dtoUser.setMiddleName(userDetail.getMiddleName());
					} else {
						dtoUser.setMiddleName("");
					}

					dtoUserList.add(dtoUser);
				}
			}
		}
		return dtoUserList;
	}
	

}

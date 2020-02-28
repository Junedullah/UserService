/**SmartSoftware User - Service */
/**
 * Description: ServiceAuthSettings
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ss.constant.ConfigSetting;
import com.ss.model.AuthorizationSetting;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserDetail;
import com.ss.model.WeekDay;
import com.ss.model.dto.DtoAuthorizationDetail;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.model.dto.DtoWeekDay;
import com.ss.repository.RepositoryAuthorizationSetting;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserGroup;
import com.ss.repository.RepositoryWeekDay;
import com.ss.util.UtilDateAndTime;
import com.ss.util.UtilRandomKey;

@Service("serviceAuthSettings")
public class ServiceAuthSettings {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ServiceAuthSettings.class);

	@Autowired
	RepositoryWeekDay repositoryWeekDay;

	@Autowired
	RepositoryUserGroup repositoryUserGroup;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	RepositoryCompany repositoryCompany;

	@Autowired
	RepositoryAuthorizationSetting repositoryAuthorizationSetting;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;
	
	private static final String USER_ID = "userid";

	/**
	 * Description: get all week days
	 * @return
	 */
	public List<DtoWeekDay> getWeekDays() {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoWeekDay> dtoWeekDayList = null;
		List<WeekDay> weekDayList = this.repositoryWeekDay.findByLanguageLanguageId(Integer.parseInt(langId));
		if (weekDayList != null && !weekDayList.isEmpty()) {
			dtoWeekDayList = new ArrayList<>();
			for (WeekDay weekDay : weekDayList) {
			  dtoWeekDayList.add(new DtoWeekDay(weekDay.getWeekDayId(), weekDay.getDayName()));
			}
		}
		return dtoWeekDayList;
	}

	/**
	 * Description: save authentication settings
	 * @param dtoAuthorizationDetail
	 */
	public void saveAuthSettings(DtoAuthorizationDetail dtoAuthorizationDetail) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		AuthorizationSetting authorizationSetting = new AuthorizationSetting();
		authorizationSetting.setCreatedBy(loggedInUserId);
		StringBuilder weekday = new StringBuilder();
		if (dtoAuthorizationDetail.getDayIds() != null && !dtoAuthorizationDetail.getDayIds().isEmpty()) {
			int count = 0;
			for (Integer dayId : dtoAuthorizationDetail.getDayIds()) {
				if (count == 0) {
					weekday.append(String.valueOf(dayId));
					count++;
				} else {
					weekday.append("," + dayId);
				}
			}
		}

		authorizationSetting.setWeekDay(weekday.toString());
		authorizationSetting.setStartDate(UtilDateAndTime.ddmmyyyyStringToDate(dtoAuthorizationDetail.getStartDate()));
		authorizationSetting.setEndDate(UtilDateAndTime.ddmmyyyyStringToDate(dtoAuthorizationDetail.getEndDate()));
		authorizationSetting
				.setStartTime(UtilDateAndTime.getTimeFromStringFrom24Formats(dtoAuthorizationDetail.getStartTime()));
		authorizationSetting
				.setEndTime(UtilDateAndTime.getTimeFromStringFrom24Formats(dtoAuthorizationDetail.getEndTime()));
		authorizationSetting = repositoryAuthorizationSetting.saveAndFlush(authorizationSetting);

		if (dtoAuthorizationDetail.getUserIds() != null && !dtoAuthorizationDetail.getUserIds().isEmpty()) {
			for (Integer userId : dtoAuthorizationDetail.getUserIds()) {
				UserCompanyRelation userCompanyRelation = repositoryUserCompanyRelation
						.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(dtoAuthorizationDetail.getCompanyId(), userId,
								false);
				if (userCompanyRelation != null) {
					userCompanyRelation.setAuthorizationSetting(authorizationSetting);
					repositoryUserCompanyRelation.saveAndFlush(userCompanyRelation);
				}
			}
		}
	}

	/**
	 * Description: update authentication settings
	 * @param dtoAuthorizationDetail
	 */
	public void updateAuthSettings(DtoAuthorizationDetail dtoAuthorizationDetail) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		AuthorizationSetting authorizationSetting = repositoryAuthorizationSetting
				.findOne(dtoAuthorizationDetail.getId());
		if (authorizationSetting != null) {
			 
			/*List<Integer> list = new ArrayList<>();
			list.add(authorizationSetting.getAuthSettingId());
			repositoryUserCompanyRelation.setNullAuthoriZationSettinfByAuthSettingId(list, loggedInUserId,null);*/
			 
			StringBuilder weekday = new StringBuilder("");
			if (dtoAuthorizationDetail.getDayIds() != null && !dtoAuthorizationDetail.getDayIds().isEmpty()) {
				int count = 0;
				for (Integer dayId : dtoAuthorizationDetail.getDayIds()) {
					if (count == 0) {
						weekday.append(String.valueOf(dayId));
						count++;
					} else {
						weekday.append("," + dayId);
					}
				}
			}

			authorizationSetting.setWeekDay(weekday.toString());
			authorizationSetting
					.setStartDate(UtilDateAndTime.ddmmyyyyStringToDate(dtoAuthorizationDetail.getStartDate()));
			authorizationSetting.setEndDate(UtilDateAndTime.ddmmyyyyStringToDate(dtoAuthorizationDetail.getEndDate()));
			authorizationSetting.setStartTime(
					UtilDateAndTime.getTimeFromStringFrom24Formats(dtoAuthorizationDetail.getStartTime()));
			authorizationSetting
					.setEndTime(UtilDateAndTime.getTimeFromStringFrom24Formats(dtoAuthorizationDetail.getEndTime()));
			authorizationSetting = repositoryAuthorizationSetting.saveAndFlush(authorizationSetting);

			if (dtoAuthorizationDetail.getUserIds() != null && !dtoAuthorizationDetail.getUserIds().isEmpty()) {
				for (Integer userId : dtoAuthorizationDetail.getUserIds()) {
					UserCompanyRelation userCompanyRelation = repositoryUserCompanyRelation
							.findByCompanyCompanyIdAndUserUserIdAndIsDeleted(dtoAuthorizationDetail.getCompanyId(),
									userId, false);
					if (userCompanyRelation != null) {
						userCompanyRelation.setAuthorizationSetting(authorizationSetting);
						userCompanyRelation.setUpdatedBy(loggedInUserId);
						repositoryUserCompanyRelation.saveAndFlush(userCompanyRelation);
					}
				}
			}
		}
	}
	
	/**
	 * Description: delete authentication settings
	 * @param dtoAuthorizationDetail
	 * @return
	 */
	public boolean deleteAuthSettings(DtoAuthorizationDetail dtoAuthorizationDetail) 
	{
		boolean result =false;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		repositoryAuthorizationSetting.deleteAuthSettings(dtoAuthorizationDetail.getIds(), true, loggedInUserId);
		repositoryUserCompanyRelation.setNullAuthoriZationSettinfByAuthSettingId(dtoAuthorizationDetail.getIds(), loggedInUserId,null);
		result=true;
		return result;
	}
	
	public DtoAuthorizationDetail getAuthSettingsById(int authId)
	{
		String langId = httpServletRequest.getHeader("langid");
		AuthorizationSetting authorizationSetting =repositoryAuthorizationSetting.findOne(authId);
		if(authorizationSetting!=null){
			int userGroupId=0;
			int companyId=0;
			String companyName="";
			String authGroupName="";
			List<Integer> userIdList = new ArrayList<>();
			List<DtoUser> dtoUserList = new ArrayList<>();
			DtoUser dtoUser = null;
			DtoAuthorizationDetail dtoAuthorizationDetail = new DtoAuthorizationDetail(authorizationSetting);
			List<UserCompanyRelation> userCompanyRelationsList = repositoryUserCompanyRelation.findByIsDeletedAndAuthorizationSettingAuthSettingId(false, authorizationSetting.getAuthSettingId());
			if(userCompanyRelationsList!=null && !userCompanyRelationsList.isEmpty()){
				for (UserCompanyRelation userCompanyRelation : userCompanyRelationsList) {
					companyId   = userCompanyRelation.getCompany().getCompanyId();
					companyName = userCompanyRelation.getCompany().getName();
					if(!userCompanyRelation.getUser().getIsDeleted())
					{
						User user = userCompanyRelation.getUser();
						userIdList.add(user.getUserId());
						dtoUser=new DtoUser();
						dtoUser.setUserId(user.getUserId());
						dtoUser.setFullName("");
						dtoUser.setFirstName("");
						dtoUser.setLastName("");
						UserDetail userDetail= repositoryUserDetail.findByUserUserId(user.getUserId());
						if(userDetail!=null)
						{
							
							if(ConfigSetting.PRIMARY.getValue().equalsIgnoreCase(langId))
							{
								if(UtilRandomKey.isNotBlank(userDetail.getFirstName())){
									dtoUser.setFirstName(userDetail.getFirstName());
									dtoUser.setFullName(userDetail.getFirstName());
								}
								
								if(UtilRandomKey.isNotBlank(userDetail.getLastName())){
									dtoUser.setLastName(userDetail.getLastName());
									dtoUser.setFullName(userDetail.getFirstName()+" "+userDetail.getLastName());
								}
								
							}
							else if (ConfigSetting.SECONDARY.getValue().equalsIgnoreCase(langId))
							{
								if(UtilRandomKey.isNotBlank(userDetail.getSecondaryFirstName())){
									dtoUser.setFirstName(userDetail.getSecondaryFirstName());
									dtoUser.setFullName(userDetail.getSecondaryFirstName());
								}
								
								if(UtilRandomKey.isNotBlank(userDetail.getSecondaryLastName())){
									dtoUser.setLastName(userDetail.getSecondaryLastName());
									dtoUser.setFullName(userDetail.getSecondaryFirstName()+" "+userDetail.getSecondaryLastName());
								}
								
							}
						}
						dtoUserList.add(dtoUser);
					}
					
					if(userCompanyRelation.getUserGroup()!=null)
					{
						userGroupId=userCompanyRelation.getUserGroup().getUserGroupId();
						authGroupName=userCompanyRelation.getUserGroup().getGroupName();
					}
				}
			}
			dtoAuthorizationDetail.setCompanyId(companyId);
			dtoAuthorizationDetail.setUserIds(userIdList);
			dtoAuthorizationDetail.setAuthorizedUserGroupId(userGroupId);
			dtoAuthorizationDetail.setAuthorizedGroupName(authGroupName);
			dtoAuthorizationDetail.setAuthSettingId(authorizationSetting.getAuthSettingId());
			dtoAuthorizationDetail.setCompanyName(companyName);
			dtoAuthorizationDetail.setUsersList(dtoUserList);
			return dtoAuthorizationDetail;
		}
		else{
			return null;
		}
	}
	
	public DtoSearch getAllAuthSettings(DtoAuthorizationDetail dtoAuthDetail)
	{
		DtoSearch dtoSearch2 = new DtoSearch();
		dtoSearch2.setPageNumber(dtoAuthDetail.getPageNumber());
		dtoSearch2.setPageSize(dtoAuthDetail.getPageSize());
		dtoSearch2.setTotalCount(repositoryAuthorizationSetting.getCountOfTotalAuthSettings());
		List<DtoAuthorizationDetail> authSettingsList= new ArrayList<>();
		List<AuthorizationSetting> authorizationSettingList=null;
		if(dtoAuthDetail.getPageNumber()!=null && dtoAuthDetail.getPageSize()!=null && dtoAuthDetail.getPageSize()>0 )
		{
			Pageable pageable = new PageRequest(dtoAuthDetail.getPageNumber(), dtoAuthDetail.getPageSize(), Direction.DESC,
					"createdDate");
			authorizationSettingList = repositoryAuthorizationSetting.findByIsDeleted(false,pageable);
		}
		else
		{
			authorizationSettingList = repositoryAuthorizationSetting.findByIsDeleted(false);
		}
		
		if(authorizationSettingList!=null && !authorizationSettingList.isEmpty())
		{
			for (AuthorizationSetting authorizationSetting : authorizationSettingList) 
			{
				int userGroupId=0;
				int companyId=0;
				String companyName="";
				String authGroupName="";
				List<Integer> userIdList = new ArrayList<>();
				DtoAuthorizationDetail dtoAuthorizationDetail = new DtoAuthorizationDetail(authorizationSetting);
				List<UserCompanyRelation> userCompanyRelationsList = repositoryUserCompanyRelation.findByIsDeletedAndAuthorizationSettingAuthSettingId(false, authorizationSetting.getAuthSettingId());
				if(userCompanyRelationsList!=null && !userCompanyRelationsList.isEmpty())
				{
					for (UserCompanyRelation userCompanyRelation : userCompanyRelationsList) 
					{
						companyId=userCompanyRelation.getCompany().getCompanyId();
						companyName=userCompanyRelation.getCompany().getName();
						userIdList.add(userCompanyRelation.getUser().getUserId());
						if(userCompanyRelation.getUserGroup()!=null)
						{
							userGroupId=userCompanyRelation.getUserGroup().getUserGroupId();
							authGroupName=userCompanyRelation.getUserGroup().getGroupName();
						}
					}
				}
				dtoAuthorizationDetail.setCompanyId(companyId);
				dtoAuthorizationDetail.setUserIds(userIdList);
				dtoAuthorizationDetail.setAuthorizedUserGroupId(userGroupId);
				dtoAuthorizationDetail.setAuthorizedGroupName(authGroupName);
				dtoAuthorizationDetail.setAuthSettingId(authorizationSetting.getAuthSettingId());
				dtoAuthorizationDetail.setCompanyName(companyName);
				authSettingsList.add(dtoAuthorizationDetail);
			}
		}
		dtoSearch2.setRecords(authSettingsList);
		return dtoSearch2;
	}
	
}

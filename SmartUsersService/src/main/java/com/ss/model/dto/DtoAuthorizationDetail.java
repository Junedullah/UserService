/**SmartSoftware User - Service */
/**
 * Description: DTO Authorization Detail class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 19, 2020
 * Modified on: March 19, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ss.constant.WeekDayConstant;
import com.ss.model.AuthorizationSetting;
import com.ss.util.UtilDateAndTime;
import com.ss.util.UtilRandomKey;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoAuthorizationDetail {

	private int authSettingId;
	private Integer id;
	private Integer companyId;
	private Integer authorizationType;
	private String authorizedUserGroup;
	private String moduleId;
	private String days;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	List<Integer> dayIds;
	List<Integer> userIds;
	private Integer authorizedUserGroupId;
	private List<Integer> ids;
	List<String> daysList;
	List<Integer> companyIds;
	List<Integer> userGroupIds;
	private Integer pageNumber;
	private Integer pageSize;
	private String searchKeyword;
	private String companyName;
	private String authorizedGroupName;
	List<DtoUser> usersList;
	
	public DtoAuthorizationDetail(){
		
	}
	
	public DtoAuthorizationDetail(AuthorizationSetting authorizationSetting)
	{
		if(authorizationSetting.getStartDate()!=null){
			startDate= UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getStartDate());
		}
		else{
			startDate="";
		}
		
		if(authorizationSetting.getEndDate()!=null){
			endDate=UtilDateAndTime.dateToStringddmmyyyy(authorizationSetting.getEndDate());
		}
		else{
			endDate="";
		}
		
		if(authorizationSetting.getStartTime()!=null){
			startTime=UtilDateAndTime.convertTimeToString24Formats(authorizationSetting.getStartTime());
		}
		else{
			startTime="";
		}
		
		if(authorizationSetting.getEndTime()!=null){
			endTime=UtilDateAndTime.convertTimeToString24Formats(authorizationSetting.getEndTime());
		}
		else{
			endTime="";
		}
		daysList= new ArrayList<>();
		dayIds= new ArrayList<>();
		if(UtilRandomKey.isNotBlank(authorizationSetting.getWeekDay()))
		{
			String[] daysSplit=authorizationSetting.getWeekDay().split(",");
			if(daysSplit!=null && daysSplit.length>0){
				for (String dayId : daysSplit) {
					dayIds.add(Integer.parseInt(dayId));
					daysList.add(WeekDayConstant.getById(Integer.parseInt(dayId)).name());
				}
			}
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getAuthorizationType() {
		return authorizationType;
	}

	public void setAuthorizationType(Integer authorizationType) {
		this.authorizationType = authorizationType;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Integer> getDayIds() {
		return dayIds;
	}

	public void setDayIds(List<Integer> dayIds) {
		this.dayIds = dayIds;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public String getAuthorizedUserGroup() {
		return authorizedUserGroup;
	}

	public void setAuthorizedUserGroup(String authorizedUserGroup) {
		this.authorizedUserGroup = authorizedUserGroup;
	}

	public Integer getAuthorizedUserGroupId() {
		return authorizedUserGroupId;
	}

	public void setAuthorizedUserGroupId(Integer authorizedUserGroupId) {
		this.authorizedUserGroupId = authorizedUserGroupId;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<String> getDaysList() {
		return daysList;
	}

	public void setDaysList(List<String> daysList) {
		this.daysList = daysList;
	}

	public List<Integer> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Integer> companyIds) {
		this.companyIds = companyIds;
	}

	public List<Integer> getUserGroupIds() {
		return userGroupIds;
	}

	public void setUserGroupIds(List<Integer> userGroupIds) {
		this.userGroupIds = userGroupIds;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getAuthSettingId() {
		return authSettingId;
	}

	public void setAuthSettingId(int authSettingId) {
		this.authSettingId = authSettingId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAuthorizedGroupName() {
		return authorizedGroupName;
	}

	public void setAuthorizedGroupName(String authorizedGroupName) {
		this.authorizedGroupName = authorizedGroupName;
	}

	public List<DtoUser> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<DtoUser> usersList) {
		this.usersList = usersList;
	}


}

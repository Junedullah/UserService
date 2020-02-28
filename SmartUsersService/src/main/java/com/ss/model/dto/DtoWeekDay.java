/**SmartSoftware User - Service */
/**
 * Description: Dto WeekDay  class having getter and setter for fields (POJO) Name
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoWeekDay {

	private Integer weekDayId;
	private String dayName;

	public DtoWeekDay() {
		super();
	}

	public DtoWeekDay(Integer weekDayId, String dayName) {
		super();
		this.weekDayId = weekDayId;
		this.dayName = dayName;
	}

	/**
	 * @return the weekDayId
	 */
	public Integer getWeekDayId() {
		return weekDayId;
	}
	/**
	 * @param weekDayId the weekDayId to set
	 */
	public void setWeekDayId(Integer weekDayId) {
		this.weekDayId = weekDayId;
	}
	/**
	 * @return the dayName
	 */
	public String getDayName() {
		return dayName;
	}
	/**
	 * @param dayName the dayName to set
	 */
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

}

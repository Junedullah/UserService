/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the week_days database table.
 * Name of Project: SmartSoftware
 * Created on: March 15, 2020
 * Modified on: March 15, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "week_days")
@NamedQuery(name = "WeekDay.findAll", query = "SELECT w FROM WeekDay w")
public class WeekDay extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "week_day_id")
	private int weekDayId;

	@Column(name = "day_name")
	private String dayName;
	
	@Column(name = "day_code")
	private String dayCode;
	
	@ManyToOne
	@JoinColumn(name="lang_id")
	private Language language;


	public WeekDay() {
	}

	public int getWeekDayId() {
		return this.weekDayId;
	}

	public void setWeekDayId(int weekDayId) {
		this.weekDayId = weekDayId;
	}

	public String getDayName() {
		return this.dayName;
	}

	public void setDayName(String dayName) {
		this.dayName = dayName;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getDayCode() {
		return dayCode;
	}

	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}
	
}
/**SmartSoftware User - Service */
/**
 * Description:The <code>Week Day Constant</code> Contains the days name.
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */ 

package com.ss.constant;
/**
 * The <code>Week Day Constant</code> Contains the days name.
 * Name of Project: BTI
 * Created on: May 09, 2017
 * Modified on: May 09, 2017 4:19:38 PM
 * @author seasia
 * Version: 
 */
public enum WeekDayConstant {

	SUNDAY(1), MONDAY(2),TUESDAY(3), WEDNESDAY(4),THURSDAY(5), FRIDAY(6),SATURDAY(7);
	
	private Integer index;

	/**
	 * @param index
	 */
	private WeekDayConstant(int index) {

		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	
	public static WeekDayConstant getById(int id) {
	    for(WeekDayConstant e : values()) {
	        if(e.index.equals(id)) return e;
	    }
	    return null;
	 }
}
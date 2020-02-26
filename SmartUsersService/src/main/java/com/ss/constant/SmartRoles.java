/**SmartSoftware User - Service */
/**
 * Description: The <code>SmartRoles</code> Contains the type of Application User Role.
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */


package com.ss.constant;

public enum SmartRoles {

	USER(1), SUPERADMIN(2);

	private int index;

	/**
	 * @param index
	 */
	private SmartRoles(int index) {

		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
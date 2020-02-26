/**SmartSoftware User - Service */
/**
 * Description: Smart Code Type
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */

package com.ss.constant;

public enum SmartCodeType {

	ROLE(1), ROLEGROUP(2), USERGROUP(3), EMPLOYEECODE(4), COMPANYCODE(5);

	private int index;

	/**
	 * @param index
	 */
	private SmartCodeType(int index) {

		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
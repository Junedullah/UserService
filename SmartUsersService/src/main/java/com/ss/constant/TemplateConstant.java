/**SmartSoftware User - Service */
/**
 * Description: The <code>TemplateConstant</code> interface contains all template Codes
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.constant;



 

public enum TemplateConstant {
	FORGOT_PASSWORD_EMAIL("Forgot_Password"), CHANGE_USER_PASSWORD_EMAIL("Change_User_Password"), USER_REGISTRATION("User_Registration"), RESET_PASSWORD("Reset_Password");

	private final String value;

	private TemplateConstant(String value) {
		this.value = value;
	}

	/**
	 * Return the value of this status.
	 */
	public String getValue() {
		return value;
	}
}
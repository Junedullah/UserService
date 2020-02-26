/**SmartSoftware User - Service */
/**
 * Description: The <code>ConfigSetting</code> interface contains all Config Setting Codes
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.constant;

 
public enum ConfigSetting {
	SMS_AUTHENTICATION("SMS_AUTHENTICATION"), PRIMARY("1"), SECONDARY("2"), SOLR_RUNNING("SOLR_RUNNING");

	private final String value;

	private ConfigSetting(String value) {
		this.value = value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getValue() {
		return value;
	}
}
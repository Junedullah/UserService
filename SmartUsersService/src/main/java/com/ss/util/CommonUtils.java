/**SmartSoftware User - Service */
/**
 * Description: Round Decimal Utility for SmartSoft
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 10:19:38 PM
 * @author Juned
 * Version: 
 */
package com.ss.util;


public class CommonUtils {

	public static double parseDouble(String value)
	{
		double d = 0.0;
		try {
			if(value!=null && !value.isEmpty()){
				d = Double.parseDouble(value);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			d = 0.0;
		}
		return d;
	}

	public static Integer parseInteger(String value)
	{
		Integer i = 0;
		try {
			if(value!=null && !value.isEmpty()){
				i = Integer.parseInt(value);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			i = 0;
		}
		return i;
	}
	
	public static String removeNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

}

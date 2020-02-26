/**SmartSoftware User - Service */
/**
 * Description: Utility class for random verification code generation
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.util;
import java.security.Key;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

 

/**
 * Description: Utility class for random verification code generation  Name of Project: BTI APP Created on: May 16,
 * 2017 Modified on: May 16, 2017 10:19:38 AM
 * 
 * @author seasia Version:
 */

public class LoginDetailsUtil {

	private static final String ALGO = "AES";

	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 't', 'i', 'S', 'S', 'e', 'c', 'r', 'e', 't',
			'K', 'e', 'y' };

	/**
	 * @return
	 */
	public String getCheckVerificationCode() {

		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIZKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getCheckResetCode() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIZKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey() {
		return new SecretKeySpec(keyValue, ALGO);
	}

}

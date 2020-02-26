/**SmartSoftware User - Service */
/**
 * Description:RandomKey Utility for SmartSoft
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 5:39:32 PM
 * @author Juned
 * Version: 
 */
package com.ss.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class UtilRandomKey {

    /**
     * it return the 6 digit random numeric number
     * @return
     */
	public static String getRandomOrderNumber() {
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	private SecureRandom random = new SecureRandom();

	/**
	 * @return
	 */
	public String nextRandomKey() {
		return new BigInteger(60, random).toString(32);
	}
	
	/**
	 * it generates encrypted session key
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String nextRandomSessionKey() throws NoSuchAlgorithmException {
		KeyGenerator gen = KeyGenerator.getInstance("DES");
    	gen.init(56); /* 56-bit DES */
		SecretKey secret = gen.generateKey();
		byte[] binary = secret.getEncoded();
		String text = String.format("%08X", new BigInteger(+1, binary));
		return text;
	}
	
    /**
     * it return session key string for logged used
     * @return
     */
	public static String generateSessionKey() {
		String key = null;
		try {
			key = nextRandomSessionKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

	public static boolean isNotBlank(final String s) {
    	  // Null-safe, short-circuit evaluation.
		return s != null && !s.trim().isEmpty();
	}

	public static boolean isNotNull(final Integer i) {
  	  // Null-safe, short-circuit evaluation.
		return i != null && i != 0;
	}

}

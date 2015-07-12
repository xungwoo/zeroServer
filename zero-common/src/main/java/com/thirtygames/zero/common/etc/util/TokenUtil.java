package com.thirtygames.zero.common.etc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TokenUtil {
	
	public static String generateAccessToken()  {
		return md5(UUID.randomUUID().toString());
	}

	public static String md5(String password) {
		String encryptedData = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte[] defaultBytes = password.getBytes();
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			encryptedData = hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {

		}
		return encryptedData;
	}
}

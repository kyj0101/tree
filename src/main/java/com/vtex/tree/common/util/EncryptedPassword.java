package com.vtex.tree.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptedPassword {
	
	public static String getEncryptedPassword(String password) {
		String encryptedPassword = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = password.getBytes("UTF-8");
			
			md.update(bytes);
			
			byte[] encryptedBytes = md.digest();
			encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encryptedPassword;
	}
}

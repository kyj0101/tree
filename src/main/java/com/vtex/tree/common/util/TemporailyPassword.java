package com.vtex.tree.common.util;

public class TemporailyPassword {
	
	public static final int MIN_PASSWORD_LENGTH = 8;
	
	public static String getTemporailyPassword(int passwordLength) {
		
		System.out.println(passwordLength);
		
		if(passwordLength < MIN_PASSWORD_LENGTH) {
			throw new IllegalArgumentException();
		}
		
		char specialChar = (char)((int)(Math.random() * 47) + 32);
		char capitalChar = (char)((int)(Math.random() * 90) + 65);
		
		char[] passwordArr = new char[passwordLength];
		passwordArr[0] = specialChar;
		passwordArr[1] = capitalChar;
		
		for(int i = 2; i < passwordLength; i++) {
			
			char ch = (char)((int)(Math.random() * 57) + 48);

			if(ch == ' ') {
				continue;
			}			
			passwordArr[i] = ch;
		}

		return new String(passwordArr);
	}
}

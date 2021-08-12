package com.vtex.tree;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void testEncode(){
		
		String password = "123456789!q";
		String enPw = passwordEncoder.encode(password);
	
		System.out.println("enPw:" + enPw);
		
		boolean matchResult = passwordEncoder.matches(password, "$2a$10$VvlF0bBDhYFym.15BRm3hugqv5rceNkAyAzylB3G2o655t.c5s51.");
		
		System.out.println("matchResult:" + matchResult);
	}
}

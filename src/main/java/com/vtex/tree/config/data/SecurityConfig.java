package com.vtex.tree.config.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vtex.tree.security.handler.LoginFailHandler;
import com.vtex.tree.security.mapper.SecurityMapper;
import com.vtex.tree.security.service.SecurityDetails;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityDetails securityDetails;

	@Autowired
	private SecurityMapper securityMapper;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LoginFailHandler loggFailHandler() {
		return new LoginFailHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/board/list")
			.failureHandler(loggFailHandler());
		
		http.logout()
		.logoutUrl("/logout") // POST
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityDetails)
			.passwordEncoder(passwordEncoder());	
	}
}

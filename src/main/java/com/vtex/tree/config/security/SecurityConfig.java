package com.vtex.tree.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.vtex.tree.security.handler.LoginFailHandler;
import com.vtex.tree.security.handler.LoginSuccessHandler;
import com.vtex.tree.security.oauth.CustomOAuth2UserService;
import com.vtex.tree.security.service.SecurityDetails;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityDetails securityDetails;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomOAuth2UserService service;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LoginFailHandler logInFailHandler() {
		return new LoginFailHandler();
	}
	
	@Bean
	public LoginSuccessHandler logInSuccessHandler() {
		return new LoginSuccessHandler();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/login")
			.permitAll()
			.and()
			.csrf()
			.disable()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/board/list")
			.failureHandler(logInFailHandler())
			.successHandler(logInSuccessHandler())
			.and()
			.logout()
			.logoutUrl("/logout") // POST
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.and()
			.rememberMe()
			.tokenValiditySeconds(60*60*24*7)
			.userDetailsService(securityDetails)
			.and()
			.oauth2Login() //OAuth2 ????????? ????????? ?????????
			.loginPage("/login")
			.failureHandler(logInFailHandler())
			.userInfoEndpoint() //????????? ?????? ?????? ????????? ????????? ????????? ?????? ???????????? ??????
			.userService(service); //????????? ?????? ????????? ?????? ????????? ????????? UserService??????????????? ???????????? ?????????
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityDetails)
			.passwordEncoder(passwordEncoder());	
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/js/**","/resource/**");
	
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		
		return repo;
	}
	
	
}

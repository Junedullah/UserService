/**
 * Description: SecurityConfig
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;



@Configuration
@ComponentScan("com.ss")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	Environment env;

	@Autowired
	com.ss.service.ServiceAuthentication authenticationService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * "if you are only creating a service that is used by non-browser
		 * clients, you will likely want to disable CSRF protection."
		 */
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/***","/login/**","/group/**","/user/**","/company/**","/authSettings/**", "/ip/**","/plugUnplugServices/**","/language/**","/department/**","/division/**","/hrCity/**", "/workFlowAssign/**","/fields/**", "/common/**", "/grids/**","/reportmaster/**","/companyreport/**","http://10.0.0.244:8080/activiti-app/**").permitAll()
				.anyRequest().authenticated().and().httpBasic(); //Basic authentication enabled
		
		
		
		http.headers().addHeaderWriter((HeaderWriter) new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
	}
	
}

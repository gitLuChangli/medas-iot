package com.foxconn.iot.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private MyAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private MyLogoutSuccessHandler logoutSuccessHandler;
	@Autowired
	private MyAccessDeniedHandler accessDeniedHandler;
	@Autowired
	private MyAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		
			.and()
			.httpBasic()
			.authenticationEntryPoint(authenticationEntryPoint)
		
			.and()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
		
			.and()
			.formLogin()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
			.permitAll()
			
			.and()
			.logout()
			.logoutSuccessHandler(logoutSuccessHandler)
			.permitAll();
		
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/**");
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:9001", "http://10.153.16.140:9001"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
}

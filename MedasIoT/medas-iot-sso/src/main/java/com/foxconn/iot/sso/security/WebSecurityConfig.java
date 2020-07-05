package com.foxconn.iot.sso.security;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxconn.iot.sso.model.RespBean;
import com.foxconn.iot.sso.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
	@Autowired
	private CustomUrlDecisionManager customUrlDecisionManager;
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/vcode", "/login", "/auth/pwd", "/auth/login");
	}

	@Bean
	LoginFilter loginFilter() throws Exception {
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			RespBean ok = RespBean.ok("登录成功");
			/*
			HttpSession session = request.getSession(false);
			Cookie cookie = new Cookie("JSESSIONID", session.getId());
			cookie.setPath("/");
			response.addCookie(cookie);
			Map<String, Object> token = new HashMap<>();
			token.put("access_token", session.getId());
			ok.setData(token);
			*/
			String s = objectMapper.writeValueAsString(ok);
			out.write(s);
			out.flush();
			out.close();
		});
		loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			RespBean respBean = RespBean.error(exception.getMessage());
			if (exception instanceof LockedException) {
				respBean.setStatus(1000);
				respBean.setMsg("账户被锁定，请联系管理员");
			} else if (exception instanceof CredentialsExpiredException) {
				respBean.setStatus(1001);
				respBean.setMsg("密码过期，请联系管理员");
			} else if (exception instanceof AccountExpiredException) {
				respBean.setStatus(1002);
				respBean.setMsg("账户过期，请联系管理员");
			} else if (exception instanceof DisabledException) {
				respBean.setStatus(1003);
				respBean.setMsg("账户被禁，请联系管理员");
			} else if (exception instanceof BadCredentialsException) {
				respBean.setStatus(1004);
				respBean.setMsg("用户或密码输入错误，请重新输入");
			}
			String s = objectMapper.writeValueAsString(respBean);
			out.write(s);
			out.flush();
			out.close();
		});
		loginFilter.setAuthenticationManager(authenticationManagerBean());
		loginFilter.setFilterProcessesUrl("/auth/login");
		ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(
				sessionRegistry());
		sessionStrategy.setMaximumSessions(1);
		loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
		return loginFilter;
	}

	@Bean
	SessionRegistryImpl sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		
		/*http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				object.setAccessDecisionManager(customUrlDecisionManager);
				object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
				return object;
			}
		});*/
		
		http.authorizeRequests().and().logout().logoutSuccessHandler((request, response, authentication) -> {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.write(objectMapper.writeValueAsString(RespBean.ok("注销成功")));
			out.flush();
			out.close();
		}).permitAll()
		.and()
		
		.exceptionHandling()
		.authenticationEntryPoint((request, response, exception) -> {
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(401);
			PrintWriter out = response.getWriter();
			RespBean respBean = RespBean.build();
			respBean.setStatus(401);
			respBean.setMsg("无权访问");
			if (exception instanceof InsufficientAuthenticationException) {
				respBean.setMsg("认证信息不足，请重新登录");
			}
			out.write(objectMapper.writeValueAsString(respBean));
			out.flush();
			out.close();
		});
		http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
			HttpServletResponse response = event.getResponse();
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(401);
			PrintWriter out = response.getWriter();
			out.write(objectMapper.writeValueAsString(RespBean.error("您已在另一台设备登录，本次登录已下线")));
			out.flush();
			out.close();
		}), ConcurrentSessionFilter.class);
		http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
		.anyRequest()
		.authenticated();
	}
	
	/*
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:9001"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}*/
}

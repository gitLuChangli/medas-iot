package com.foxconn.iot.sso.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.foxconn.iot.sso.service.ResourceService;

/**
 * 根据请求的地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		String method = ((FilterInvocation) object).getRequest().getMethod();
		List<String> roles = resourceService.queryRoles(url, method);
		if (roles != null && roles.size() > 0) {
			return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
		}
		return SecurityConfig.createList("role_login");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}

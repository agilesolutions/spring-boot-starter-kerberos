package ch.agilesolutions.boot.start.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import ch.agilesolutions.boot.start.service.AuthenticationService;

public class AuthenticationFilter extends GenericFilterBean {
	
	@Autowired
	AuthenticationService authenticationService;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);

	}

}
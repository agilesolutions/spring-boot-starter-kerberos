package ch.agilesolutions.boot.start.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@Order(2)
public class JWTSecurityConfiguration extends WebSecurityConfigurerAdapter {
	

	/**
	 * read https://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security/#customwebsecurityconfigureradapter
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
		  	.authorizeRequests()
		  	.anyRequest().authenticated()
		  	.and()
			.addFilterBefore(new AuthenticationFilter(), BasicAuthenticationFilter.class);
	}


}
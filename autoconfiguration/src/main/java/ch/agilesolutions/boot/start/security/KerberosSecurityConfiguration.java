package ch.agilesolutions.boot.start.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ch.agilesolutions.boot.start.config.KerberosConfig;
import ch.agilesolutions.boot.start.service.AuthenticationService;


@Configuration
@EnableWebSecurity
@Order(1)
public class KerberosSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private KerberosConfig kerberosConfig;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * read https://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security/#customwebsecurityconfigureradapter
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/")
		  	.authorizeRequests()
		  	.anyRequest().authenticated()
		  	.and()
			.addFilterBefore(spnegoAuthenticationFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);
		
		http.sessionManagement().maximumSessions(Integer.parseInt(kerberosConfig.getProperty("maxSessions")));
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(kerberosServiceAuthenticationProvider()).authenticationProvider(kerberosAuthenticationProvider());
	}

	// build a local authentication manager
//	@Override
//	public void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.authenticationProvider(kerberosAuthenticationProvider());
//	}

	@Bean
	public KerberosAuthenticationProvider kerberosAuthenticationProvider() {
		KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
		SunJaasKerberosClient client = new SunJaasKerberosClient();
		client.setDebug(true);
		provider.setKerberosClient(client);
		provider.setUserDetailsService(userInfoService);
		return provider;
	}

	@Bean
	public SpnegoEntryPoint spnegoEntryPoint() {
		return new SpnegoEntryPoint("/login");
	}

	@Bean
	public SpnegoAuthenticationFilter  spnegoAuthenticationFilter (AuthenticationManager authenticationManager) {
		SpnegoAuthenticationFilter  filter = new SpnegoAuthenticationFilter (authenticationService);
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Bean
	public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() {
		KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
		provider.setTicketValidator(sunJaasKerberosTicketValidator());
		provider.setUserDetailsService(userInfoService);
		return provider;
	}

	@Bean
	public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
		SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
		ticketValidator.setServicePrincipal(kerberosConfig.getProperty("spn"));
		ticketValidator.setKeyTabLocation(new FileSystemResource(kerberosConfig.getProperty("keytab")));
		ticketValidator.setDebug(true);
		try {
			ticketValidator.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ticketValidator;
	}

//	@Bean
//	public UserInfoService userInfoService() {
//		return new UserInfoService();
//	}

}
package ch.agilesolutions.boot.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import ch.agilesolutions.boot.start.security.JWTSecurityConfiguration;
import ch.agilesolutions.boot.start.security.KerberosSecurityConfiguration;
import ch.agilesolutions.boot.start.security.UserInfoService;
import ch.agilesolutions.boot.start.service.AuthenticationService;

@Configuration
@ConditionalOnClass({KerberosSecurityConfiguration.class,  KerberosConfig.class, UserInfoService.class, AuthenticationService.class})
@EnableConfigurationProperties(KerberosPoperties.class)
public class KerberosAutoConfiguration {
	
	public static final String KEYTAB = "keytab";
	public static final String SPN = "spn";
	public static final String SIGNINGKEY = "signingkey";
	public static final String ROLES = "roles";

 
    @Autowired
    private KerberosPoperties kerberosProperties;
    
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(kerberosProperties.getUrl());
        contextSource.setBase(kerberosProperties.getBase());
        contextSource.setUserDn(kerberosProperties.getUsername());
        contextSource.setPassword(kerberosProperties.getPassword());
        return contextSource;
    }
    
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

 
    @Bean
    @ConditionalOnMissingBean
    public KerberosConfig kerberosConfig() {
 
 
        KerberosConfig kerberosConfig = new KerberosConfig();
        kerberosConfig.put(KEYTAB, kerberosProperties.getKtab());
        kerberosConfig.put(SPN, kerberosProperties.getSpn());
        kerberosConfig.put(SIGNINGKEY, kerberosProperties.getSigningkey());
        kerberosConfig.put(ROLES, kerberosProperties.getRoles());

        return kerberosConfig;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public KerberosSecurityConfiguration instantiateKerberos() {
        return new KerberosSecurityConfiguration();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public UserInfoService instantiateUserInfoService() {
        return new UserInfoService(kerberosProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public AuthenticationService instantiateAuthenticationService() {
        return new AuthenticationService(kerberosProperties);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public JWTSecurityConfiguration instantiateJWT() {
        return new JWTSecurityConfiguration();
    }

 
}

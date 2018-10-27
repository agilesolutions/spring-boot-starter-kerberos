package ch.agilesolutions.boot.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.agilesolutions.boot.start.security.JWTSecurityConfiguration;
import ch.agilesolutions.boot.start.security.KerberosSecurityConfiguration;

@Configuration
@ConditionalOnClass(KerberosSecurityConfiguration.class)
@EnableConfigurationProperties(KerberosPoperties.class)
public class KerberosAutoConfiguration {
	
	public static final String KEYTAB = "keytab";
	public static final String SPN = "spn";
	public static final String SIGNINGKEY = "signingkey";

 
    @Autowired
    private KerberosPoperties kerberosProperties;
 
    @Bean
    @ConditionalOnMissingBean
    public KerberosConfig kerberosConfig() {
 
 
        KerberosConfig kerberosConfig = new KerberosConfig();
        kerberosConfig.put(KEYTAB, kerberosProperties.getKtab());
        kerberosConfig.put(SPN, kerberosProperties.getSpn());
        kerberosConfig.put(SIGNINGKEY, kerberosProperties.getSigningkey());
        kerberosProperties.getRoles().entrySet().stream().map(entry -> kerberosConfig.put(entry.getKey(), entry.getValue()));

        return kerberosConfig;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public KerberosSecurityConfiguration instantiateKerberos() {
        return new KerberosSecurityConfiguration();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public JWTSecurityConfiguration instantiateJWT() {
        return new JWTSecurityConfiguration();
    }

 
}

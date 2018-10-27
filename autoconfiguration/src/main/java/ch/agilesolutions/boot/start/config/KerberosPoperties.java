package ch.agilesolutions.boot.start.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kerberos")
public class KerberosPoperties {

	private String ktab;
	private String spn;
	private String signingkey;
	private Map<String, String> roles = new HashMap<String, String>();
	
	public String getSigningkey() {
		return signingkey;
	}
	public void setSigningkey(String signingkey) {
		this.signingkey = signingkey;
	}
	public String getKtab() {
		return ktab;
	}
	public void setKtab(String ktab) {
		this.ktab = ktab;
	}
	public String getSpn() {
		return spn;
	}
	public void setSpn(String spn) {
		this.spn = spn;
	}
	public Map<String, String> getRoles() {
		return roles;
	}
	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}
	
	
	
	
}

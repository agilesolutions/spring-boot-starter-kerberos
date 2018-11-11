package ch.agilesolutions.boot.start.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kerberos")
public class KerberosPoperties {

	private String ktab;
	private String spn;
	private String signingkey;
	private Map<String, String> roles = new HashMap<String, String>();
	private String url;
	private String base;
	private String username;
	private String password;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}

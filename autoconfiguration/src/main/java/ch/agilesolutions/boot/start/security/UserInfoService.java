package ch.agilesolutions.boot.start.security;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ch.agilesolutions.boot.start.config.KerberosPoperties;

public class UserInfoService implements UserDetailsService {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private KerberosPoperties kerberosProperties;

	/**
	 * Match users AD groups to roles configured.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<String> roles = new ArrayList<>();

		List<String> groups = ldapTemplate.search(query().where("objectClass").is("user").and("sAMAccountName").is(username), new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return (String) attrs.get("memberOf").get();
			}
		});
		
		kerberosProperties.getRoles().entrySet().stream().forEach(entry -> {
			if (groups.contains(entry.getValue())) {
				roles.add(entry.getKey());
			}
			
		});

		return new User(username, "notUsed", true, true, true, true, AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));
	}
}
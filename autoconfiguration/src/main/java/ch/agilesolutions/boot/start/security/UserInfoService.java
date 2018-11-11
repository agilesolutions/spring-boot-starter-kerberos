package ch.agilesolutions.boot.start.security;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.SearchScope;
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

	public UserInfoService(KerberosPoperties kerberosProperties) {
		super();
		this.kerberosProperties = kerberosProperties;
	}

	/**
	 * Match users AD groups to roles configured.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<String> roles = new ArrayList<>();
		
		try {
			/*
			 * Get user distinguised name, example: "user" -> "CN=User Name,OU=Groups,OU=Domain Users,DC=company,DC=something,DC=org"
			 * This will be used for our query later
			 */
			String distinguishedName = ldapTemplate.search(
			        query().where("objectClass").is("user").and("sAMAccountName").is(username.split("@")[0]),
			        (AttributesMapper<String>) attrs -> attrs.get("distinguishedName").get().toString()
			).get(0); //.get(0): we assume that search will return a result 

			/*
			 * This one recursively search for all (nested) group that this user belongs to
			 * "member:1.2.840.113556.1.4.1941:" is a magic attribute, Reference: 
			 * https://msdn.microsoft.com/en-us/library/aa746475(v=vs.85).aspx
			 * However, this filter is usually slow in case your ad directory is large.
			 */
			List<String> groups = ldapTemplate.search(
			        query().searchScope(SearchScope.SUBTREE)
			                .where("member:1.2.840.113556.1.4.1941:").is(distinguishedName),
			        (AttributesMapper<String>) attrs -> attrs.get("cn").get().toString()
			);
			
			kerberosProperties.getRoles().entrySet().stream().forEach(entry -> {
				if (groups.contains(entry.getValue())) {
					roles.add((String) entry.getKey());
				}
				
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new User(username, "notUsed", true, true, true, true, AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));
	}
}
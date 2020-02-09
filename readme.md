# A Spring Boot Starter for Kerberos AD integration

This is a Spring Boot starter for automatically configuring kerberos AD authentication on your spring Boot application.

## Spring Method Security

* [Spring Method Security as Baeldung](https://www.baeldung.com/spring-security-method-security)
* [Securing Spring Data REST With PreAuthorize at Dzone](https://dzone.com/articles/securing-spring-data-rest-with-preauthorize)
* [at Github](https://github.com/farrelmr/introtospringdatarest/blob/3.0.0/src/main/java/com/javabullets/springdata/jparest/ParkrunCourseRepository.java)
* [Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/current/reference/html/authorization.html#el-access)
" [Testing Method Securit](https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/test-method.html)

```
Note that it isn't necessary to add the ROLE_ prefix here, Spring Security will add that prefix automatically.

If we don't want to have that prefix, we can consider using authority instead of role.

For example, let's declare a getUsernameInLowerCase method:

@PreAuthorize("hasAuthority('SYS_ADMIN')")
public String getUsernameLC(){
    return getUsername().toLowerCase();
}
We could test that using authorities:

@Test
@WithMockUser(username = "JOHN", authorities = { "SYS_ADMIN" })
public void givenAuthoritySysAdmin_whenCallGetUsernameLC_thenReturnUsername() {
    String username = userRoleService.getUsernameInLowerCase();
 
    assertEquals("john", username);
}
Conveniently, if we want to use the same user for many test cases, we can declare the @WithMockUser annotation at test class:

@RunWith(SpringRunner.class)
@ContextConfiguration
@WithMockUser(username = "john", roles = { "VIEWER" })
public class TestWithMockUserAtClassLevel {
    //...
}

```

## Dependency

```xml
<dependency>
  <groupId>ch.agilesolutions.boot</groupId>
  <artifactId>kerberos-spring-boot-starter</artifactId>
  <version>0.0.1</version>
</dependency>
```


### Example how to configure kerberos and AD role mapping:

```
spring:
  kerberos:
	ktab: ...
	ktab: ...
	signingkey: ...
	roles:
	  user: ad-user-group
	  admin: ad-admin-group
	  operator: ad-operator-group
```

## read
1. [Spring security core components](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/technical-overview.html)
2. [Orignal sources spnego filter](https://github.com/spring-projects/spring-security-kerberos)
3. [Control the session with Spring](https://www.baeldung.com/spring-security-session)
4. [Session management with Spring](https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/session-mgmt.html)
5. [5.4.4 Storing the SecurityContext between requests](https://docs.spring.io/spring-security/site/docs/3.0.x/reference/technical-overview.html)

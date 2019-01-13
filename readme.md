# A Spring Boot Starter for Kerberos AD integration

This is a Spring Boot starter for automatically configuring kerberos AD authentication on your spring Boot application.


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
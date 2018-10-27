# A Spring Boot Starter for Kerberos AD integration

This is a Spring Boot starter for automatically configuring kerberos AD authentication on your spring Boot application.


# Dependency

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

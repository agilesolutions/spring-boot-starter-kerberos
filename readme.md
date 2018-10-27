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


### This requires you to setup 2 properties on the configuration of you application:

1. spring.kerberos.ktab - location of the servers keytab file letting your app to a pre-authentication to AD.
2. spring.kerberos.spn - Service Principal Name you configured on your keytab file.

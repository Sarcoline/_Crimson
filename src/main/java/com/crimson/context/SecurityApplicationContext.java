package com.crimson.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//Konfiguracja spring security
@Configuration
@EnableWebSecurity
public class SecurityApplicationContext extends WebSecurityConfigurerAdapter {


}

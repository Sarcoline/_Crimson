package com.crimson.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//Konfiguracja spring security
@Configuration
@ImportResource("classpath:**/security-config.xml")
public class SecurityApplicationContext {


}

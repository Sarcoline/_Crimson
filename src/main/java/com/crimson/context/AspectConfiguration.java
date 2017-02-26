package com.crimson.context;

import com.crimson.core.logging.LogHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = LogHandler.class)
public class AspectConfiguration {

}

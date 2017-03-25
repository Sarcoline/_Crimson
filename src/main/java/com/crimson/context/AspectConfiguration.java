package com.crimson.context;

import com.crimson.logging.LogHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = LogHandler.class)
public class AspectConfiguration {

}

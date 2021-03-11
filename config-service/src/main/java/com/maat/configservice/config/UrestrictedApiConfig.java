package com.maat.configservice.config;

import com.maat.configservice.api.unrestricted.HealthApi;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {HealthApi.class})
public class UrestrictedApiConfig implements WebMvcConfigurer { //TODO: Move it to service commons
}


package com.maat.configservice.config;

import com.maat.configservice.api.restricted.ConfigWriteApi;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {ConfigWriteApi.class})
public class RestrictedApiConfig implements WebMvcConfigurer {}

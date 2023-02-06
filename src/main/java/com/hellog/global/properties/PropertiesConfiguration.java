package com.hellog.global.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class, S3Properties.class, EndPointProperties.class})
public class PropertiesConfiguration {
}

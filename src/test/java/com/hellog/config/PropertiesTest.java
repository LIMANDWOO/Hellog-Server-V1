package com.hellog.config;

import com.hellog.global.properties.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@EnableConfigurationProperties(JwtProperties.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application.yml"})
class PropertiesTest {

    @Autowired private JwtProperties jwtProperties;

    @Test
    @DisplayName("Jwt Properties Configuration")
    void testJwtConfiguration() {
        assertThat(jwtProperties).isNotNull();
        assertThat(jwtProperties.getIssuer()).isNotEmpty();
        assertThat(jwtProperties.getSecret().getAccess()).isNotEmpty();
    }
}

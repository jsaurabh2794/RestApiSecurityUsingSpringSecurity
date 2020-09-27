package com.saurabh.springsecurity.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.jwt")
@Configuration
@Getter
@Setter
@NoArgsConstructor
public class JwtConfig {
    private String secureKey;
    private String claimHeaderName;
    private int noOfDaysToExpireToken;
    private String tokenHeaderName;
    private String tokenPrefix;
}

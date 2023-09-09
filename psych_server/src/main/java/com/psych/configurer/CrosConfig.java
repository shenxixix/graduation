package com.psych.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *  跨域控制
 */
@Configuration
public class CrosConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration cor = new CorsConfiguration();
        cor.addAllowedHeader("*");
        cor.addAllowedMethod("*");
        cor.addAllowedOrigin("*");
        return cor;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}

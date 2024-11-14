package com.meme.translator.emotion_meme_translator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.0.51:5174/*")
                .allowedOrigins("http://localhost:5174/*")
                .allowedMethods("*")
                .allowCredentials(false)
                // 브라우저가 preflight 요청 결과를 3000초 동안 캐싱함
                .maxAge(3000);
    }
}
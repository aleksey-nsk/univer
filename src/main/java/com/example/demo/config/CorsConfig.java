package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("prod")
public class CorsConfig implements WebMvcConfigurer {

    // Настройка CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // обращаться к нашему приложению можно по любому внутреннему url
                .allowedOrigins("http://localhost:8080") // только указанный сайт может делать запросы
                .allowedMethods("*"); // запросы можно делать абсолютно всеми методами (GET, POST, PUT и т.д.)
    }
}

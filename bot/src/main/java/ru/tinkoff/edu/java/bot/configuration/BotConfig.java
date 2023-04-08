package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig{
    @Bean
    public String token(ApplicationConfig config) {
        return config.token();
    }
}

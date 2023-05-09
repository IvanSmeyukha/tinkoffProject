package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClientImpl;

@Configuration
public class ClientConfig {

    @Bean
    public ScrapperClientImpl scrapperClient(@Value("${scrapper.url:${scrapper.base-url}}") String url) {
        return new ScrapperClientImpl(WebClient.builder().baseUrl(url).build());
    }

}

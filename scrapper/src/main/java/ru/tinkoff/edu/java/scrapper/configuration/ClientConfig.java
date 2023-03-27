package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClientImpl;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClientImpl;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }

    @Bean
    public GitHubClient gitHubClient(@Value("${github.url}") String url, WebClient webClient){
        return new GitHubClientImpl(url, webClient);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(@Value("${stackoverflow.url}") String url, WebClient webClient){
        return new StackOverflowClientImpl(url, webClient);
    }
}

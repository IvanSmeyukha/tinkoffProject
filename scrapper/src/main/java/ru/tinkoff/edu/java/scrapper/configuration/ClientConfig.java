package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotClientImpl;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClientImpl;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClientImpl;

@Configuration
public class ClientConfig {

    @Bean
    public GitHubClient gitHubClient(@Value("${github.url:${github.base-url}}") String url) {
        return new GitHubClientImpl(WebClient.builder().baseUrl(url).build());
    }

    @Bean
    public StackOverflowClient stackOverflowClient(
        @Value("${stackoverflow.url:${stackoverflow.base-url}}") String url
    ) {
        return new StackOverflowClientImpl(WebClient.builder().baseUrl(url).build());
    }

    @Bean
    public BotClient botClient(@Value("${bot.url:${bot.base-url}}") String url) {
        return new BotClientImpl(WebClient.builder().baseUrl(url).build());
    }
}

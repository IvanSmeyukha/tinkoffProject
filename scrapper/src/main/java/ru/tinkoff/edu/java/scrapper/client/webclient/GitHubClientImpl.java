package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubClientResponse;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<GitHubClientResponse> fetchRepo(String user, String repo) {
        return webClient
            .get()
            .uri(String.format("%s/%s", user, repo))
            .retrieve()
            .bodyToMono(GitHubClientResponse.class);
    }
}

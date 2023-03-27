package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.dto.GitHubClientResponse;

public class GitHubClientImpl implements GitHubClient{
    private static final String BASE_URL = "https://api.github.com/repos/";
    private final String url;
    private final WebClient webClient;

    public GitHubClientImpl(String url, WebClient webClient){
        this.url = url;
        this.webClient = webClient;
    }

    public GitHubClientImpl(WebClient webClient){
        this(BASE_URL, webClient);
    }

    @Override
    public Mono<GitHubClientResponse> fetchRepo(String user, String repo) {
        return webClient
                .get()
                .uri(url + user + "/" + repo)
                .retrieve()
                .bodyToMono(GitHubClientResponse.class);
    }
}

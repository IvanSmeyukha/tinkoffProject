package ru.tinkoff.edu.java.scrapper.client.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubClientResponse;

public interface GitHubClient {
    Mono<GitHubClientResponse> fetchRepo(String user, String repo);
}

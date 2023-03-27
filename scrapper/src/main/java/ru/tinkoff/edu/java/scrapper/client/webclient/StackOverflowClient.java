package ru.tinkoff.edu.java.scrapper.client.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.dto.StackOverflowClientResponse;

public interface StackOverflowClient {
    Mono<StackOverflowClientResponse> fetchQuestion(Long id);
}

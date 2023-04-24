package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowClientResponse;

public class StackOverflowClientImpl implements StackOverflowClient{
    private static final String REQUEST_PARAM = "?site=stackoverflow";
    private final WebClient webClient;

    public StackOverflowClientImpl(WebClient webClient){
        this.webClient = webClient;
    }


    @Override
    public Mono<StackOverflowClientResponse> fetchQuestion(Long id) {
        return webClient
                .get()
                .uri(String.format("%d%s", id, REQUEST_PARAM))
                .retrieve()
                .bodyToMono(StackOverflowClientResponse.class);
    }
}

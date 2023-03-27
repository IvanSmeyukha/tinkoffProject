package ru.tinkoff.edu.java.scrapper.client.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.dto.StackOverflowClientResponse;

public class StackOverflowClientImpl implements StackOverflowClient{
    private static final String BASE_URL = "https://api.stackexchange.com/2.3/questions/";
    private static final String REQUEST_PARAM = "?site=stackoverflow";
    private final String url;
    private final WebClient webClient;

    public StackOverflowClientImpl(String url, WebClient webClient){
        this.url = url;
        this.webClient = webClient;
    }

    public StackOverflowClientImpl(WebClient webClient){
        this(BASE_URL, webClient);
    }

    @Override
    public Mono<StackOverflowClientResponse> fetchQuestion(Long id) {
        return webClient
                .get()
                .uri(url + id + REQUEST_PARAM)
                .retrieve()
                .bodyToMono(StackOverflowClientResponse.class);
    }
}

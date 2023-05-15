package ru.tinkoff.edu.java.scrapper.client.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;

@RequiredArgsConstructor
public class BotClientImpl implements BotClient {
    private final WebClient webClient;
    private static final String UPDATES = "/updates";

    @Override
    public void fetchUpdate(LinkUpdateRequest linkUpdateRequest) {
        webClient
            .post()
            .uri(UPDATES)
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}

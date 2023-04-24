package ru.tinkoff.edu.java.scrapper.client.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.GitHubClientResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;

public interface BotClient {
    void fetchUpdate(LinkUpdateRequest linkUpdateRequest);
}

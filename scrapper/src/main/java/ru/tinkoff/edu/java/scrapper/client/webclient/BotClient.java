package ru.tinkoff.edu.java.scrapper.client.webclient;

import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;

public interface BotClient {
    void fetchUpdate(LinkUpdateRequest linkUpdateRequest);
}

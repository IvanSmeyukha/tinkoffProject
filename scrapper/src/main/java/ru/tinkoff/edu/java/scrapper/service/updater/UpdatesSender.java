package ru.tinkoff.edu.java.scrapper.service.updater;

import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;

public interface UpdatesSender {
    void sendUpdate(LinkUpdateRequest update);
}

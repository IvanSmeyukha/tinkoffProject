package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

import java.net.URI;
import java.util.List;

public interface ChatService {
    void register(Long chatId);
    void unregister(Long chatId);

    List<Long> findChatsByUrl(URI url);
}

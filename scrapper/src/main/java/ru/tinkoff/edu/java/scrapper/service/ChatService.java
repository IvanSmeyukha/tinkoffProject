package ru.tinkoff.edu.java.scrapper.service;

import java.net.URI;
import java.util.List;

public interface ChatService {
    void register(Long chatId);

    void unregister(Long chatId);

    List<Long> findChatsByUrl(URI url);
}

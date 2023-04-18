package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

public interface ChatService {
    void register(Long chatId);
    Chat unregister(Long chatId);
}
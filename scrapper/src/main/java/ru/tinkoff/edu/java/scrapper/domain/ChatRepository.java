package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface ChatRepository{
    void add(Long chatId);

    Optional<List<Chat>> findAll();

    Optional<Chat> findById(Long chatId);

    Optional<List<Chat>> findChatsByUrl(URI url);

    Optional<Chat> remove(Long chatId);
}

package ru.tinkoff.edu.java.scrapper.domain;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

public interface ChatRepository {
    void add(Long chatId);

    Optional<List<Chat>> findAll();

    Optional<Chat> findById(Long chatId);

    Optional<List<Long>> findChatsByUrl(URI url);

    void remove(Long chatId);
}

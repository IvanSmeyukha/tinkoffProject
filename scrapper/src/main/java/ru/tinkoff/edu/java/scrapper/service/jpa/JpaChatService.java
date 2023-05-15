package ru.tinkoff.edu.java.scrapper.service.jpa;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    public void register(Long chatId) {
        Chat chat = new Chat(chatId);
        chatRepository.save(chat);
    }

    @Override
    public void unregister(Long chatId) {
        Chat chat = new Chat(chatId);
        chatRepository.delete(chat);
    }

    @Override
    public List<Long> findChatsByUrl(URI url) {
        Link link = linkRepository.findByUrl(url.toString()).get();
        return link.getChats().stream().map(Chat::getId).toList();
    }
}

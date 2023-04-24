package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.ChatRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;


@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;
    @Override
    public void register(Long chatId) {
        try {
            chatRepository.add(chatId);
        } catch (DuplicateKeyException ignored){
        }
    }

    @Override
    public void unregister(Long chatId) {
        chatRepository.remove(chatId);
    }
}

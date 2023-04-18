package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final ChatRepository chatRepository;
    @Override
    public void register(Long chatId) {
        chatRepository.add(chatId);
    }

    @Override
    public Chat unregister(Long chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if(chatOptional.isPresent()){
            return chatRepository.remove(chatId).get();
        }
        return null;
    }
}

package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.dto.ChatResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TgChatController {
    private final ChatService chatService;

    @PostMapping("/tg-chat/{id}")
    public void registerChat(@PathVariable("id") Long id) {
        chatService.register(id);
//        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        chatService.unregister(id);
//        return ResponseEntity.ok().build();
    }
}

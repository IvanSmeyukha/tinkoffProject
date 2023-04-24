package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

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

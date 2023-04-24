package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.Bot;

@RestController
@RequiredArgsConstructor
public class UpdateController {
    private final Bot bot;

    @PostMapping(path = "/updates")
    public void sendUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
//        linkUpdate.tgChatIds().forEach(id ->
//                bot.execute(new SendMessage(id, String.format("%s\n%s", linkUpdate.description(), linkUpdate.url())))
//        );
        System.out.println("aaaa");
        for(Long id : linkUpdate.tgChatIds()){
            System.out.println(id);
            bot.execute(new SendMessage(id, String.format("%s\n%s", linkUpdate.description(), linkUpdate.url())));
        }
    }
}

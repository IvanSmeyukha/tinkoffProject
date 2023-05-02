package ru.tinkoff.edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.Bot;
import ru.tinkoff.edu.java.bot.service.UpdatesSender;

@RestController
@RequiredArgsConstructor
public class UpdateController {
    private final UpdatesSender updatesSender;

    @PostMapping(path = "/updates")
    public void sendUpdate(@RequestBody LinkUpdateRequest update) {
        updatesSender.send(update);
    }
}

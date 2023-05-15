package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class UpdatesSender {
    private final Bot bot;

    public void send(LinkUpdateRequest update) {
        for (Long id : update.tgChatIds()) {
            bot.execute(new SendMessage(id, update.description() + update.url()));
        }
    }
}

package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class UpdatesSender {
    private final Bot bot;

    public void send(LinkUpdateRequest update) {
        for(Long id : update.tgChatIds()) {
            bot.execute(
                    new SendMessage(
                            id,
                            String.format(
                                    "%s\n%s",
                                    update.description(),
                                    update.url()
                            )
                    )
            );
        }
    }
}

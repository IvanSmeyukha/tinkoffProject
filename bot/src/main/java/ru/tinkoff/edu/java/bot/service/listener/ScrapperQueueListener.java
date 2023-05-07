package ru.tinkoff.edu.java.bot.service.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UpdatesSender;

@RabbitListener(queues = "${app.queue-name}")
@RequiredArgsConstructor
public class ScrapperQueueListener {
    private final UpdatesSender updatesSender;

    @RabbitHandler
    public void receiver(LinkUpdateRequest update) {
        updatesSender.send(update);
    }
}
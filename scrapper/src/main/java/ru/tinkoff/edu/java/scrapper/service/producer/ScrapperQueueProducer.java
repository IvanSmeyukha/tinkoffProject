package ru.tinkoff.edu.java.scrapper.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.updater.UpdatesSender;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements UpdatesSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void sendUpdate(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(queue.getName(), update);
    }

}

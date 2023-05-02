package ru.tinkoff.edu.java.scrapper.service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;


@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void send(LinkUpdateRequest update) {
        rabbitTemplate.convertAndSend(queue.getName(), update);
    }

}

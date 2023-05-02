package ru.tinkoff.edu.java.scrapper.service.updater;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.service.producer.ScrapperQueueProducer;

@Service
public class UpdatesSender {
    private final ScrapperQueueProducer queueProducer;
    private final BotClient botClient;
    private final Boolean useQueue;

    public UpdatesSender(
            ScrapperQueueProducer queueProducer,
            BotClient webService,
            ApplicationConfig config
    ) {
        this.queueProducer = queueProducer;
        this.botClient = webService;
        this.useQueue = config.useQuery();
    }


    public void sendUpdate(LinkUpdateRequest update) {
        if (useQueue) {
            queueProducer.send(update);
        } else {
            botClient.fetchUpdate(update);
        }
    }
}

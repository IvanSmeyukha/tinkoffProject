package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkService linkService(JpaLinkRepository linkRepository, JpaChatRepository chatRepository) {
        return new JpaLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService chatService(JpaLinkRepository linkRepository, JpaChatRepository chatRepository) {
        return new JpaChatService(linkRepository, chatRepository);
    }
}

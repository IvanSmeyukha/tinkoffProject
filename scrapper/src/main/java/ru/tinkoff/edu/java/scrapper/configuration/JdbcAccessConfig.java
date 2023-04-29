package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public JdbcLinkRepository linkRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkRepository(jdbcTemplate);
    }

    @Bean
    public JdbcChatRepository chatRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcChatRepository(jdbcTemplate);
    }

    @Bean
    public LinkService linkService(JdbcLinkRepository linkRepository) {
        return new JdbcLinkService(linkRepository);
    }

    @Bean
    public ChatService chatService(JdbcChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }
}

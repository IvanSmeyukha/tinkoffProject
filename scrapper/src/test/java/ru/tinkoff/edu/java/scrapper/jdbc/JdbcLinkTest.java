package ru.tinkoff.edu.java.scrapper.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcLinkTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository linkRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final URI TEST_LINK = URI.create("https://edu.tinkoff.ru");

    @Configuration
    static class MyIntegrationTests {
        @DynamicPropertySource
        static void registerProperties(DynamicPropertyRegistry registry) {
            registry.add("database-access-type", () -> "jdbc");
        }
    }

    @Test
    @Transactional
    @Rollback
    public void add() {
        Link link = linkRepository.add(TEST_LINK).get();

        assertNotNull(link);
        assertEquals(TEST_LINK.toString(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrl() {
        addLink();
        Link link = linkRepository.findLinkByUrl(TEST_LINK).get();

        assertNotNull(link);
        assertEquals(TEST_LINK.toString(), link.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    public void addChatLinkSubscription() {
        addLink();
        Link link = linkRepository.findLinkByUrl(TEST_LINK).get();
        Chat chat = addChat();
        linkRepository.addChatLinkSubscription(chat.getId(), link.getId());
        String query = """
            SELECT link_id FROM links_chats
            WHERE chat_id = ?
            """;
        Long linkId = jdbcTemplate.queryForObject(query, Long.class, chat.getId());

        assertNotNull(linkId);
    }

    @Test
    @Transactional
    @Rollback
    public void findLinksByChatId() {
        Link link = addLink();
        Chat chat = addChat();
        linkRepository.addChatLinkSubscription(chat.getId(), link.getId());

        List<Link> linkList = linkRepository.findLinksByChatId(chat.getId()).get();

        assert (!linkList.isEmpty());
    }

    private Chat addChat() {
        String query = """
            INSERT INTO chats (id)
            VALUES (1)
            RETURNING id
            """;
        return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Chat.class));
    }

    private Link addLink() {
        String query = """
            INSERT INTO links (url, last_check_time)
            VALUES (?, ?)
            RETURNING *
            """;
        return jdbcTemplate.queryForObject(
            query,
            new BeanPropertyRowMapper<>(Link.class),
            TEST_LINK.toString(),
            OffsetDateTime.now()
        );
    }

}

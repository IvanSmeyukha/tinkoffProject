package ru.tinkoff.edu.java.scrapper.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcChatTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final URI TEST_LINK = URI.create("https://edu.tinkoff.ru");

    private final Long CHAT_ID = 1L;
    @Test
    @Transactional
    @Rollback
    public void add() {
        chatRepository.add(CHAT_ID);

        String query = """
                SELECT * FROM chats
                WHERE id = ?
                """;
        Chat chat =  jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Chat.class), CHAT_ID);

        assertNotNull(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void findById() {
        addChat();
        Chat chat = chatRepository.findById(CHAT_ID).get();

        assertNotNull(chat);
    }

    @Test
    @Transactional
    @Rollback
    public void findChatsByUrl() {
        Link link = addLink();
        Chat chat = addChat();
        addSubscription(chat.getId(), link.getId());
        List<Long> chatList = chatRepository.findChatsByUrl(TEST_LINK).get();

        assert(!chatList.isEmpty());
    }

    private void addSubscription(Long chatId, Long linkId) {
        String query = """
                INSERT INTO links_chats (chat_id, link_id)
                VALUES (?, ?)
                """;
        jdbcTemplate.update(
                query,
                chatId,
                linkId
        );
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

package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.ChatRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository implements ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_CHAT_QUERY = "INSERT INTO chats (id) VALUES (?)";
    private static final String SELECT_ALL_CHATS_QUERY = "SELECT * FROM chats";
    private static final String SELECT_CHAT_BY_ID_QUERY = "SELECT * FROM chats WHERE id = ?";
    private static final String DELETE_CHAT_BY_ID_QUERY = "DELETE FROM chats WHERE id = ?";
    private static final String SELECT_CHATS_BY_URL_QUERY = """
            SELECT chat_id
            FROM chats
            JOIN links_chats ON chats.id = links_chats.chat_id
            JOIN links ON links.id = links_chats.link_id
            WHERE links.url = ?
            """;


    @Override
    public void add(Long chatId) {
        jdbcTemplate.update(
                INSERT_CHAT_QUERY,
                chatId
        );
    }

    @Override
    public Optional<List<Chat>> findAll() {
        return Optional.of(jdbcTemplate.query(
                        SELECT_ALL_CHATS_QUERY,
                        new BeanPropertyRowMapper<>(Chat.class)
                )
        );
    }

    @Override
    public Optional<Chat> findById(Long chatId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                        SELECT_CHAT_BY_ID_QUERY,
                        new BeanPropertyRowMapper<>(Chat.class),
                        chatId
                )
        );
    }

    @Override
    public Optional<List<Long>> findChatsByUrl(URI url) {
        return Optional.of(jdbcTemplate.queryForList(
                        SELECT_CHATS_BY_URL_QUERY,
                        Long.class,
                        url.toString()
                )
        );
    }

    @Override
    public void remove(Long chatId) {
        jdbcTemplate.update(
                DELETE_CHAT_BY_ID_QUERY,
                chatId
        );
    }
}

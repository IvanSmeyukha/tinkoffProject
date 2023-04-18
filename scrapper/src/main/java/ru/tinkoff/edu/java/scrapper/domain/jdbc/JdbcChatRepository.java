package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.ChatRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;

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
    private static final String SELECT_CHATS_BY_URL_QUERY = "SELECT * FROM links_chats WHERE url = ?";

    @Override
    public void add(Long chatId){
        jdbcTemplate.update(INSERT_CHAT_QUERY, chatId);
    }

    @Override
    public Optional<List<Chat>> findAll(){
        return Optional.of(jdbcTemplate.queryForList(SELECT_ALL_CHATS_QUERY, Chat.class));
    }

    @Override
    public Optional<Chat> findById(Long chatId){
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_CHAT_BY_ID_QUERY, Chat.class, chatId));
    }

    @Override
    public Optional<List<Chat>> findChatsByUrl(URI url) {
        return Optional.of(jdbcTemplate.queryForList(SELECT_CHATS_BY_URL_QUERY, Chat.class, url));
    }

    @Override
    public Optional<Chat> remove(Long chatId){
        return Optional.ofNullable(jdbcTemplate.queryForObject(DELETE_CHAT_BY_ID_QUERY, Chat.class, chatId));
    }
}

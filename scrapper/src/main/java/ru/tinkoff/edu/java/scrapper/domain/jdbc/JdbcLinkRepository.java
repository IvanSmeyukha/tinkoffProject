package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.LinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.entity.Subscription;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_LINK_QUERY = "INSERT INTO links (url) VALUES (?)";
    private static final String SELECT_ALL_LINKS_QUERY = "SELECT * FROM links";
    private static final String SELECT_LINK_BY_ID_QUERY = "SELECT * FROM links WHERE id = ?";
    private static final String SELECT_LINK_BY_URL_QUERY = "SELECT * FROM links WHERE url = ?";
    private static final String SELECT_LINKS_BY_CHAT_ID_QUERY = "SELECT * FROM links_chats WHERE chat_id = ?";
    private static final String UPDATE_LONG_TIME_AGO_CHECKED_LINKS_QUERY = """
            UPDATE links
            SET last_check_time = ?
            WHERE last_check_time > ?
            RETURNING id, url, last_check_time, last_update_time
            """;
    private static final String DELETE_LINK_BY_ID_QUERY = "DELETE FROM links WHERE id = ?";
    private static final String ADD_SUBSCRIPTION_QUERY = """
            INSERT INTO links_chats (link_id, chat_id)
                VALUES (?, ?)
            """;

    @Override
    public Optional<Link> add(URI url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(INSERT_LINK_QUERY, Link.class, url));
    }

    @Override
    public Optional<List<Link>> findAllLinks() {
        return Optional.ofNullable(jdbcTemplate.queryForList(SELECT_ALL_LINKS_QUERY, Link.class));
    }

    @Override
    public Optional<Link> findLinkById(Long linkId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_LINK_BY_ID_QUERY, Link.class, linkId));
    }

    @Override
    public Optional<Link> findLinkByUrl(String url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_LINK_BY_URL_QUERY, Link.class, url));
    }

    @Override
    public Optional<List<Link>> findLinksByChatId(Long chatId) {
        return Optional.of(jdbcTemplate.queryForList(SELECT_LINKS_BY_CHAT_ID_QUERY, Link.class, chatId));
    }

    @Override
    public Optional<List<Link>> findLongTimeAgoCheckedLinks(OffsetDateTime lastCheckDate) {
        return Optional.of(
                jdbcTemplate.query(
                        UPDATE_LONG_TIME_AGO_CHECKED_LINKS_QUERY,
                        new BeanPropertyRowMapper<>(Link.class),
                        OffsetDateTime.now(),
                        lastCheckDate
                )
        );
    }

    @Override
    public Optional<Link> removeLink(Long linkId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(DELETE_LINK_BY_ID_QUERY, Link.class, linkId));
    }

    @Override
    public Optional<Subscription> addChatLinkSubscription(Long chatId, Long linkId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(ADD_SUBSCRIPTION_QUERY, Subscription.class, linkId, chatId));
    }
}

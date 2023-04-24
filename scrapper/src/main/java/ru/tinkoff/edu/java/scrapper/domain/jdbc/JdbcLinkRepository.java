package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.objenesis.instantiator.sun.SunReflectionFactoryInstantiator;
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

    private static final String INSERT_LINK_QUERY = """
            INSERT INTO links (url, last_check_time, last_update_time)
            VALUES (?, ?, ?)
            RETURNING id, url, last_check_time, last_update_time
            """;

    private static final String SELECT_LINK_BY_URL_QUERY = "SELECT id, url, last_check_time, last_update_time FROM links WHERE url = ?";
    private static final String SELECT_LINKS_BY_CHAT_ID_QUERY = """
            SELECT *
            FROM links
            JOIN links_chats
            ON links.id = links_chats.link_id
            WHERE links_chats.chat_id = ?
            """;

    private static final String UPDATE_LONG_TIME_AGO_CHECKED_LINKS_QUERY = """
            UPDATE links
            SET last_check_time = ?
            WHERE last_check_time < ?
            RETURNING *
            """;

    private static final String UPDATE_LINK_QUERY = """
            UPDATE links 
            SET last_update_time = ?
            WHERE id = ?
            """;
    private static final String ADD_SUBSCRIPTION_QUERY = """
            INSERT INTO links_chats (link_id, chat_id)
            VALUES (?, ?)
            """;

    private static final String DELETE_SUBSCRIPTION_QUERY = """
            DELETE FROM links_chats
            WHERE chat_id = ? AND link_id IN (SELECT id FROM links WHERE url = ?)
            RETURNING link_id
            """;

    private static final String DELETE_UNUSED_LINKS_QUERY = """
            DELETE FROM links
            WHERE id = ?
            AND NOT EXISTS (
                SELECT 1 FROM links_chats WHERE link_id = ?
            )
            """;

    @Override
    public Optional<Link> add(URI url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                        INSERT_LINK_QUERY,
                        new BeanPropertyRowMapper<>(Link.class),
                        url.toString(),
                        OffsetDateTime.now(),
                        OffsetDateTime.now()
                )
        );
    }

    @Override
    public Optional<Link> findLinkByUrl(URI url) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            SELECT_LINK_BY_URL_QUERY,
                            new BeanPropertyRowMapper<>(Link.class),
                            url.toString()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Link>> findLinksByChatId(Long chatId) {
        return Optional.of(jdbcTemplate.query(
                        SELECT_LINKS_BY_CHAT_ID_QUERY,
                        new BeanPropertyRowMapper<>(Link.class),
                        chatId
                )
        );
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
    public void updateLink(Link link) {
        jdbcTemplate.update(
                UPDATE_LINK_QUERY,
                link.getLastUpdateTime(),
                link.getId()
        );
    }


    @Override
    public void removeUnusedLinks(Long linkId) {
        jdbcTemplate.update(
                DELETE_UNUSED_LINKS_QUERY,
                linkId,
                linkId
        );
    }

    @Override
    public Optional<Long> removeChatLinkSubscription(Long chatId, URI url) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                        DELETE_SUBSCRIPTION_QUERY,
                        Long.class,
                        chatId,
                        url.toString()
                )
        );
    }

    @Override
    public void addChatLinkSubscription(Long chatId, Long linkId) {
        jdbcTemplate.update(
                ADD_SUBSCRIPTION_QUERY,
                linkId,
                chatId
        );
    }
}

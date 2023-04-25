package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {

    @Query(value = """
            DELETE FROM links
            WHERE id = :linkId
            AND NOT EXISTS (
                SELECT 1 FROM links_chats WHERE link_id = :linkId
            )
            RETURNING *
            """,
            nativeQuery = true)
    List<Link> removeUnusedLinks(@Param("linkId") Long linkId);


    List<Link> findLinksByLastCheckTimeAfter(OffsetDateTime lastCheckTime);


    Optional<Link> findByUrl(String url);
}

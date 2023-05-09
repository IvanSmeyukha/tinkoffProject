package ru.tinkoff.edu.java.scrapper.domain.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

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

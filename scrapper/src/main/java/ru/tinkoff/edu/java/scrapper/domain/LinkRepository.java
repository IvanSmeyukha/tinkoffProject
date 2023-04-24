package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Optional<Link> add(URI url);

    Optional<Long> removeChatLinkSubscription(Long chatId, URI url);

    void removeUnusedLinks(Long linkId);

    Optional<Link> findLinkByUrl(URI url);

    Optional<List<Link>> findLinksByChatId(Long chatId);

    Optional<List<Link>> findLongTimeAgoCheckedLinks(OffsetDateTime lastCheckDate);

    void addChatLinkSubscription(Long chatId, Long linkId);

    void updateLink(Link link);
}

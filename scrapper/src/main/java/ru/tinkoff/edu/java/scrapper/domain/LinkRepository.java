package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.dto.entity.Subscription;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Optional<Link> add(URI url);

    Optional<List<Link>> findAllLinks();

    Optional<Link> findLinkById(Long linkId);

    Optional<Link> removeLink(Long linkId);

    Optional<Link> findLinkByUrl(String url);

    Optional<List<Link>> findLinksByChatId(Long chatId);


    Optional<List<Link>> findLongTimeAgoCheckedLinks(OffsetDateTime lastCheckDate);

    Optional<Subscription> addChatLinkSubscription(Long chatId, Long linkId);
}

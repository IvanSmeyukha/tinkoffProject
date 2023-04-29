package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.LinkFormatException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    Link add(Long chatId, URI url) throws LinkFormatException;

    Link removeSubscription(Long chatId, URI url);

    List<Link> listAll(Long chatId);

    List<Link> findLongTimeAgoCheckedLinks(OffsetDateTime lastCheckDate);

    void updateLink(Link link);
}

package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.LinkFormatException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    @Transactional
    public Link add(Long chatId, URI url) throws LinkFormatException {
        Link link = new Link();
        link.setUrl(url.toString());
        link.setLastCheckTime(OffsetDateTime.now());
        link.setLastUpdateTime(OffsetDateTime.now());
        Optional<Link> linkOptional = linkRepository.findByUrl(url.toString());
        Link addedlink = linkOptional.orElseGet(() -> linkRepository.save(link));
        Chat chat = chatRepository.findById(chatId).get();
        addedlink.getChats().add(chat);
        return addedlink;
    }

    @Override
    @Transactional
    public Link removeSubscription(Long chatId, URI url) {
        Link link;
        Optional<Link> linkOptional = linkRepository.findByUrl(url.toString());
        if (linkOptional.isPresent()) {
            link = linkOptional.get();
            Chat chat = chatRepository.findById(chatId).get();
            link.getChats().remove(chat);
            linkRepository.removeUnusedLinks(link.getId());
            return link;
        }
        link = new Link();
        link.setUrl(url.toString());
        return link;
    }

    @Override
    public List<Link> listAll(Long chatId) {
        return chatRepository.findById(chatId).get().getLinks().stream().toList();
    }

    @Override
    public List<Link> findLongTimeAgoCheckedLinks(OffsetDateTime lastCheckDate) {
        List<Link> links = linkRepository.findLinksByLastCheckTimeAfter(lastCheckDate);
        links.forEach(link -> {
                    link.setLastCheckTime(OffsetDateTime.now());
                    linkRepository.save(link);
                }
        );
        return links;
    }

    @Override
    public void updateLink(Link link) {
        linkRepository.save(link);
    }
}

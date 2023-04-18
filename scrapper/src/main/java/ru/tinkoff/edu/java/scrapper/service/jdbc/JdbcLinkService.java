package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    @Override
    public Link add(Long chatId, URI url) {
        Link link;
        Optional<Link> linkOptional = linkRepository.findLinkByUrl(url.toString());
        if(linkOptional.isEmpty()){
            link = linkRepository.add(url).get();
            linkRepository.addChatLinkSubscription(chatId, linkOptional.get().getId());
        } else {
            link = linkRepository.findLinkByUrl(url.toString()).get();
        }
        linkRepository.addChatLinkSubscription(chatId, link.getId());
        return link;
    }

    @Transactional
    @Override
    public Link remove(Long chatId, URI url) {
        Link link = null;
        Optional<Link> linkOptional = linkRepository.findLinkByUrl(url.toString());
        if(linkOptional.isPresent()){
            link = linkRepository.removeLink(linkOptional.get().getId()).get();
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(Long chatId) {
        return linkRepository.findLinksByChatId(chatId).get();
    }

}

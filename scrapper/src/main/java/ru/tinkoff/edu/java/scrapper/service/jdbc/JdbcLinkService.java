package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.exception.LinkFormatException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Transactional
    @Override
    public Link add(Long chatId, URI url) throws LinkFormatException {
        Link link;
        Optional<Link> linkOptional = linkRepository.findLinkByUrl(url);
        link = linkOptional.orElseGet(() -> linkRepository.add(url).get());
        try {
            linkRepository.addChatLinkSubscription(chatId, link.getId());
        } catch (DuplicateKeyException ignored){
        }
        return link;
    }


    @Transactional
    @Override
    public Link removeSubscription(Long chatId, URI url) {
        Optional<Link> linkOptional = linkRepository.findLinkByUrl(url);
        Long linkId = linkRepository.removeChatLinkSubscription(chatId, url).get();
        linkRepository.removeUnusedLinks(linkId);
        return linkOptional.get();
    }

    @Override
    public List<Link> listAll(Long chatId) {
        return linkRepository.findLinksByChatId(chatId).get();
    }

}

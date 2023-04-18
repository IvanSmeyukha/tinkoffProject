package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(Long chatId, URI url);
    Link remove(Long chatId, URI url);
    Collection<Link> listAll(Long chatId);
}

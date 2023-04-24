package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exception.LinkFormatException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/links/{id}")
    public ListLinksResponse getLinks(@PathVariable Long id) {
        List<LinkResponse> listLinksResponse = linkService
                .listAll(id)
                .stream()
                .map(link -> new LinkResponse(link.getUrl()))
                .toList();
        return new ListLinksResponse(listLinksResponse, listLinksResponse.size());
    }

    @PostMapping("/links/{id}")
    public LinkResponse addLink(@PathVariable Long id, @RequestBody AddLinkRequest request) throws LinkFormatException {
        return new LinkResponse(linkService.add(id, request.url()).getUrl());
    }

    @DeleteMapping("/links/{id}")
    public LinkResponse removeLink(@PathVariable Long id, @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(linkService.removeSubscription(id, request.url()).getUrl());
    }
}

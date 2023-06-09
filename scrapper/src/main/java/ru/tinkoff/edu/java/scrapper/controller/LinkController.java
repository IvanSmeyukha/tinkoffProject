package ru.tinkoff.edu.java.scrapper.controller;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exception.LinkFormatException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/links/{id}")
    public ListLinksResponse getLinks(@PathVariable Long id) {
        List<LinkResponse> listLinksResponse = linkService
            .listAll(id)
            .stream()
            .map(link -> new LinkResponse(URI.create(link.getUrl())))
            .toList();
        return new ListLinksResponse(listLinksResponse, listLinksResponse.size());
    }

    @PostMapping("/links/{id}")
    public LinkResponse addLink(@PathVariable Long id, @RequestBody AddLinkRequest request) throws LinkFormatException {
        return new LinkResponse(URI.create(linkService.add(id, request.url()).getUrl()));
    }

    @DeleteMapping("/links/{id}")
    public LinkResponse removeLink(@PathVariable Long id, @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(URI.create(linkService.removeSubscription(id, request.url()).getUrl()));
    }
}

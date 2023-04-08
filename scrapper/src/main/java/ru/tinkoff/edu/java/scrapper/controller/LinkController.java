package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.util.Collections;

@RestController
public class LinkController {
    @GetMapping("/links/{id}")
    public ListLinksResponse getLinks(@PathVariable Long id) {
        return new ListLinksResponse(Collections.emptyList(), 0);
    }

    @PostMapping("/links/{id}")
    public LinkResponse addLink(@PathVariable Long id, @RequestBody AddLinkRequest request) {
        return new LinkResponse("", "");
    }

    @DeleteMapping("/links/{id}")
    public LinkResponse removeLink(@PathVariable Long id, @RequestBody RemoveLinkRequest request) {
        return new LinkResponse("", "");
    }
}

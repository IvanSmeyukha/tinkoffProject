package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
public class LinkController {
    @GetMapping("/links/{id}")
    public ResponseEntity<ListLinksResponse> getLinks(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/links/{id}")
    public ResponseEntity<LinkResponse> addLink(@PathVariable Long id, @RequestBody AddLinkRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/links/{id}")
    public ResponseEntity<LinkResponse> removeLink(@PathVariable Long id, @RequestBody RemoveLinkRequest request) {
        return ResponseEntity.ok().build();
    }
}

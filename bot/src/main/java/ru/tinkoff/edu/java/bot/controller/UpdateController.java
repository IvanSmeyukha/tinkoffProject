package ru.tinkoff.edu.java.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@RestController
public class UpdateController {
    @PostMapping(path = "/updates")
    public ResponseEntity<String> sendUpdate(@RequestBody LinkUpdateRequest linkUpdate){
        return ResponseEntity.ok().build();
    }
}

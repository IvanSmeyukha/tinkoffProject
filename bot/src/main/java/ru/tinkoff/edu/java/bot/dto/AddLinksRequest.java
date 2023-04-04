package ru.tinkoff.edu.java.bot.dto;

import java.net.URI;

public record AddLinksRequest(long id, URI link) {
}

package ru.tinkoff.edu.java.bot.dto;

import java.net.URI;

public record AddLinkRequest(long id, URI link) {
}

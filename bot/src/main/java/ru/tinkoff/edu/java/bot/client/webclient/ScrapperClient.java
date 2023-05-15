package ru.tinkoff.edu.java.bot.client.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.DeleteChatRequest;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksRequest;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RegisterChatRequest;

public interface ScrapperClient {
    Mono<Void> registerChat(RegisterChatRequest request);

    Mono<Void> deleteChat(DeleteChatRequest request);

    Mono<LinkResponse> addLink(Long id, String url);

    Mono<ListLinksResponse> getLinks(ListLinksRequest request);

    Mono<LinkResponse> removeLink(Long id, String url);
}

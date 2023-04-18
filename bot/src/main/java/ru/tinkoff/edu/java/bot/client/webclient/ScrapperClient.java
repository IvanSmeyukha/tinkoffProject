package ru.tinkoff.edu.java.bot.client.webclient;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.*;

public interface ScrapperClient {
    Mono<Void> registerChat(RegisterChatRequest request);
    Mono<Void> deleteChat(DeleteChatRequest request);
    Mono<LinkResponse> addLink(AddLinkRequest request);
    Mono<ListLinksResponse> getLinks(ListLinksRequest request);
    Mono<LinkResponse> removeLink(RemoveLinkRequest request);
}

package ru.tinkoff.edu.java.bot.client.webclient;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.*;

import java.net.URI;

public class ScrapperClientImpl implements ScrapperClient {
    private static final String TGCHAT_REQUEST_URI = "/tg-chat/{id}";
    private static final String LINKS_REQUEST_URI = "/links/{id}";
    private final WebClient webClient;

    public ScrapperClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<Void> registerChat(RegisterChatRequest request) {
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(TGCHAT_REQUEST_URI)
                        .build(request.id()))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> deleteChat(DeleteChatRequest request) {
        return webClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path(TGCHAT_REQUEST_URI)
                        .build(request.id()))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<ListLinksResponse> getLinks(ListLinksRequest request) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS_REQUEST_URI)
                        .build(request.id()))
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }

    @Override
    public Mono<LinkResponse> removeLink(Long id, String url) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS_REQUEST_URI)
                        .build(id))
                .bodyValue(new RemoveLinkRequest(URI.create(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }

    @Override
    public Mono<LinkResponse> addLink(Long id, String url) {
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(LINKS_REQUEST_URI)
                        .build(id))
                .bodyValue(new AddLinkRequest(URI.create(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }

}

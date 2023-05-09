package ru.tinkoff.edu.java.scrapper.service.updater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.GitHubLinkResponse;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.LinkParserResponse;
import ru.tinkoff.edu.java.linkparser.StackOverflowLinkResponse;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubClientResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowClientResponse;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LinkUpdaterService implements LinkUpdater {
    private final LinkService linkService;
    private final ChatService chatService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final UpdatesSender updatesSender;

    @Transactional
    @Override
    public void update(Duration checkInterval) {
        List<Link> uncheckedLinks =
            linkService.findLongTimeAgoCheckedLinks(OffsetDateTime.now().minus(checkInterval));
        for (Link link : uncheckedLinks) {
            LinkParserResponse response = LinkParser.parseLink(link.getUrl().toString());
            if (response instanceof GitHubLinkResponse resp) {
                checkGitHubUpdates(resp, link);
            } else if (response instanceof StackOverflowLinkResponse resp) {
                checkStackOverflowUpdates(resp, link);
            }
        }
    }

    private void checkGitHubUpdates(GitHubLinkResponse gitHubLinkResponse, Link link) {
        GitHubClientResponse gitHubClientResponse =
            gitHubClient
                .fetchRepo(gitHubLinkResponse.user(), gitHubLinkResponse.repository())
                .block();
        if (gitHubClientResponse.updatedAt().isAfter(link.getLastUpdateTime())) {
            sendUpdates(link, "Содержимое ссылки было обновлено");
            link.setLastUpdateTime(gitHubClientResponse.updatedAt());
            linkService.updateLink(link);
        }
    }

    private void checkStackOverflowUpdates(StackOverflowLinkResponse stackOverflowLinkResponse, Link link) {
        StackOverflowClientResponse stackOverflowClientResponse =
            stackOverflowClient
                .fetchQuestion(stackOverflowLinkResponse.id())
                .block();
        stackOverflowClientResponse.items()
            .stream()
            .filter(response ->
                response.lastEditDate() != null &&
                    Objects.equals(response.questionId(), stackOverflowLinkResponse.id())
            )
            .forEach(response -> {
                if (response.lastEditDate().isAfter(link.getLastUpdateTime())) {
                    sendUpdates(link, "Содержимое ссылки было обновлено");
                    link.setLastUpdateTime(response.lastEditDate());
                    linkService.updateLink(link);
                }
            });
    }

    private void sendUpdates(Link link, String description) {
        updatesSender.sendUpdate(
            new LinkUpdateRequest(
                URI.create(link.getUrl()),
                description,
                chatService
                    .findChatsByUrl(URI.create(link.getUrl()))
            )
        );
    }
}

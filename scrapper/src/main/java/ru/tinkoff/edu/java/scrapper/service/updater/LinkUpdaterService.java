package ru.tinkoff.edu.java.scrapper.service.updater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkparser.GitHubLinkResponse;
import ru.tinkoff.edu.java.linkparser.LinkParser;
import ru.tinkoff.edu.java.linkparser.LinkParserResponse;
import ru.tinkoff.edu.java.linkparser.StackOverflowLinkResponse;
import ru.tinkoff.edu.java.scrapper.client.webclient.BotClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.webclient.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.domain.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.LinkRepository;
import ru.tinkoff.edu.java.scrapper.dto.GitHubClientResponse;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.StackOverflowClientResponse;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.Link;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkUpdaterService implements LinkUpdater {
    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    @Override
    public void update(Duration checkInterval) {
        List<Link> uncheckedLinks =
                linkRepository
                        .findLongTimeAgoCheckedLinks(
                                OffsetDateTime.now()
                                        .minus(checkInterval)
                        ).get();
        for (Link link : uncheckedLinks) {
            LinkParserResponse response = LinkParser.parseLink(link.getUrl().toString());
            if(response instanceof GitHubLinkResponse resp ){
                checkGitHubUpdates(resp, link);
            } else if(response instanceof StackOverflowLinkResponse resp){
                checkStackOverflowUpdates(resp, link);
            }
//            switch (response) {
//                case GitHubLinkResponse resp -> checkGitHubUpdates(resp, link);
//                case StackOverflowLinkResponse resp -> checkStackOverflowUpdates(resp, link);
//            }
        }
    }

    private void checkGitHubUpdates(GitHubLinkResponse gitHubLinkResponse, Link link) {
        GitHubClientResponse gitHubClientResponse =
                gitHubClient
                        .fetchRepo(gitHubLinkResponse.user(), gitHubLinkResponse.repository())
                        .block();
        if (gitHubClientResponse.updatedAt().isAfter(link.getLastUpdateTime())) {
            sendUpdates(link, "Содержимое ссылки было обновлено");
        }
    }

    private void checkStackOverflowUpdates(StackOverflowLinkResponse stackOverflowLinkResponse, Link link) {
        StackOverflowClientResponse stackOverflowClientResponse =
                stackOverflowClient
                        .fetchQuestion(stackOverflowLinkResponse.id())
                        .block();
        stackOverflowClientResponse.items()
                .forEach(response -> {
                    if (response.lastEditDate().isAfter(link.getLastUpdateTime()))
                        sendUpdates(link, "Содержимое ссылки было обновлено");
                });
    }

    private void sendUpdates(Link link, String description) {
        botClient.fetchUpdate(
                new LinkUpdateRequest(
                        link.getUrl(),
                        description,
                        chatRepository
                                .findChatsByUrl(link.getUrl()).get()
                                .stream().map(Chat::getId)
                                .toList()
                )
        );
    }
}

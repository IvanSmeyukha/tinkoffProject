package ru.tinkoff.edu.java.bot.service.command;

import java.net.URI;
import java.util.stream.Collectors;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksRequest;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private static final String NO_LINKS_REQUEST = "Список ссылок пуст";
    private final ScrapperClient client;

    @Override
    public String command() {
        return CommandInfo.LIST.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.LIST.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        ListLinksResponse listLinks = client.getLinks(new ListLinksRequest(update.message().chat().id())).block();
        return new SendMessage(
            update.message().chat().id(),
            listLinks.size() > 0
                ? listLinks.links().stream()
                .map(LinkResponse::url)
                .map(URI::toString)
                .collect(Collectors.joining("\n"))
                : NO_LINKS_REQUEST
        );

    }
}

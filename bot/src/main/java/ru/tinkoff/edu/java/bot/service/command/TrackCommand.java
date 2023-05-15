package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;
import ru.tinkoff.edu.java.linkparser.LinkParser;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private final ScrapperClient client;
    private static final String SUCCESS_MESSAGE = "Ссылка добавлена в отслеживаемые";
    private static final String WRONG_LINK_FORMAT_MESSAGE = "Неверный формат ссылки";
    private static final String WRONG_MESSAGE_FORMAT_MESSAGE = "Неверный формат команды";
    private static final Pattern PATTERN = Pattern.compile("^\\s*/track\\s+(?<url>\\S+)\\s*$");

    @Override
    public String command() {
        return CommandInfo.TRACK.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.TRACK.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        Matcher matcher = PATTERN.matcher(update.message().text());
        if (!matcher.find()) {
            return new SendMessage(update.message().chat().id(), WRONG_MESSAGE_FORMAT_MESSAGE);
        }
        String url = matcher.group("url");
        if (LinkParser.parseLink(url) == null) {
            return new SendMessage(update.message().chat().id(), WRONG_LINK_FORMAT_MESSAGE);
        }
        client.addLink(update.message().chat().id(), url).block();
        return new SendMessage(update.message().chat().id(), SUCCESS_MESSAGE);
    }
}

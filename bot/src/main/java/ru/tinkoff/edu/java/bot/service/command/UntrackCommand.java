package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;
import ru.tinkoff.edu.java.linkparser.LinkParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command{
    private final ScrapperClient client;
    private static final String SUCCESS_MESSAGE = "Ссылка удалена из отслеживаемых";
    private static final String WRONG_LINK_FORMAT_MESSAGE = "Неверный формат ссылки";
    private static final String WRONG_MESSAGE_FORMAT_MESSAGE = "Неверный формат команды";
    private static final Pattern pattern = Pattern.compile("^\\s*/untrack\\s+(?<url>\\S+)\\s*$");
    @Override
    public String command() {
        return CommandInfo.UNTRACK.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.UNTRACK.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        Matcher matcher = pattern.matcher(update.message().text());
        if (!matcher.find()) {
            return new SendMessage(update.message().chat().id(), WRONG_MESSAGE_FORMAT_MESSAGE);
        }
        String url = matcher.group("url");
        if (LinkParser.parseLink(url) == null) {
            return new SendMessage(update.message().chat().id(), WRONG_LINK_FORMAT_MESSAGE);
        }
        client.removeLink(update.message().chat().id(), url).block();
        return new SendMessage(update.message().chat().id(), SUCCESS_MESSAGE);
    }
}

package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClient;
import ru.tinkoff.edu.java.bot.dto.RegisterChatRequest;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient client;
    private static final String WELCOME_MESSAGE = "Привет!";

    @Override
    public String command() {
        return CommandInfo.START.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.START.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        client.registerChat(new RegisterChatRequest(update.message().chat().id())).block();
        return new SendMessage(update.message().chat().id(), WELCOME_MESSAGE);
    }
}

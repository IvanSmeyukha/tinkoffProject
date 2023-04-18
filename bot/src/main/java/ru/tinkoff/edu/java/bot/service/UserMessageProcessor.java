package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.command.Command;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMessageProcessor {
    private final List<Command> commands;
    private static final String UNSUPPORTED_COMMAND_MESSAGE = "Я не знаю такую команду. Для вывода списка всех комманд используйте /help";

    public SendMessage process(Update update) {
        return commands.stream()
                .filter(command -> command.supports(update))
                .findFirst()
                .map(command -> command.handle(update))
                .orElse(new SendMessage(update.message().chat().id(), UNSUPPORTED_COMMAND_MESSAGE));
    }
}
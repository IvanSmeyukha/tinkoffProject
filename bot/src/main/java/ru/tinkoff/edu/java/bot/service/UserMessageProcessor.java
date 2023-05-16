package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.command.Command;

@Service
@RequiredArgsConstructor
public class UserMessageProcessor {
    private final List<Command> commands;
    private final Counter counter = Metrics.counter("bot_messages_processed_total");
    private static final String UNSUPPORTED_COMMAND_MESSAGE =
        "Я не знаю такую команду. Для вывода списка всех комманд используйте /help";

    public SendMessage process(Update update) {
        counter.increment();
        return commands.stream()
            .filter(command -> command.supports(update))
            .findFirst()
            .map(command -> command.handle(update))
            .orElse(new SendMessage(update.message().chat().id(), UNSUPPORTED_COMMAND_MESSAGE));
    }
}

package ru.tinkoff.edu.java.bot.service.command;

import java.util.List;
import java.util.stream.Collectors;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;

@Component
@AllArgsConstructor
public class HelpCommand implements Command {
    List<Command> commands;

    @Override
    public String command() {
        return CommandInfo.HELP.getCommand();
    }

    @Override
    public String description() {
        return CommandInfo.HELP.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(),
            commands.stream()
                .map(command -> String.format("%s - %s", command.command(), command.description()))
                .collect(Collectors.joining("\n"))
        );
    }
}

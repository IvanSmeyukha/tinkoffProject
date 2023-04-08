package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;

@Component
public class StartCommand implements Command{
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
        return new SendMessage(update.message().chat().id(), "Команда /start");
    }
}

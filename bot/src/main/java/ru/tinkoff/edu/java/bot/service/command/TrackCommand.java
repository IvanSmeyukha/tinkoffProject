package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.enums.CommandInfo;

@Component
public class TrackCommand implements Command{
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
        return new SendMessage(update.message().chat().id(), "Команда /track");
    }
}

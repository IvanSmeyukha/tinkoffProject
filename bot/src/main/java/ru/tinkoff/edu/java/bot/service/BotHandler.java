package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.command.Command;

@Service
public class BotHandler implements Bot {
    TelegramBot bot;
    UserMessageProcessor userMessageProcessor;

    public BotHandler(@Value("#{@token}") String token, List<Command> commands) {
        bot = new TelegramBot(token);
        bot.setUpdatesListener(this);
        userMessageProcessor = new UserMessageProcessor(commands);
        bot.execute(new SetMyCommands(commands.stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new)));
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            execute(userMessageProcessor.process(update));
        }
        return CONFIRMED_UPDATES_ALL;
    }

}

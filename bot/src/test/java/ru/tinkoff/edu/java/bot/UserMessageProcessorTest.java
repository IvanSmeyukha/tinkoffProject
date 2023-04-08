package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClientImpl;
import ru.tinkoff.edu.java.bot.service.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.service.command.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMessageProcessorTest {
    @Mock
    private static ScrapperClientImpl client;
    private static UserMessageProcessor userMessageProcessor;

    @BeforeAll
    public static void beforeAll() {
        List<Command> commandList = new ArrayList<>(List.of(
                new StartCommand(),
                new ListCommand(client),
                new TrackCommand(),
                new UntrackCommand()));
        commandList.add(new HelpCommand(List.copyOf(commandList)));
        userMessageProcessor = new UserMessageProcessor(commandList);
    }


    @Test
    public void process_UserSendUnsupportedCommand_SpecialMessage(){
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        Chat mockChat = mock(Chat.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("someMessage");
        when(mockMessage.chat()).thenReturn(mockChat);
        when(mockChat.id()).thenReturn(1111L);

        assertEquals("Я не знаю такую команду. Для вывода списка всех комманд используйте /help",
                userMessageProcessor.process(mockUpdate).getParameters().get("text").toString());
    }
}

package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.webclient.ScrapperClientImpl;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksRequest;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.service.command.ListCommand;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCommandTest {
    @Mock
    private ScrapperClientImpl client;
    private ListCommand listCommand;
    private Update mockUpdate;
    private Message mockMessage ;
    private Chat mockChat;

    @BeforeEach
    public void beforeAll(){
        listCommand = new ListCommand(client);
        mockUpdate = mock(Update.class);
        mockMessage = mock(Message.class);
        mockChat = mock(Chat.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.chat()).thenReturn(mockChat);
        when(mockChat.id()).thenReturn(1111L);
    }

    @Test
    void handle_ClientReturnEmptyList_SpecialMessage(){
        ListLinksRequest listLinksRequest = new ListLinksRequest(1111L);
        ListLinksResponse listLinksResponse = new ListLinksResponse(Collections.emptyList(), 0);
        when(client.getLinks(listLinksRequest)).thenReturn(Mono.just(listLinksResponse));

        assertEquals("Список ссылок пуст", listCommand.handle(mockUpdate).getParameters().get("text").toString());
    }

    @Test
    void handle_ClientReturnNotEmptyList_LinksList(){
        List<LinkResponse> links = Arrays.asList(
                new LinkResponse(1111L, URI.create("http://example.com")),
                new LinkResponse(1111L, URI.create("http://example.org")));

        ListLinksRequest listLinksRequest = new ListLinksRequest(1111L);
        ListLinksResponse listLinksResponse = new ListLinksResponse(links, 2);
        when(client.getLinks(listLinksRequest)).thenReturn(Mono.just(listLinksResponse));

        assertEquals("http://example.com\nhttp://example.org",
                listCommand.handle(mockUpdate).getParameters().get("text").toString());
    }
}

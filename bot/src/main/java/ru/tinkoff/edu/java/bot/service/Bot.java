package ru.tinkoff.edu.java.bot.service;

import java.util.List;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

public interface Bot extends UpdatesListener {
    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    @Override
    int process(List<Update> updates);

}

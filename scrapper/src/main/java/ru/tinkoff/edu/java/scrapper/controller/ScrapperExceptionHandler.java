package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

import java.util.Arrays;

@RestControllerAdvice(
        basePackageClasses = {TgChatController.class, LinkController.class},
        basePackages = "ru.tinkoff.edu.java.scrapper.controller"
)
public class ScrapperExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiErrorResponse handleIllegalArgumentException(RuntimeException exception){
        return new ApiErrorResponse("Некорректные параметры запроса",
                HttpStatus.BAD_REQUEST.toString(),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList()
        );
    }
}

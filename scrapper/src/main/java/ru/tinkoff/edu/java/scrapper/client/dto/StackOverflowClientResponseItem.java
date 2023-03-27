package ru.tinkoff.edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowClientResponseItem(
        @JsonProperty("account_id")
        Long accountId,
        @JsonProperty("is_answered")
        Boolean is_answered,
        @JsonProperty("creation_date")
        OffsetDateTime creation_date,
        @JsonProperty("link")
        String link
) {
}

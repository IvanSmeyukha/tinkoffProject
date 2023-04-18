package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowClientResponseItem(
        @JsonProperty("account_id")
        Long accountId,
        @JsonProperty("is_answered")
        Boolean is_answered,
        @JsonProperty("creation_date")
        OffsetDateTime creation_date,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate,
        @JsonProperty("last_edit_date")
        OffsetDateTime lastEditDate,
        @JsonProperty("link")
        String link
) {
}

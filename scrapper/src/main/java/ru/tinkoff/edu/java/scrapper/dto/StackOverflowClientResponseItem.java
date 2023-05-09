package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowClientResponseItem(
    @JsonProperty("question_id")
    Long questionId,
    @JsonProperty("account_id")
    Long accountId,
    @JsonProperty("is_answered")
    Boolean isAnswered,
    @JsonProperty("creation_date")
    OffsetDateTime creationDate,
    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivityDate,
    @JsonProperty("last_edit_date")
    OffsetDateTime lastEditDate,
    @JsonProperty("link")
    String link
) {
}

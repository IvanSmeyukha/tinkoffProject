package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubClientResponse(
        @JsonProperty("id")
        Long id,
        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("created_at")
        OffsetDateTime createdAt,
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt,
        @JsonProperty("html_url")
        String html_url
) {
}

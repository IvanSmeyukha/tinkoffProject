package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowClientResponse(
        @JsonProperty("items")
        List<StackOverflowClientResponseItem> items
) {
}

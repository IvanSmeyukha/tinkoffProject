package ru.tinkoff.edu.java.scrapper.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowClientResponse(
    @JsonProperty("items")
    List<StackOverflowClientResponseItem> items
) {
}

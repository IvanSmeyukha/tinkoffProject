package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.*;

import java.net.URI;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private URI url;
    private OffsetDateTime lastCheckTime;
    private OffsetDateTime lastUpdateTime;
}

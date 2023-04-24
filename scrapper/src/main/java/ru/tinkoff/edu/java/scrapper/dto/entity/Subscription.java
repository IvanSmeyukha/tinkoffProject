package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private Long id;
    private Long linkId;
    private Long chatId;
}

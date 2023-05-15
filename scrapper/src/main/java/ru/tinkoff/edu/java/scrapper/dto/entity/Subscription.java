package ru.tinkoff.edu.java.scrapper.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

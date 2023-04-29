package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.enums.AccessType;

import java.time.Duration;

@Validated
@EnableScheduling
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType) {
    record Scheduler(Duration interval, Duration checkInterval){
    }

}
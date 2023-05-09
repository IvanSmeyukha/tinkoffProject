package ru.tinkoff.edu.java.scrapper.configuration;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import ru.tinkoff.edu.java.scrapper.enums.AccessType;

@Validated
@EnableScheduling
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull String queueName,
                                @NotNull String exchangeName,
                                @NotNull boolean useQueue
) {
    record Scheduler(Duration interval, Duration checkInterval) {
    }

}

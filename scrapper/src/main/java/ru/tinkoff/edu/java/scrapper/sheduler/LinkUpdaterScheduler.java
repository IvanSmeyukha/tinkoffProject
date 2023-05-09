package ru.tinkoff.edu.java.scrapper.sheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.service.updater.LinkUpdater;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;

    @Value("#{@schedulerCheckIntervalMs}")
    private Duration checkInterval;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        linkUpdater.update(checkInterval);
    }
}

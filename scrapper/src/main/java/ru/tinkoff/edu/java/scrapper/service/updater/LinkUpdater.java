package ru.tinkoff.edu.java.scrapper.service.updater;

import java.time.Duration;

public interface LinkUpdater {
    void update(Duration checkInterval);
}

package ru.tinkoff.edu.java.scrapper.sheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update(){
        log.info("LinkUpdaterScheduler");
    }
}

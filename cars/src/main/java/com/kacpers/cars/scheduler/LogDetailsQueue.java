package com.kacpers.cars.scheduler;

import com.kacpers.cars.service.DetailsQueueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class LogDetailsQueue {

    private final DetailsQueueService queueService;

    private static final Logger log = LoggerFactory.getLogger(LogDetailsQueue.class);

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void logDetailsQueue() {
        log.info("{} items in details queue", queueService.size());
    }

}

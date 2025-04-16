package com.kacpers.cars.scheduler;

import com.kacpers.cars.service.QueueProcessor;
import com.kacpers.cars.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LogQueueInfo {

    private final QueueService<?> queueService;

    private final QueueProcessor<?> queueProcessor;

    private final Logger log = LoggerFactory.getLogger(LogQueueInfo.class);

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void logQueueInfo() {
        log.info("{} items in queue, {} processed", queueService.queueSize(), queueProcessor.getCounter().get());
    }
}

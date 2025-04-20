package com.kacpers.cars.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import java.time.OffsetDateTime;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class QueueService {

    private final LinkedBlockingDeque<Pair<Long, OffsetDateTime>> queue = new LinkedBlockingDeque<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void enqueueMoveToEnd(Long queueItem) {
        if (!moveToEnd(queueItem)) {
            enqueue(queueItem);
        }
    }

    public boolean moveToEnd(Long queueItem) {
        if (queue.removeIf(pair -> pair.getFirst().equals(queueItem))) {
            return queue.offerLast(Pair.of(queueItem, OffsetDateTime.now()));
        }
        return false;
    }

    public void enqueue(Long queueItem) {
        queue.addLast(Pair.of(queueItem, OffsetDateTime.now()));
    }

    public Long poll() {
        Pair<Long, OffsetDateTime> poll = queue.pollFirst();
        return poll == null ? null : poll.getFirst();
    }

    public Pair<Long, OffsetDateTime> pollWithTimestamp() {
        return queue.pollFirst();
    }

    public Integer size() {
        return queue.size();
    }
}

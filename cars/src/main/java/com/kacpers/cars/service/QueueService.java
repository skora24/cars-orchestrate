package com.kacpers.cars.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class QueueService<T> {

    private final QueueProcessor<T> queueProcessor;
    
    private final LinkedBlockingDeque<T> queue = new LinkedBlockingDeque<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final Long REQUEUE_DELAY_MS = 500L;

    public void push(T t) {
        queue.add(t);
    }

    public T pop() {
        return queue.poll();
    }

    public Integer queueSize() {
        return this.queue.size();
    }

    public void startWatching() {
        new Thread(this::watchQueue).start();
    }

    public void startWatching(Set<T> initialValues) {
//        queue.addAll(initialValues);
        new Thread(this::watchQueue).start();
    }

    private void watchQueue() {
        log.info("Watcher thread running.");
        while (!Thread.currentThread().isInterrupted()) {
            T item = null;
            try {
                item = queue.takeFirst();
                log.debug("Took item from queue: {}", item);

                queueProcessor.processQueue(item);
            } catch (InterruptedException e) {
                log.info("Watcher thread interrupted while waiting for item. Re-interrupting and exiting.");
                Thread.currentThread().interrupt();
                if (item != null) {
                    requeueItem(item, "interruption during processing attempt");
                }
                break;
            } catch (TaskRejectedException e) {
//                log.warn("Task rejected for item: {}. Requeue to front.", item, e);
                if (item != null) {
                    requeueItem(item, "task rejection");
                    try {
                        TimeUnit.MILLISECONDS.sleep(REQUEUE_DELAY_MS);
                    } catch (InterruptedException ie) {
                        log.warn("Watcher thread interrupted during requeue delay. Re-interrupting and exiting.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    log.error("TaskRejectedException occurred but item was null. This shouldn't happen.");
                }
            } catch (Exception e) {
                log.error("Unexpected error in watcher loop for item: {}", item, e);
            }
        }
        log.info("Watcher thread finished.");
    }

    private void requeueItem(T item, String reason) {
//        log.info("Requeue item {} to front due to: {}", item, reason);
        boolean requeue = queue.offerFirst(item);
        if (!requeue) {
            log.error("CRITICAL: Failed to requeue item to front (queue might be full if bounded): {}", item);
        }
    }
}

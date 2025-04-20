package com.kacpers.cars;

import com.kacpers.cars.service.DetailsQueueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // To disable scheduler
class QueueServiceTest {

    @Autowired
    private DetailsQueueService queueService;

    @Test
    void testQueueMove() {
        for (int i = 0; i < 10; i++) {
            queueService.enqueueMoveToEnd((long) i);
        }

        queueService.enqueueMoveToEnd(1L);
        queueService.enqueueMoveToEnd(2L);

        List<Long> queueList = new ArrayList<>();
        Long polled;
        while ((polled = queueService.poll()) != null) {
            queueList.add(polled);
        }

        Assertions.assertEquals(10, queueList.size());
        Assertions.assertEquals(List.of(0L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L), queueList);
    }

}

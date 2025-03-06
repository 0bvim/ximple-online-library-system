package com.ximple.online.library.system.service;

import com.ximple.online.library.system.events.models.BookCreatedEvent;
import com.ximple.online.library.system.events.models.ReservationEvent;
import com.ximple.online.library.system.events.models.ReviewEvent;
import com.ximple.online.library.system.events.store.EventStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventReplayService {

    private final EventStoreService eventStoreService;
    private final RedisTemplate<String, Object> redisTemplate;

    public void rebuildAnalytics() {
        log.info("Starting analytics rebuild from events");

        // Clear existing analytics data
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();

        AtomicInteger bookCounter = new AtomicInteger(0);
        AtomicInteger reservationCounter = new AtomicInteger(0);
        AtomicInteger reviewCounter = new AtomicInteger(0);

        // Replay book events
        // Replay reservation events
        eventStoreService.replayAllEvents("reservation", ReservationEvent.class, event -> {
            log.debug("Replaying reservation event: {}", event.getEventId());
            redisTemplate.opsForValue().increment("stats:reservations:total");
            redisTemplate.opsForValue().increment("stats:reservations:book:" + event.getBookId());
            redisTemplate.opsForValue().increment("stats:reservations:user:" + event.getUserId());

            // Track reservations by date
            String reservationDate = event.getReservationDate().toString().substring(0, 10); // YYYY-MM-DD
            redisTemplate.opsForHash().increment("stats:reservations:by_date", reservationDate, 1);

            reservationCounter.incrementAndGet();
        });

        // Replay review events
        eventStoreService.replayAllEvents("review", ReviewEvent.class, event -> {
            log.debug("Replaying review event: {}", event.getEventId());
            redisTemplate.opsForValue().increment("stats:reviews:total");
            redisTemplate.opsForValue().increment("stats:reviews:book:" + event.getBookId());
            redisTemplate.opsForValue().increment("stats:reviews:user:" + event.getUserId());

            // Track average ratings
            String bookKey = "stats:reviews:avg_rating:" + event.getBookId();
            Double currentAvg = (Double) redisTemplate.opsForValue().get(bookKey);
            Double currentCount = (Double) redisTemplate.opsForValue().get(bookKey + ":count");

            if (currentAvg == null || currentCount == null) {
                redisTemplate.opsForValue().set(bookKey, (double) event.getRating());
                redisTemplate.opsForValue().set(bookKey + ":count", 1.0);
            } else {
                double newCount = currentCount + 1;
                double newAvg = ((currentAvg * currentCount) + event.getRating()) / newCount;
                redisTemplate.opsForValue().set(bookKey, newAvg);
                redisTemplate.opsForValue().set(bookKey + ":count", newCount);
            }

            // Track ratings distribution
            redisTemplate.opsForHash().increment("stats:reviews:rating_distribution",
                    String.valueOf(event.getRating()), 1);

            reviewCounter.incrementAndGet();
        });

        // Log rebuild completion
        log.info("Analytics rebuild completed: {} books, {} reservations, {} reviews processed",
                bookCounter.get(), reservationCounter.get(), reviewCounter.get());

        // Store rebuild timestamp
        redisTemplate.opsForValue().set("stats:last_rebuild", System.currentTimeMillis());
    }
}
package com.ximple.online.library.system.service;

import com.ximple.online.library.system.domain.ReservationStatus;
import com.ximple.online.library.system.events.models.BookCreatedEvent;
import com.ximple.online.library.system.events.models.BookUpdatedEvent;
import com.ximple.online.library.system.events.models.ReservationEvent;
import com.ximple.online.library.system.events.models.ReviewEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticsService {

    private final RedisTemplate<String, Object> redisTemplate;

    public AnalyticsService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void recordNewBook(BookCreatedEvent event) {
        log.info("Recording analytics for new book: {}", event.getBookId());
        redisTemplate.opsForValue().increment("stats:books:total");
        redisTemplate.opsForValue().increment("stats:books:genre:" + event.getGenre());
        redisTemplate.opsForHash().increment("stats:books:by_year",
                String.valueOf(event.getPublicationYear()), 1);
    }

    public void recordBookUpdate(BookUpdatedEvent event) {
        log.info("Recording analytics for book update: {}", event.getBookId());
        redisTemplate.opsForValue().increment("stats:books:updates");
    }

    public void recordNewReservation(ReservationEvent event) {
        log.info("Recording analytics for new reservation: {}", event.getReservationId());
        redisTemplate.opsForValue().increment("stats:reservations:total");
        redisTemplate.opsForValue().increment("stats:reservations:book:" + event.getBookId());
        redisTemplate.opsForValue().increment("stats:users:activity:" + event.getUserId());
    }

    public void recordReservationUpdate(ReservationEvent event) {
        log.info("Recording analytics for reservation update: {}", event.getReservationId());
        redisTemplate.opsForValue().increment("stats:reservations:updates");

        if (event.getStatus() == ReservationStatus.BORROWED) {
            redisTemplate.opsForValue().increment("stats:books:borrowed");
        } else if (event.getStatus() == ReservationStatus.RETURNED) {
            redisTemplate.opsForValue().increment("stats:books:returned");
        }
    }

    public void recordReservationCancellation(ReservationEvent event) {
        log.info("Recording analytics for reservation cancellation: {}", event.getReservationId());
        redisTemplate.opsForValue().increment("stats:reservations:cancelled");
    }

    public void recordNewReview(ReviewEvent event) {
        log.info("Recording analytics for new review: {}", event.getReviewId());
        redisTemplate.opsForValue().increment("stats:reviews:total");
        redisTemplate.opsForValue().increment("stats:reviews:book:" + event.getBookId());
        redisTemplate.opsForHash().increment("stats:reviews:ratings",
                String.valueOf(event.getRating()), 1);
    }

    public void recordReviewUpdate(ReviewEvent event) {
        log.info("Recording analytics for review update: {}", event.getReviewId());
        redisTemplate.opsForValue().increment("stats:reviews:updates");
    }

    public void recordReviewDeletion(ReviewEvent event) {
        log.info("Recording analytics for review deletion: {}", event.getReviewId());
        redisTemplate.opsForValue().increment("stats:reviews:deleted");
    }
}


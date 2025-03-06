package com.ximple.online.library.system.events.consumer;

import com.ximple.online.library.system.events.models.BookCreatedEvent;
import com.ximple.online.library.system.events.models.BookUpdatedEvent;
import com.ximple.online.library.system.events.models.ReservationEvent;
import com.ximple.online.library.system.events.models.ReviewEvent;
import com.ximple.online.library.system.service.AnalyticsService;
import com.ximple.online.library.system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryEventConsumer {

    private final AnalyticsService analyticsService;
    private final NotificationService notificationService;

    @KafkaListener(topics = "books-topic", groupId = "library-consumer-group")
    public void consumeBookEvents(
            @Payload Object event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Consumed book event: {} from partition: {}", event, partition);

        if (event instanceof BookCreatedEvent bookCreatedEvent) {
            log.info("Processing book created event: {}", bookCreatedEvent);
            analyticsService.recordNewBook(bookCreatedEvent);
        } else if (event instanceof BookUpdatedEvent bookUpdatedEvent) {
            log.info("Processing book updated event: {}", bookUpdatedEvent);
            analyticsService.recordBookUpdate(bookUpdatedEvent);
        }
    }

    @KafkaListener(topics = "reservations-topic", groupId = "library-consumer-group")
    public void consumeReservationEvents(
            @Payload ReservationEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Consumed reservation event: {} from partition: {}", event, partition);

        switch (event.getEventType()) {
            case "CREATED" -> {
                analyticsService.recordNewReservation(event);
                notificationService.sendReservationConfirmation(event);
            }
            case "UPDATED" -> {
                analyticsService.recordReservationUpdate(event);
                notificationService.sendReservationUpdateNotification(event);
            }
            case "CANCELLED" -> {
                analyticsService.recordReservationCancellation(event);
                notificationService.sendReservationCancellationNotification(event);
            }
            default -> log.warn("Unknown reservation event type: {}", event.getEventType());
        }
    }

    @KafkaListener(topics = "reviews-topic", groupId = "library-consumer-group")
    public void consumeReviewEvents(
            @Payload ReviewEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {

        log.info("Consumed review event: {} from partition: {}", event, partition);

        switch (event.getEventType()) {
            case "CREATED" -> {
                analyticsService.recordNewReview(event);
                notificationService.sendReviewNotification(event);
            }
            case "UPDATED" -> analyticsService.recordReviewUpdate(event);
            case "DELETED" -> analyticsService.recordReviewDeletion(event);
            default -> log.warn("Unknown review event type: {}", event.getEventType());
        }
    }
}
package com.ximple.online.library.system.events.producer;

import com.ximple.online.library.system.events.models.BookCreatedEvent;
import com.ximple.online.library.system.events.models.BookUpdatedEvent;
import com.ximple.online.library.system.events.models.ReservationEvent;
import com.ximple.online.library.system.events.models.ReviewEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CompletableFuture<SendResult<String, Object>> sendBookCreatedEvent(BookCreatedEvent event) {
        String key = event.getBookId().toString();
        log.info("Sending book created event: {}", event);
        return kafkaTemplate.send("books-topic", key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleFailure(key, event, ex);
                    } else {
                        handleSuccess(key, event, result);
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendBookUpdatedEvent(BookUpdatedEvent event) {
        String key = event.getBookId().toString();
        log.info("Sending book updated event: {}", event);
        return kafkaTemplate.send("books-topic", key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleFailure(key, event, ex);
                    } else {
                        handleSuccess(key, event, result);
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendReservationEvent(ReservationEvent event) {
        String key = event.getReservationId().toString();
        log.info("Sending reservation event: {}", event);
        return kafkaTemplate.send("reservations-topic", key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleFailure(key, event, ex);
                    } else {
                        handleSuccess(key, event, result);
                    }
                });
    }

    public CompletableFuture<SendResult<String, Object>> sendReviewEvent(ReviewEvent event) {
        String key = event.getReviewId().toString();
        log.info("Sending review event: {}", event);
        return kafkaTemplate.send("reviews-topic", key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        handleFailure(key, event, ex);
                    } else {
                        handleSuccess(key, event, result);
                    }
                });
    }

    private void handleFailure(String key, Object value, Throwable ex) {
        log.error("Error sending message with key: {}, value: {}", key, value, ex);
    }

    private void handleSuccess(String key, Object value, SendResult<String, Object> result) {
        log.info("Message sent successfully for key: {}, value: {}, partition: {}",
                key, value, result.getRecordMetadata().partition());
    }
}
package com.ximple.online.library.system.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class MetricsService {

    private final MeterRegistry meterRegistry;

    // Event counters
    private final Counter bookSearchCounter;
    private final Counter bookReservationCounter;
    private final Counter reviewSubmissionCounter;
    private final Counter eventProcessedCounter;

    // Performance timers
    private final Timer bookSearchTimer;
    private final Timer reservationProcessingTimer;
    private final Timer eventProcessingTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        // Initialize counters
        this.bookSearchCounter = Counter.builder("library.search.count")
                .description("Number of book searches performed")
                .register(meterRegistry);

        this.bookReservationCounter = Counter.builder("library.reservation.count")
                .description("Number of book reservations made")
                .register(meterRegistry);

        this.reviewSubmissionCounter = Counter.builder("library.review.count")
                .description("Number of book reviews submitted")
                .register(meterRegistry);

        this.eventProcessedCounter = Counter.builder("library.events.processed")
                .description("Number of events processed")
                .register(meterRegistry);

        // Initialize timers
        this.bookSearchTimer = Timer.builder("library.search.time")
                .description("Time taken to search for books")
                .register(meterRegistry);

        this.reservationProcessingTimer = Timer.builder("library.reservation.time")
                .description("Time taken to process a reservation")
                .register(meterRegistry);

        this.eventProcessingTimer = Timer.builder("library.event.processing.time")
                .description("Time taken to process an event")
                .register(meterRegistry);
    }

    public void incrementBookSearchCount() {
        bookSearchCounter.increment();
    }

    public void incrementReservationCount() {
        bookReservationCounter.increment();
    }

    public void incrementReviewCount() {
        reviewSubmissionCounter.increment();
    }

    public void incrementEventProcessedCount() {
        eventProcessedCounter.increment();
    }

    public void recordEventProcessingTime(long timeMs) {
        eventProcessingTimer.record(timeMs, TimeUnit.MILLISECONDS);
    }

    public <T> T measureSearchExecutionTime(Supplier<T> searchOperation) {
        return bookSearchTimer.record(searchOperation);
    }

    public <T> T measureReservationExecutionTime(Supplier<T> reservationOperation) {
        return reservationProcessingTimer.record(reservationOperation);
    }

    public <T> T measureEventProcessingTime(Supplier<T> eventOperation) {
        return eventProcessingTimer.record(eventOperation);
    }

    public void recordCustomMetric(String name, double value) {
        meterRegistry.gauge(name, value);
    }

    public void recordCustomTimer(String name, long timeMs) {
        Timer timer = Timer.builder(name)
                .register(meterRegistry);
        timer.record(timeMs, TimeUnit.MILLISECONDS);
    }
}

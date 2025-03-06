package com.ximple.online.library.system.events.models;

import java.time.LocalDateTime;
import java.util.UUID;

public sealed interface LibraryEvent permits BookCreatedEvent, BookUpdatedEvent, ReservationEvent, ReviewEvent {
    UUID getEventId();
    LocalDateTime getTimestamp();
    String getEventType();
}

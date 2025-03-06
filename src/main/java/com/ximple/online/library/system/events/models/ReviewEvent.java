package com.ximple.online.library.system.events.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ReviewEvent implements LibraryEvent {
    private UUID eventId;
    private LocalDateTime timestamp;
    private String eventType; // CREATED, UPDATED, DELETED
    private UUID reviewId;
    private UUID userId;
    private UUID bookId;
    private int rating;
    private String comment;
}

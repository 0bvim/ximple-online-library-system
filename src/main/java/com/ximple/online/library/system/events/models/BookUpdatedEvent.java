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
public final class BookUpdatedEvent implements LibraryEvent {
    private UUID eventId;
    private LocalDateTime timestamp;
    private String eventType;
    private UUID bookId;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;
}


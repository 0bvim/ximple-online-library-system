package com.ximple.online.library.system.events.models;

import com.ximple.online.library.system.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ReservationEvent implements LibraryEvent {
    private UUID eventId;
    private LocalDateTime timestamp;
    private String eventType; // CREATED, UPDATED, CANCELLED, etc.
    private UUID reservationId;
    private UUID userId;
    private UUID bookId;
    private ReservationStatus status;
    private LocalDateTime reservationDate;
    private LocalDate dueDate;
    private LocalDateTime returnDate;
}


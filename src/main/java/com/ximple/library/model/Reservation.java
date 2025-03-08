package com.ximple.library.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record Reservation(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        UUID id,
        @NotEmpty(message = "Book ID is required")
        UUID bookId,
        @NotEmpty(message = "User ID is required")
        UUID userId,
        @NotEmpty(message = "Reservation date is required")
        LocalDateTime reservationDate,
        @NotEmpty
        LocalDateTime dueDate,
        String status
) {
}

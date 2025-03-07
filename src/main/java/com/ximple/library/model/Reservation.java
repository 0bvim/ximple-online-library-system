package com.ximple.library.model;

import com.ximple.library.utils.OneOf;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record Reservation(
        @Id
        Long id,
        String bookId,
        String userId,
        @NotEmpty
        LocalDateTime reservationDate,
        @NotEmpty
        LocalDateTime dueDate,
        @OneOf(Status.class)
        Status status


) {
}

package com.ximple.library.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Book(
        @Id
        Integer id,
        @NotEmpty @NotNull
        String title,
        @NotEmpty @NotNull
        String author,
        @NotEmpty @NotNull
        String isbn,
        @NotEmpty @NotNull
        Integer year,
        @NotEmpty @NotNull
        String genre,
        Review reviews,
        Reservation reservation,
        LocalDateTime created_at,
        LocalDateTime updated_at,
        LocalDateTime deleted_at
) {
}

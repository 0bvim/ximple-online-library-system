package com.ximple.library.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record Review(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        UUID id,
        @NotEmpty(message = "Book ID is required")
        UUID bookId,
        @NotEmpty(message = "User ID is required")
        UUID userId,
        @Min(1)
        @Max(5)
        int rating,
        @Size(max = 200, message = "Comment should be less than 200 characters")
        String comment,
        LocalDateTime createdAt
) {}

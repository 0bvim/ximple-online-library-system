package com.ximple.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        UUID bookId,
        UUID userId,
        int rating,
        String comment,
        LocalDateTime createdAt
) {
}

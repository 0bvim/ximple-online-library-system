package com.ximple.online.library.system.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationRequest(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotNull(message = "Book ID is required")
        UUID bookId
) {}
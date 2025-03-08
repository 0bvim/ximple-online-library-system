package com.ximple.library.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDTO(
        UUID id,
        UUID bookId,
        UUID userId,
        LocalDateTime reservationDate
) {
}

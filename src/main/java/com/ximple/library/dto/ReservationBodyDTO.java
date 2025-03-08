package com.ximple.library.dto;

import java.util.UUID;

public record ReservationBodyDTO(
        UUID bookId,
        UUID userId
) {
}

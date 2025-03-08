package com.ximple.library.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public record User(
        @Id
        UUID id,
        @NotEmpty(message = "Username is required")
        String username,
        @NotEmpty(message = "E-mail is required")
        String email
) {
}

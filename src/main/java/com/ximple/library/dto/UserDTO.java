package com.ximple.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String username,
        String email
) {
}

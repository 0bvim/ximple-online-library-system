package com.ximple.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BookDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String title,
        String author,
        String isbn,
        String genre,
        Integer amount
) {}
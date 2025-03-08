package com.ximple.library.dto;

import java.util.UUID;

public record BookDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        String genre,
        Integer amount
) {}
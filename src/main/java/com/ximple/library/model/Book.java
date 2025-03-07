package com.ximple.library.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public record Book(
        @Id
        Long id,
        @NotEmpty @NotNull
        String title,
        @NotEmpty @NotNull
        String author,
        @NotEmpty @NotNull
        String isbn,
        @NotEmpty @NotNull
        Genre genre,
        Integer amount,
        boolean available
) {
}

package com.ximple.library.model;

import com.ximple.library.dto.BookDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
public final class Book {
    @Id
    private UUID id;
    @NotNull @Valid
    private String title;
    @NotNull @Valid
    private String author;
    @NotNull @Valid
    private String isbn;
    private String genre;
    @Max(20) @NotNull @Valid
    private Integer amount;

    public Book() { }

    public @NotEmpty @NotNull String title() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Book) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.author, that.author) &&
                Objects.equals(this.isbn, that.isbn) &&
                Objects.equals(this.genre, that.genre) &&
                Objects.equals(this.amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, genre, amount);
    }

    @Override
    public String toString() {
        return "Book[" +
                "id=" + id + ", " +
                "title=" + title + ", " +
                "author=" + author + ", " +
                "isbn=" + isbn + ", " +
                "genre=" + genre + ", " +
                "amount=" + amount + "]";
    }

    public BookDTO toDTO() {
        return new BookDTO(id, title, author, isbn, genre, amount);
    }

}

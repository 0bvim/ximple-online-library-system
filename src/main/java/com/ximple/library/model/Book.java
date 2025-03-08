/**
 * Represents a book in the library.
 */
package com.ximple.library.model;

import com.ximple.library.dto.BookDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.*;

/**
 * Represents a book entity with details such as title, author, ISBN, genre, and amount.
 */
@Setter
@Getter
@Entity
@AllArgsConstructor
public final class Book {
  /**
   * Unique identifier for the book.
   */
  @Id private UUID id;
  /**
   * Title of the book.
   */
  @NotNull @Valid private String title;
  /**
   * Author of the book.
   */
  @NotNull @Valid private String author;
  /**
   * ISBN of the book.
   */
  @NotNull @Valid private String isbn;
  /**
   * Genre of the book.
   */
  private String genre;
  /**
   * Amount of books available.
   */
  @Max(20) @NotNull @Valid private Integer amount;

  /**
   * Default constructor.
   */
  public Book() {}

  /**
   * Returns the title of the book.
   *
   * @return the title of the book
   */
  public @NotEmpty @NotNull String title() {
    return title;
  }

  /**
   * Checks if this book is equal to another object.
   *
   * @param obj the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Book) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.title, that.title)
        && Objects.equals(this.author, that.author) && Objects.equals(this.isbn, that.isbn)
        && Objects.equals(this.genre, that.genre) && Objects.equals(this.amount, that.amount);
  }

  /**
   * Returns the hash code of this book.
   *
   * @return the hash code of this book
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, isbn, genre, amount);
  }

  /**
   * Returns a string representation of this book.
   *
   * @return a string representation of this book
   */
  @Override
  public String toString() {
    return "Book["
        + "id=" + id + ", "
        + "title=" + title + ", "
        + "author=" + author + ", "
        + "isbn=" + isbn + ", "
        + "genre=" + genre + ", "
        + "amount=" + amount + "]";
  }

  /**
   * Converts this book to a BookDTO.
   *
   * @return the BookDTO representation of this book
   */
  public BookDTO toDTO() {
    return new BookDTO(id, title, author, isbn, genre, amount);
  }
}

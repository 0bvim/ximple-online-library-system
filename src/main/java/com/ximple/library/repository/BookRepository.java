/**
 * Repository interface for managing Book entities.
 */
package com.ximple.library.repository;

import com.ximple.library.model.Book;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Book entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
  /**
   * Decrements the available copies of a book by 1 if there are available copies.
   *
   * @param id the UUID of the book
   * @return the number of rows affected
   */
  @Modifying
  @Query("UPDATE Book b SET b.amount = b.amount - 1 WHERE b.id = :id AND b.amount > 0")
  int decrementAvailableCopies(@Param("id") UUID id);

  /**
   * Increments the available copies of a book by 1.
   *
   * @param id the UUID of the book
   */
  @Modifying
  @Query("UPDATE Book b SET b.amount = b.amount + 1 WHERE b.id = :id")
  void incrementAvailableCopies(@Param("id") UUID id);

  /**
   * Finds books by their title.
   *
   * @param title the title of the book
   * @return a list of books with the given title
   */
  List<Book> findByTitle(String title);
  /**
   * Finds books by their author.
   *
   * @param author the author of the book
   * @return a list of books by the given author
   */
  List<Book> findByAuthor(String author);
  /**
   * Finds books by their ISBN.
   *
   * @param isbn the ISBN of the book
   * @return a list of books with the given ISBN
   */
  List<Book> findByIsbn(String isbn);
  /**
   * Finds books by their genre.
   *
   * @param genre the genre of the book
   * @return a list of books with the given genre
   */
  List<Book> findByGenre(String genre);
}

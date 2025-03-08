package com.ximple.library.repository;

import com.ximple.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Modifying
    @Query("UPDATE Book b SET b.amount = b.amount - 1 WHERE b.id = :id AND b.amount > 0")
    int decrementAvailableCopies(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE Book b SET b.amount = b.amount + 1 WHERE b.id = :id")
    int incrementAvailableCopies(@Param("id") UUID id);

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByIsbn(String isbn);
    List<Book> findByGenre(String genre);
}

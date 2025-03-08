package com.ximple.library.repository;

import com.ximple.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByIsbn(String isbn);
    List<Book> findByGenre(String genre);
}

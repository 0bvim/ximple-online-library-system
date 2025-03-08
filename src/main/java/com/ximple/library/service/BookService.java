package com.ximple.library.service;

import com.ximple.library.dto.BookDTO;
import com.ximple.library.exception.BookNotFoundException;
import com.ximple.library.model.Book;
import com.ximple.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> findAll() {
        return (List<BookDTO>) bookRepository.findAll().stream()
                .map(Book::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(UUID id) {
        return bookRepository.findById(id)
                .map(Book::toDTO)
                .orElseThrow();
    }

    public List<BookDTO> findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .stream()
                .map(Book::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .stream()
                .map(Book::toDTO)
                .findFirst()
                .orElseThrow();
    }

    public BookDTO findByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .stream()
                .map(Book::toDTO)
                .findFirst()
                .orElseThrow();
    }

    public List<BookDTO> findByGenre(String genre) {
        List<BookDTO> books = bookRepository.findByGenre(genre.toUpperCase())
                .stream()
                .map(Book::toDTO)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            throw new BookNotFoundException();
        }

        return books;
    }

    public void addBook(BookDTO book) {

        if (!bookRepository.findByIsbn(book.isbn()).isEmpty()) {
            throw new IllegalArgumentException("Book with ISBN " + book.isbn() + " already exists");
        }

        Book newBook = new Book(
                book.id() != null ? book.id() : UUID.randomUUID(),
                book.title(),
                book.author(),
                book.isbn(),
                book.genre().toUpperCase(),
                book.amount()
        );

        bookRepository.save(newBook);
    }
}
package com.ximple.library.service;

import com.ximple.library.dto.BookDTO;
import com.ximple.library.exception.BookNotFoundException;
import com.ximple.library.model.Book;
import com.ximple.library.repository.BookRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/*
  Service class for managing books.
 */
public class BookService {
  private final BookRepository bookRepository;
  private static final Logger log = LoggerFactory.getLogger(BookService.class);

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    log.info("BookService initialized");
  }

  /**
   * Retrieves all books.
   *
   * @return a list of BookDTO objects
   */
  public List<BookDTO> findAll() {
    log.debug("Fetching all books");
    return bookRepository.findAll().stream().map(Book::toDTO).collect(Collectors.toList());
  }

  /**
   * Retrieves a book by its ID.
   *
   * @param id the UUID of the book
   * @return the BookDTO object
   * @throws BookNotFoundException if the book is not found
   */
  public BookDTO findById(UUID id) {
    log.debug("Searching for book with id: {}", id);
    return bookRepository.findById(id).map(Book::toDTO).orElseThrow(() -> {
      log.error("Book with id {} not found", id);
      return new BookNotFoundException();
    });
  }

  /**
   * Retrieves books by their title.
   *
   * @param title the title of the book
   * @return a list of BookDTO objects
   */
  public List<BookDTO> findByTitle(String title) {
    log.debug("Searching for books with title: {}", title);
    List<BookDTO> books =
        bookRepository.findByTitle(title).stream().map(Book::toDTO).collect(Collectors.toList());
    log.debug("Found {} books with title: {}", books.size(), title);
    return books;
  }

  /**
   * Retrieves a book by its ISBN.
   *
   * @param isbn the ISBN of the book
   * @return the BookDTO object
   * @throws BookNotFoundException if the book is not found
   */
  public BookDTO findByIsbn(String isbn) {
    log.debug("Searching for book with ISBN: {}", isbn);
    return bookRepository.findByIsbn(isbn).stream().map(Book::toDTO).findFirst().orElseThrow(() -> {
      log.error("Book not found with ISBN: {}", isbn);
      return new BookNotFoundException();
    });
  }

  /**
   * Retrieves a book by its author.
   *
   * @param author the author of the book
   * @return the BookDTO object
   * @throws BookNotFoundException if the book is not found
   */
  public BookDTO findByAuthor(String author) {
    log.debug("Searching for book by author: {}", author);
    return bookRepository.findByAuthor(author)
        .stream()
        .map(Book::toDTO)
        .findFirst()
        .orElseThrow(() -> {
          log.error("Book not found by author: {}", author);
          return new BookNotFoundException();
        });
  }

  /**
   * Retrieves books by their genre.
   *
   * @param genre the genre of the book
   * @return a list of BookDTO objects
   * @throws BookNotFoundException if no books are found in the genre
   */
  public List<BookDTO> findByGenre(String genre) {
    log.debug("Searching for books in genre: {}", genre);
    List<BookDTO> books = bookRepository.findByGenre(genre.toUpperCase())
                              .stream()
                              .map(Book::toDTO)
                              .collect(Collectors.toList());

    if (books.isEmpty()) {
      log.error("No books found in genre: {}", genre);
      throw new BookNotFoundException();
    }

    log.debug("Found {} books in genre: {}", books.size(), genre);
    return books;
  }

  /**
   * Adds a new book.
   *
   * @param book the BookDTO object
   * @throws IllegalArgumentException if a book with the same ISBN already exists
   */
  @Transactional
  public void addBook(BookDTO book) {
    log.debug("Attempting to add new book with ISBN: {}", book.isbn());

    if (!bookRepository.findByIsbn(book.isbn()).isEmpty()) {
      log.error("Cannot add book: ISBN {} already exists", book.isbn());
      throw new IllegalArgumentException("Book with ISBN " + book.isbn() + " already exists");
    }

    Book newBook = new Book(book.id() != null ? book.id() : UUID.randomUUID(), book.title(),
        book.author(), book.isbn(), book.genre().toUpperCase(), book.amount());

    bookRepository.save(newBook);
    log.info("Successfully added new book: {}", newBook.getTitle());
  }

  /**
   * Updates an existing book.
   *
   * @param book the BookDTO object
   * @throws BookNotFoundException if the book is not found
   */
  @Transactional
  public void updateBook(BookDTO book) {
    log.debug("Attempting to update book with id: {}", book.isbn());
    List<Book> existingBook = bookRepository.findByIsbn(book.isbn());
    if (existingBook.isEmpty()) {
      log.error("Cannot update: book not found with id: {}", book.id());
      throw new BookNotFoundException();
    }

    existingBook.getFirst().setTitle(book.title());
    existingBook.getFirst().setAuthor(book.author());
    existingBook.getFirst().setIsbn(book.isbn());
    existingBook.getFirst().setGenre(book.genre().toUpperCase());
    existingBook.getFirst().setAmount(book.amount());

    bookRepository.save(existingBook.getFirst());
    log.info("Successfully updated book: {}", existingBook.getFirst().getTitle());
  }

  /**
   * Deletes a book by its ISBN.
   *
   * @param isbn the ISBN of the book
   * @throws BookNotFoundException if the book is not found
   */
  @Transactional
  public void deleteBook(String isbn) {
    log.debug("Attempting to delete book with ISBN: {}", isbn);
    List<Book> books = bookRepository.findByIsbn(isbn);
    if (books.isEmpty()) {
      log.error("Cannot delete: book not found with ISBN: {}", isbn);
      throw new BookNotFoundException();
    }
    bookRepository.delete(books.getFirst());
    log.info("Successfully deleted book with ISBN: {}", isbn);
  }
}

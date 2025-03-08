/**
 * Controller for handling book-related operations.
 */
package com.ximple.library.controller;

import com.ximple.library.dto.BookDTO;
import com.ximple.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Operations pertaining to books")

/*
  Controller for managing books in the library.
 */
public class BookController {
  private final BookService bookService;

  /**
   * Constructs a new BookController with the specified BookService.
   *
   * @param bookService the service to manage book operations
   */
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Retrieves all books in the library.
   *
   * @return a ResponseEntity containing a list of all books
   */
  @GetMapping("")
  @Operation(summary = "Get all books", description = "Get all books in the library")
  public ResponseEntity<List<BookDTO>> findAll() {
    return ResponseEntity.ok(bookService.findAll());
  }

  /**
   * Retrieves a book by its unique identifier.
   *
   * @param id the unique identifier of the book
   * @return a ResponseEntity containing the book
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get book by ID", description = "Get a book by its unique identifier")
  public ResponseEntity<BookDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(bookService.findById(id));
  }

  /**
   * Retrieves books by their title.
   *
   * @param title the title of the book
   * @return a ResponseEntity containing a list of books with the specified title
   */
  @GetMapping("/title")
  @Operation(summary = "Get book by title", description = "Get a book by its title")
  public ResponseEntity<List<BookDTO>> findByTitle(@RequestParam String title) {
    return ResponseEntity.ok(bookService.findByTitle(title));
  }

  /**
   * Retrieves a book by its ISBN.
   *
   * @param isbn the ISBN of the book
   * @return a ResponseEntity containing the book with the specified ISBN
   */
  @GetMapping("/isbn")
  @Operation(summary = "Get book by ISBN", description = "Get a book by its ISBN")
  public ResponseEntity<BookDTO> findByIsbn(@RequestParam String isbn) {
    return ResponseEntity.ok(bookService.findByIsbn(isbn));
  }

  /**
   * Retrieves a book by its author.
   *
   * @param author the author of the book
   * @return a ResponseEntity containing the book with the specified author
   */
  @GetMapping("/author")
  @Operation(summary = "Get book by author", description = "Get a book by its author")
  public ResponseEntity<BookDTO> findByAuthor(@RequestParam String author) {
    return ResponseEntity.ok(bookService.findByAuthor(author));
  }

  /**
   * Retrieves books by their genre.
   *
   * @param genre the genre of the book
   * @return a ResponseEntity containing a list of books with the specified genre
   */
  @GetMapping("/genre")
  @Operation(summary = "Get book by genre", description = "Get a book by its genre")
  public ResponseEntity<List<BookDTO>> findByGenre(@RequestParam String genre) {
    return ResponseEntity.ok(bookService.findByGenre(genre));
  }

  /**
   * Adds a new book to the library.
   *
   * @param book the book to be added
   * @return a ResponseEntity containing a success message
   */
  @PostMapping("")
  @Operation(summary = "Add a book", description = "Add a new book to the library")
  public ResponseEntity<String> addBook(@Valid @RequestBody BookDTO book) {
    bookService.addBook(book);
    return ResponseEntity.ok("Successfully added book: " + book.title());
  }

  /**
   * Updates an existing book in the library.
   *
   * @param book the book to be updated
   * @return a ResponseEntity containing a success message
   */
  @PutMapping("")
  @Operation(summary = "Update a book", description = "Update an existing book in the library")
  public ResponseEntity<String> updateBook(@RequestBody BookDTO book) {
    bookService.updateBook(book);
    return ResponseEntity.ok("Successfully updated book: " + book.title());
  }

  /**
   * Deletes a book from the library by its ISBN.
   *
   * @param isbn the ISBN of the book to be deleted
   * @return a ResponseEntity containing a success message
   */
  @DeleteMapping("")
  @Operation(summary = "Delete a book", description = "Delete a book from the library")
  public ResponseEntity<String> deleteBook(@RequestParam String isbn) {
    bookService.deleteBook(isbn);
    return ResponseEntity.ok("Successfully deleted book with ISBN: " + isbn);
  }
}

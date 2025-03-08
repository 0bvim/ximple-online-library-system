package com.ximple.library.controller;

import com.ximple.library.dto.BookDTO;
import com.ximple.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Operations pertaining to books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    @Operation(summary = "Get all books", description = "Get all books in the library")
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Get a book by its unique identifier")
    public ResponseEntity<BookDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

   @GetMapping("/title")
   @Operation(summary = "Get book by title", description = "Get a book by its title")
    public ResponseEntity<List<BookDTO>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.findByTitle(title));
   }

    @GetMapping("/isbn")
    @Operation(summary = "Get book by ISBN", description = "Get a book by its ISBN")
    public ResponseEntity<BookDTO> findByIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }

    @GetMapping("/author")
    @Operation(summary = "Get book by author", description = "Get a book by its author")
    public ResponseEntity<BookDTO> findByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping("/genre")
    @Operation(summary = "Get book by genre", description = "Get a book by its genre")
    public ResponseEntity<List<BookDTO>> findByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(bookService.findByGenre(genre));
    }

    @PostMapping("")
    @Operation(summary = "Add a book", description = "Add a new book to the library")
    public ResponseEntity<String> addBook(@Valid @RequestBody BookDTO book) {
        bookService.addBook(book);
        return ResponseEntity.ok("Successfully added book: " + book.title());
    }

    @PutMapping("")
    @Operation(summary = "Update a book", description = "Update an existing book in the library")
    public ResponseEntity<String> updateBook(@RequestBody BookDTO book) {
        bookService.updateBook(book);
        return ResponseEntity.ok("Successfully updated book: " + book.title());
    }

    @DeleteMapping("")
    @Operation(summary = "Delete a book", description = "Delete a book from the library")
    public ResponseEntity<String> deleteBook(@RequestParam String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.ok("Successfully deleted book with ISBN: " + isbn);
    }
}

package com.ximple.library.controller;

import com.ximple.library.dto.BookDTO;
import com.ximple.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

   @GetMapping("/title")
    public ResponseEntity<List<BookDTO>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.findByTitle(title));
   }

    @GetMapping("/isbn")
    public ResponseEntity<BookDTO> findByIsbn(@RequestParam String isbn) {
        return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }

    @GetMapping("/author")
    public ResponseEntity<BookDTO> findByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping("/genre")
    public ResponseEntity<List<BookDTO>> findByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(bookService.findByGenre(genre));
    }


    @PostMapping("")
    public void addBook(@Valid @RequestBody BookDTO book) {
        bookService.addBook(book);
    }

//    @RequestMapping(method = RequestMethod.PUT)
//    public void updateBook(@RequestBody Book book) {
//        bookService.updateBook(book);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public void deleteBook(@PathVariable UUID id) {
//        bookService.deleteBook(id);
//    }

}

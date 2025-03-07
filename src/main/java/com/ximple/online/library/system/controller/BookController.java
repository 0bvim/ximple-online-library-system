package com.ximple.online.library.system.controller;

import com.ximple.online.library.system.domain.Book;
import com.ximple.online.library.system.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book search and management API")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Search books", description = "Search books by title, author, genre, etc.")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.searchBooks(title, author, genre, pageRequest);
        return ResponseEntity.ok(books);
    }
}


package com.ximple.online.library.system.controller;

import com.ximple.online.library.system.domain.Review;
import com.ximple.online.library.system.dto.ReviewRequest;
import com.ximple.online.library.system.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Book review API")
public class ReviewController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Submit a review", description = "Submit a new review for a book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "404", description = "Book or user not found"),
        @ApiResponse(responseCode = "409", description = "User has already reviewed this book"),
        @ApiResponse(responseCode = "422", description = "Invalid review data")
    })
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewRequest request) {
        Review review = bookService.createReview(
                request.userId(),
                request.bookId(),
                request.rating(),
                request.comment());
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get book reviews", description = "List reviews for a specific book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
        @ApiResponse(responseCode = "400", description = "Invalid book ID"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<List<Review>> getBookReviews(@RequestParam UUID bookId) {
        List<Review> reviews = bookService.getBookReviews(bookId);
        return ResponseEntity.ok(reviews);
    }
}

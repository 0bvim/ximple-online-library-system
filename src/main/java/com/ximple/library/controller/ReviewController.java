package com.ximple.library.controller;

import com.ximple.library.dto.ReviewDTO;
import com.ximple.library.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "Operations pertaining to reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("")
    @Operation(summary = "Get all reviews", description = "Get all reviews in the library")
    public ResponseEntity<List<ReviewDTO>> findAll() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    @PostMapping("")
    @Operation(summary = "Create a review", description = "Submit a review for a book")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }
}


package com.ximple.library.controller;
import com.ximple.library.dto.ReviewDTO;
import com.ximple.library.dto.ReviewUpdateDTO;
import com.ximple.library.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing reviews.
 */
@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "Operations pertaining to reviews")
public class ReviewController {
  private final ReviewService reviewService;

  /**
   * Constructs a new ReviewController.
   *
   * @param reviewService the review service
   */
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  /**
   * Retrieves all reviews or filters by book ID.
   *
   * @param bookId the book ID (optional)
   * @return a ResponseEntity containing a list of review data transfer objects
   */
  @GetMapping
  @Operation(summary = "Get all reviews or filter by book ID",
      description = "Get all reviews in the library or filter by book ID")
  public ResponseEntity<List<ReviewDTO>> getReviews(@RequestParam(required = false) UUID bookId) {
    if (bookId != null) {
      return ResponseEntity.ok(reviewService.findByBookId(bookId));
    }

    return ResponseEntity.ok(reviewService.findAll());
  }

  /**
   * Creates a new review.
   *
   * @param reviewDTO the review data transfer object
   * @return a ResponseEntity containing the created review data transfer object
   */
  @PostMapping("")
  @Operation(summary = "Create a review", description = "Submit a review for a book")
  public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
    return ResponseEntity.ok(reviewService.createReview(reviewDTO));
  }

  /**
   * Updates an existing review.
   *
   * @param reviewDTO the review update data transfer object
   * @return a ResponseEntity containing the updated review data transfer object
   */
  @PutMapping
  @Operation(summary = "Update a review", description = "Update a review for a book")
  public ResponseEntity<ReviewDTO> updateReview(@Valid @RequestBody ReviewUpdateDTO reviewDTO) {
    return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
  }

  /**
   * Deletes a review by ID.
   *
   * @param reviewId the review ID
   * @return a ResponseEntity with no content
   */
  @DeleteMapping
  @Operation(summary = "Delete a review", description = "Delete a review for a book")
  public ResponseEntity<Void> deleteReview(@RequestParam UUID reviewId) {
    reviewService.deleteReview(reviewId);
    return ResponseEntity.noContent().build();
  }
}

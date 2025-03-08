package com.ximple.library.repository;

import com.ximple.library.model.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Review entities.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

  /**
   * Finds reviews by the book ID.
   *
   * @param bookId the UUID of the book
   * @return a list of reviews for the given book ID
   */
  List<Review> findByBookId(UUID bookId);
}

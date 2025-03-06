package com.ximple.online.library.system.repository;

import com.ximple.online.library.system.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByBookId(UUID bookId);
    List<Review> findByUserId(UUID userId);
    List<Review> findByBookIdOrderByCreatedAtDesc(UUID bookId);
}

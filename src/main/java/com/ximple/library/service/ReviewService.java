package com.ximple.library.service;

import com.ximple.library.dto.ReviewDTO;
import com.ximple.library.dto.ReviewUpdateDTO;
import com.ximple.library.model.Book;
import com.ximple.library.model.Review;
import com.ximple.library.model.User;
import com.ximple.library.repository.BookRepository;
import com.ximple.library.repository.ReviewRepository;
import com.ximple.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        log.info("ReviewService instantiated");
    }

    public List<ReviewDTO> findAll() {
        log.debug("Fetching all reviews");
        return reviewRepository.findAll().stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        log.debug("Creating a new review");

        Optional<User> user = userRepository.findById(reviewDTO.userId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        Optional<Book> book = bookRepository.findById(reviewDTO.bookId());
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book does not exist");
        }

        Review review = new Review(
                UUID.randomUUID(),
                reviewDTO.bookId(),
                reviewDTO.userId(),
                reviewDTO.rating(),
                reviewDTO.comment(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        review = reviewRepository.save(review);
        return review.toDTO();
    }

    public List<ReviewDTO> findByBookId(UUID bookId) {
        log.debug("Fetching reviews for book ID: {}", bookId);
        return reviewRepository.findByBookId(bookId).stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO updateReview(@Valid ReviewUpdateDTO reviewDTO) {
        log.debug("Updating review with ID: {}", reviewDTO.id());

        Optional<Review> existingReview = reviewRepository.findById(reviewDTO.id());
        if (existingReview.isEmpty()) {
            throw new IllegalArgumentException("Review does not exist");
        }

        Review review = existingReview.get();
        review.setRating(reviewDTO.rating());
        review.setComment(reviewDTO.comment());
        review.setUpdatedAt(LocalDateTime.now());

        review = reviewRepository.save(review);
        return review.toDTO();
    }
}

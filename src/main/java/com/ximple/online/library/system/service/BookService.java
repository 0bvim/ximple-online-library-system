package com.ximple.online.library.system.service;

import com.ximple.online.library.system.domain.Book;
import com.ximple.online.library.system.domain.Reservation;
import com.ximple.online.library.system.domain.ReservationStatus;
import com.ximple.online.library.system.domain.Review;
import com.ximple.online.library.system.domain.User;
import com.ximple.online.library.system.events.models.ReservationEvent;
import com.ximple.online.library.system.events.models.ReviewEvent;
import com.ximple.online.library.system.events.producer.LibraryEventProducer;
import com.ximple.online.library.system.repository.BookRepository;
import com.ximple.online.library.system.repository.ReservationRepository;
import com.ximple.online.library.system.repository.ReviewRepository;
import com.ximple.online.library.system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final LibraryEventProducer eventProducer;

    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {
        Specification<Book> spec = Specification.where(null);

        if (title != null && !title.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        if (genre != null && !genre.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("genre")), "%" + genre.toLowerCase() + "%"));
        }

        return bookRepository.findAll(spec, pageable);
    }

    @Transactional
    public Reservation createReservation(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        if (book.getAvailableCopies() < 1) {
            throw new IllegalStateException("No copies of the book available for reservation");
        }

        // Update available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Create reservation
        Reservation reservation = Reservation.builder()
                .user(user)
                .book(book)
                .reservationDate(LocalDateTime.now())
                .dueDate(LocalDate.now().plusDays(14)) // 2 weeks loan period
                .status(ReservationStatus.RESERVED)
                .build();

        reservationRepository.save(reservation);

        // Publish event
        ReservationEvent event = ReservationEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .eventType("CREATED")
                .reservationId(reservation.getId())
                .userId(userId)
                .bookId(bookId)
                .status(ReservationStatus.RESERVED)
                .reservationDate(reservation.getReservationDate())
                .dueDate(reservation.getDueDate())
                .build();

        CompletableFuture.runAsync(() -> eventProducer.sendReservationEvent(event));

        return reservation;
    }

    @Transactional
    public Review createReview(UUID userId, UUID bookId, int rating, String comment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Create review
        Review review = Review.builder()
                .user(user)
                .book(book)
                .rating(rating)
                .comment(comment)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        // Publish event
        ReviewEvent event = ReviewEvent.builder()
                .eventId(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .eventType("CREATED")
                .reviewId(review.getId())
                .userId(userId)
                .bookId(bookId)
                .rating(rating)
                .comment(comment)
                .build();

        CompletableFuture.runAsync(() -> eventProducer.sendReviewEvent(event));

        return review;
    }

    public List<Review> getBookReviews(UUID bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
package com.ximple.library.service;

import com.ximple.library.dto.ReservationBodyDTO;
import com.ximple.library.dto.ReservationDTO;
import com.ximple.library.model.Reservation;
import com.ximple.library.repository.BookRepository;
import com.ximple.library.repository.ReservationRepository;
import com.ximple.library.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing reservations.
 */
@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  /**
   * Constructs a new ReservationService.
   *
   * @param reservationRepository the reservation repository
   * @param bookRepository the book repository
   * @param userRepository the user repository
   */
  public ReservationService(ReservationRepository reservationRepository,
      BookRepository bookRepository, UserRepository userRepository) {
    this.reservationRepository = reservationRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  /**
   * Reserves a book for a user.
   *
   * @param reservationDTO the reservation data transfer object
   * @return the reservation data transfer object
   */
  @Transactional
  public ReservationDTO reserveBook(ReservationBodyDTO reservationDTO) {
    if (!bookRepository.existsById(reservationDTO.bookId())) {
      throw new IllegalArgumentException("Book not found with ID: " + reservationDTO.bookId());
    }

    if (!userRepository.existsById(reservationDTO.userId())) {
      throw new IllegalArgumentException("User not found with ID: " + reservationDTO.userId());
    }

    int updatedRows = bookRepository.decrementAvailableCopies(reservationDTO.bookId());
    if (updatedRows == 0) {
      throw new IllegalArgumentException(
          "No available copies for book with ID: " + reservationDTO.bookId());
    }

    Reservation reservation = new Reservation(
        UUID.randomUUID(), reservationDTO.bookId(), reservationDTO.userId(), LocalDateTime.now());
    reservationRepository.save(reservation);
    return reservation.toDTO();
  }

  /**
   * Retrieves all reservations.
   *
   * @return a list of reservation data transfer objects
   */
  public List<ReservationDTO> findAll() {
    return reservationRepository.findAll()
        .stream()
        .map(Reservation::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * Deletes a reservation by ID.
   *
   * @param id the reservation ID
   */
  @Transactional
  public void deleteReservation(UUID id) {
    Reservation reservation = reservationRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Reservation not found with ID: " + id));

    bookRepository.incrementAvailableCopies(reservation.getBookId());
    reservationRepository.deleteById(id);
  }

  /**
   * Retrieves a reservation by ID.
   *
   * @param id the reservation ID
   * @return a list of reservation data transfer objects
   */
  public List<ReservationDTO> findById(UUID id) {
    return reservationRepository.findById(id).stream().map(Reservation::toDTO).toList();
  }

  /**
   * Retrieves reservations by book ID.
   *
   * @param bookId the book ID
   * @return a list of reservation data transfer objects
   */
  public List<ReservationDTO> findByBookId(UUID bookId) {
    return reservationRepository.findByBookId(bookId).stream().toList();
  }
}
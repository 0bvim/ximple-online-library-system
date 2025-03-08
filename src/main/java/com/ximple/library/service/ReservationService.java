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

@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public ReservationService(ReservationRepository reservationRepository,
      BookRepository bookRepository, UserRepository userRepository) {
    this.reservationRepository = reservationRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

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

  public List<ReservationDTO> findAll() {
    return reservationRepository.findAll()
        .stream()
        .map(Reservation::toDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteReservation(UUID id) {
    Reservation reservation = reservationRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("Reservation not found with ID: " + id));

    bookRepository.incrementAvailableCopies(reservation.getBookId());
    reservationRepository.deleteById(id);
  }

  public List<ReservationDTO> findById(UUID id) {
    return reservationRepository.findById(id).stream().map(Reservation::toDTO).toList();
  }

  public List<ReservationDTO> findByBookId(UUID bookId) {
    return reservationRepository.findByBookId(bookId).stream().toList();
  }
}

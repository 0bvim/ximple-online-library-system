package com.ximple.library.controller;

import com.ximple.library.dto.ReservationBodyDTO;
import com.ximple.library.dto.ReservationDTO;
import com.ximple.library.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation", description = "Operations pertaining to reservations")
public class ReservationController {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping
  @Operation(summary = "Get all reservations", description = "Get all reservations in the library")
  public ResponseEntity<List<ReservationDTO>> getReservation(
      @RequestParam(required = false) UUID id) {
    if (id != null) {
      return ResponseEntity.ok(reservationService.findById(id));
    }

    return ResponseEntity.ok(reservationService.findAll());
  }

  @GetMapping("/book/{bookId}")
  @Operation(
      summary = "Get all reservations for a book", description = "Get all reservations for a book")
  public ResponseEntity<List<ReservationDTO>>
  getReservationsByBookId(@PathVariable UUID bookId) {
    return ResponseEntity.ok(reservationService.findByBookId(bookId));
  }

  @PostMapping
  @Operation(summary = "Reserve a book", description = "Reserve a book for a user")
  public ResponseEntity<ReservationDTO> reserveBook(
      @RequestBody ReservationBodyDTO reservationDTO) {
    return ResponseEntity.ok(reservationService.reserveBook(reservationDTO));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a reservation", description = "Delete a reservation by id")
  public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
    reservationService.deleteReservation(id);
    return ResponseEntity.noContent().build();
  }
}

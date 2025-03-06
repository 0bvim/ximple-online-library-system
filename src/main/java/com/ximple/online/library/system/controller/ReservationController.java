package com.ximple.online.library.system.controller;

import com.ximple.online.library.system.domain.Reservation;
import com.ximple.online.library.system.dto.ReservationRequest;
import com.ximple.online.library.system.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Book reservation API")
public class ReservationController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Reserve a book", description = "Create a new reservation for a book")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = bookService.createReservation(request.userId(), request.bookId());
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }
}

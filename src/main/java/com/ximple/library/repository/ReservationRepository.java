package com.ximple.library.repository;

import com.ximple.library.dto.ReservationDTO;
import com.ximple.library.model.Reservation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Reservation entities.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

  /**
   * Finds reservations by the book ID.
   *
   * @param bookId the UUID of the book
   * @return a list of ReservationDTOs for the given book ID
   */
  List<ReservationDTO> findByBookId(UUID bookId);
}

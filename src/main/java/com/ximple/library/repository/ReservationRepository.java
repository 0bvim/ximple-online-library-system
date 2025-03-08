package com.ximple.library.repository;

import com.ximple.library.dto.ReservationDTO;
import com.ximple.library.model.Reservation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
  List<ReservationDTO> findByBookId(UUID bookId);
}

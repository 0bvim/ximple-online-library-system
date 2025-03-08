package com.ximple.library.repository;

import com.ximple.library.dto.ReservationDTO;
import com.ximple.library.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<ReservationDTO> findByBookId(UUID bookId);
}

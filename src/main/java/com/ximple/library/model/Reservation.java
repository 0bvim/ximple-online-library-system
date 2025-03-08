package com.ximple.library.model;

import com.ximple.library.dto.ReservationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
public final class Reservation {
    @Id
    private UUID id;
    @NotNull(message = "Book ID is required")
    private  UUID bookId;
    @NotNull(message = "User ID is required")
    private  UUID userId;
    private LocalDateTime reservationDate;

        public Reservation() {

        }

        @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Reservation) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.bookId, that.bookId) &&
                Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.reservationDate, that.reservationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, reservationDate);
    }

        @Override
        public String toString() {
                return "Reservation{" +
                        "id=" + id +
                        ", bookId=" + bookId +
                        ", userId=" + userId +
                        ", reservationDate=" + reservationDate +
                        '}';
        }

        public ReservationDTO toDTO() {
                return new ReservationDTO(id, bookId, userId, reservationDate);
        }
}

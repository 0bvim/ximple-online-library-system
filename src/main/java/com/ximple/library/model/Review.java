package com.ximple.library.model;

import com.ximple.library.dto.ReviewDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public final class Review {
    @Id
    private UUID id;
    @NotEmpty(message = "Book ID is required")
    private UUID bookId;
    @NotEmpty(message = "User ID is required")
    private UUID userId;
    @Min(1)
    @Max(5)
    int rating;
    @Size(max = 200, message = "Comment should be less than 200 characters")
    private String comment;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Review) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.bookId, that.bookId) &&
                Objects.equals(this.userId, that.userId) &&
                this.rating == that.rating &&
                Objects.equals(this.comment, that.comment) &&
                Objects.equals(this.createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, userId, rating, comment, createdAt);
    }

    @Override
    public String toString() {
        return "Review[" +
                "id=" + id + ", " +
                "bookId=" + bookId + ", " +
                "userId=" + userId + ", " +
                "rating=" + rating + ", " +
                "comment=" + comment + ", " +
                "createdAt=" + createdAt + ']';
    }

    public ReviewDTO toDTO() {
        return new ReviewDTO(id, bookId, userId, rating, comment, createdAt);
    }
}

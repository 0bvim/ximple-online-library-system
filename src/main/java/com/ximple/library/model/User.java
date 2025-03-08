package com.ximple.library.model;

import com.ximple.library.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public final class User {

    @Id
    private  UUID id;

    @NotEmpty(message = "Username is required")
    private   String username;

    @NotEmpty(message = "E-mail is required")
    private   String email;

        public static User fromDTO(UserDTO userDTO) {
                return new User(UUID.randomUUID(), userDTO.username(), userDTO.email());
        }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.username, that.username) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "User[" +
                "id=" + id + ", " +
                "username=" + username + ", " +
                "email=" + email + ']';
    }

    public UserDTO toDTO() {
        return new UserDTO(id, username, email);
    }
}

package com.ximple.library.repository;

import com.ximple.library.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link User} entities.
 * Provides methods to perform CRUD operations and custom queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  /**
   * Finds a user by their username.
   *
   * @param username the username of the user
   * @return the user with the specified username, or null if no user found
   */
  User findByUsername(String username);

  /**
   * Finds a user by their email.
   *
   * @param email the email of the user
   * @return the user with the specified email, or null if no user found
   */
  User findByEmail(String email);
}
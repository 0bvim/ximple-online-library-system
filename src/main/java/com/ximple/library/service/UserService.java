package com.ximple.library.service;

import com.ximple.library.dto.UserDTO;
import com.ximple.library.model.User;
import com.ximple.library.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
  private final UserRepository userRepository;
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  /**
   * Constructs a new UserService.
   *
   * @param userRepository the user repository
   */
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    log.info("UserService initialized");
  }

  /**
   * Registers a new user.
   *
   * @param userDTO the user data transfer object
   */
  public void registerUser(UserDTO userDTO) {
    log.debug("Registering user: {}", userDTO);
    User user = User.fromDTO(userDTO);
    userRepository.save(user);
    log.info("User registered: {}", user);
  }

  /**
   * Retrieves all users.
   *
   * @return a list of user data transfer objects
   */
  public List<UserDTO> findAll() {
    log.debug("Fetching all users");
    return userRepository.findAll().stream().map(User::toDTO).toList();
  }

  /**
   * Retrieves a user by ID.
   *
   * @param id the user ID
   * @return the user data transfer object
   */
  public UserDTO findById(UUID id) {
    log.debug("Fetching user by ID: {}", id);
    return userRepository.findById(id).map(User::toDTO).orElseThrow();
  }

  /**
   * Retrieves a user by username.
   *
   * @param username the username
   * @return the user data transfer object
   */
  public UserDTO findByUsername(String username) {
    log.debug("Fetching user by username: {}", username);
    return userRepository.findByUsername(username).toDTO();
  }

  /**
   * Retrieves a user by email.
   *
   * @param email the email
   * @return the user data transfer object
   */
  public UserDTO findByEmail(String email) {
    log.debug("Fetching user by email: {}", email);
    return userRepository.findByEmail(email).toDTO();
  }

  /**
   * Updates an existing user.
   *
   * @param id the user ID
   * @param userDTO the user data transfer object
   */
  public void updateUser(UUID id, UserDTO userDTO) {
    log.debug("Updating user with ID: {}", id);
    User user = userRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("User not found"));
    user.updateFromDTO(userDTO);
    userRepository.save(user);
    log.info("User updated: {}", user);
  }
}
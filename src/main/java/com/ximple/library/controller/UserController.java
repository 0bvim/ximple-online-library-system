package com.ximple.library.controller;

import com.ximple.library.dto.UserDTO;
import com.ximple.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing users.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Operations pertaining to users")
public class UserController {
  private final UserService userService;

  /**
   * Constructs a new UserController.
   *
   * @param userService the user service
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves all users.
   *
   * @return a ResponseEntity containing a list of user data transfer objects
   */
  @GetMapping("")
  @Operation(summary = "Get all users", description = "Get all users in the library")
  public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  /**
   * Retrieves a user by username.
   *
   * @param username the username of the user
   * @return a ResponseEntity containing the user data transfer object
   */
  @GetMapping("/username")
  @Operation(summary = "Get user by username", description = "Get a user by its username")
  public ResponseEntity<UserDTO> findByUsername(@RequestParam String username) {
    return ResponseEntity.ok(userService.findByUsername(username));
  }

  /**
   * Retrieves a user by email.
   *
   * @param email the email of the user
   * @return a ResponseEntity containing the user data transfer object
   */
  @GetMapping("/email")
  @Operation(summary = "Get user by email", description = "Get a user by its email")
  public ResponseEntity<UserDTO> findByEmail(@RequestParam String email) {
    return ResponseEntity.ok(userService.findByEmail(email));
  }

  /**
   * Retrieves a user by ID.
   *
   * @param id the unique identifier of the user
   * @return a ResponseEntity containing the user data transfer object
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID", description = "Get a user by its unique identifier")
  public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  /**
   * Registers a new user.
   *
   * @param userDTO the user data transfer object
   * @return a ResponseEntity with no content
   */
  @PostMapping("")
  @Operation(summary = "Register user", description = "Register a new user")
  public ResponseEntity<Void> registerUser(UserDTO userDTO) {
    userService.registerUser(userDTO);
    return ResponseEntity.ok().build();
  }

  /**
   * Updates an existing user.
   *
   * @param id the unique identifier of the user
   * @param userDTO the user data transfer object
   * @return a ResponseEntity with no content
   */
  @PutMapping("/{id}")
  @Operation(summary = "Update user", description = "Update an existing user")
  public ResponseEntity<Void> updateUser(@PathVariable UUID id, UserDTO userDTO) {
    userService.updateUser(id, userDTO);
    return ResponseEntity.ok().build();
  }
}
package com.ximple.library.service;

import com.ximple.library.dto.UserDTO;
import com.ximple.library.model.User;
import com.ximple.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("UserService initialized");
    }

    public void registerUser(UserDTO userDTO) {
        log.debug("Registering user: {}", userDTO);
        User user = User.fromDTO(userDTO);
        userRepository.save(user);
        log.info("User registered: {}", user);
    }

    public List<UserDTO> findAll() {
        log.debug("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(User::toDTO)
                .toList();
    }

    public UserDTO findById(UUID id) {
        log.debug("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(User::toDTO)
                .orElseThrow();
    }

    public UserDTO findByUsername(String username) {
        log.debug("Fetching user by username: {}", username);
        return userRepository.findByUsername(username)
                .toDTO();
    }

    public UserDTO findByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .toDTO();
    }

    public void updateUser(UUID id, UserDTO userDTO) {
        log.debug("Updating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.updateFromDTO(userDTO);
        userRepository.save(user);
        log.info("User updated: {}", user);
    }
}

package com.ximple.library.service;

import com.ximple.library.dto.UserDTO;
import com.ximple.library.model.User;
import com.ximple.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

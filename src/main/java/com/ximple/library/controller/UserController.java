package com.ximple.library.controller;

import com.ximple.library.dto.UserDTO;
import com.ximple.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Operations pertaining to users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @Operation(summary = "Get all users", description = "Get all users in the library")
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("")
    @Operation(summary = "Register user", description = "Register a new user")
    public ResponseEntity<Void> registerUser(UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok().build();
    }
}

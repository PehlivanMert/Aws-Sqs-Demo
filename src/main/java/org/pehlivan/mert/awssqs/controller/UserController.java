package org.pehlivan.mert.awssqs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pehlivan.mert.awssqs.dto.UserCreateRequest;
import org.pehlivan.mert.awssqs.dto.UserCreateResponse;
import org.pehlivan.mert.awssqs.dto.UserDto;
import org.pehlivan.mert.awssqs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> registerUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}
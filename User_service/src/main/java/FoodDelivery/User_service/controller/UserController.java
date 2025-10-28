package FoodDelivery.User_service.controller;

import FoodDelivery.User_service.dto.*;

import FoodDelivery.User_service.entity.User;

import FoodDelivery.User_service.exception.UserAlreadyExistsException;

import FoodDelivery.User_service.service.AuthenticationService;

import FoodDelivery.User_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;

import java.util.Optional;

@RestController

@RequestMapping("/users")

@CrossOrigin(origins = "*")

public class UserController {

    @Autowired

    private UserService userService;

    @Autowired

    private AuthenticationService authenticationService;

    @PostMapping("/register")

    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request,

                                          BindingResult bindingResult) {

        try {

            // Check for validation errors

            if (bindingResult.hasErrors()) {

                Map<String, String> errors = new HashMap<>();

                bindingResult.getFieldErrors().forEach(error ->

                        errors.put(error.getField(), error.getDefaultMessage())

                );

                return ResponseEntity.badRequest().body(Map.of(

                        "success", false,

                        "message", "Validation failed",

                        "errors", errors

                ));

            }

            AuthResponse response = userService.registerUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(

                    "success", true,

                    "message", "User registered successfully",

                    "data", response

            ));

        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.badRequest().body(Map.of(

                    "success", false,

                    "message", e.getMessage()

            ));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(

                    "success", false,

                    "message", "Registration failed: " + e.getMessage()

            ));

        }

    }

    @PostMapping("/login")

    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest request,

                                       BindingResult bindingResult) {

        try {

            // Check for validation errors

            if (bindingResult.hasErrors()) {

                Map<String, String> errors = new HashMap<>();

                bindingResult.getFieldErrors().forEach(error ->

                        errors.put(error.getField(), error.getDefaultMessage())

                );

                return ResponseEntity.badRequest().body(Map.of(

                        "success", false,

                        "message", "Validation failed",

                        "errors", errors

                ));

            }

            AuthResponse response = authenticationService.loginUser(request);

            return ResponseEntity.ok(Map.of(

                    "success", true,

                    "message", "Login successful",

                    "data", response

            ));

        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(

                    "success", false,

                    "message", "Invalid username or password"

            ));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(

                    "success", false,

                    "message", "Login failed: " + e.getMessage()

            ));

        }

    }

    @GetMapping("/profile")

    public ResponseEntity<?> getUserProfile() {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            Optional<User> userOptional = userService.findByUsername(username);

            if (userOptional.isPresent()) {

                User user = userOptional.get();

                UserResponse userResponse = new UserResponse(

                        user.getId(),

                        user.getUsername(),

                        user.getEmail(),

                        user.getFullName(),

                        user.getPhoneNumber(),

                        user.getAddress(),

                        user.getRole(),

                        user.getCreatedAt()

                );

                return ResponseEntity.ok(Map.of(

                        "success", true,

                        "message", "User profile retrieved successfully",

                        "data", userResponse

                ));

            } else {

                return ResponseEntity.notFound().build();

            }

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(

                    "success", false,

                    "message", "Failed to retrieve user profile: " + e.getMessage()

            ));

        }

    }

    @GetMapping("/health")

    public ResponseEntity<?> healthCheck() {

        return ResponseEntity.ok(Map.of(

                "success", true,

                "message", "User Service is running",

                "timestamp", System.currentTimeMillis()

        ));

    }

}

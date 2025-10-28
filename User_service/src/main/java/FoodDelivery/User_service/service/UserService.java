//package FoodDelivery.User_service.service;
//
//public class UserService {
//}
package FoodDelivery.User_service.service;

import FoodDelivery.User_service.dto.*;
import FoodDelivery.User_service.entity.User;
import FoodDelivery.User_service.exception.UserAlreadyExistsException;
import FoodDelivery.User_service.repository.UserRepository;
import FoodDelivery.User_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    public AuthResponse registerUser(UserRegistrationRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);
        // Create user response
        UserResponse userResponse = mapToUserResponse(savedUser);
        return new AuthResponse(token, userResponse);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}

package FoodDelivery.User_service.service;

import FoodDelivery.User_service.dto.AuthResponse;
import FoodDelivery.User_service.dto.UserLoginRequest;
import FoodDelivery.User_service.dto.UserResponse;
import FoodDelivery.User_service.entity.User;
import FoodDelivery.User_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    public AuthResponse loginUser(UserLoginRequest request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            // Update last login time
            user.setLastLoginAt(LocalDateTime.now());
            userService.updateUser(user);
            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);
            // Create user response
            UserResponse userResponse = userService.mapToUserResponse(user);
            return new AuthResponse(token, userResponse);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
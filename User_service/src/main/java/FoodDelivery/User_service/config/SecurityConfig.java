package FoodDelivery.User_service.config;

import FoodDelivery.User_service.security.JwtAuthenticationFilter;
import FoodDelivery.User_service.service.UserService;
import FoodDelivery.User_service.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        return new JwtAuthenticationFilter(jwtUtil, userService);
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/users/register", "/users/login", "/users/health").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // For H2 database console
                        .requestMatchers("/actuator/**").permitAll() // For Spring Boot Actuator
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Disable frame options for H2 console
        http.headers(headers -> headers.frameOptions().disable());
        return http.build();
    }
}
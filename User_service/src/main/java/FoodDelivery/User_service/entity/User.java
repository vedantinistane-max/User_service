//package FoodDelivery.User_service.entity;
//
//public class User {
//}
package FoodDelivery.User_service.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

import java.util.Collection;

import java.util.Collections;

@Entity

@Table(name = "users")

public class User implements UserDetails {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Username is required")

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")

    @Column(unique = true, nullable = false)

    private String username;

    @NotBlank(message = "Email is required")

    @Email(message = "Email should be valid")

    @Column(unique = true, nullable = false)

    private String email;

    @NotBlank(message = "Password is required")

    @Size(min = 6, message = "Password must be at least 6 characters")

    @Column(nullable = false)

    private String password;

    @NotBlank(message = "Full name is required")

    @Column(nullable = false)

    private String fullName;

    @Column

    private String phoneNumber;

    @Column

    private String address;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private Role role = Role.USER;

    @Column(nullable = false)

    private Boolean isEnabled = true;

    @Column(nullable = false)

    private Boolean isAccountNonExpired = true;

    @Column(nullable = false)

    private Boolean isAccountNonLocked = true;

    @Column(nullable = false)

    private Boolean isCredentialsNonExpired = true;

    @Column(nullable = false)

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column

    private LocalDateTime lastLoginAt;

    // Constructors

    public User() {}

    public User(String username, String email, String password, String fullName) {

        this.username = username;

        this.email = email;

        this.password = password;

        this.fullName = fullName;

    }

    // UserDetails interface methods

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));

    }

    @Override

    public String getPassword() {

        return password;

    }

    @Override

    public String getUsername() {

        return username;

    }

    @Override

    public boolean isAccountNonExpired() {

        return isAccountNonExpired;

    }

    @Override

    public boolean isAccountNonLocked() {

        return isAccountNonLocked;

    }

    @Override

    public boolean isCredentialsNonExpired() {

        return isCredentialsNonExpired;

    }

    @Override

    public boolean isEnabled() {

        return isEnabled;

    }

    // Getters and Setters

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getFullName() {

        return fullName;

    }

    public void setFullName(String fullName) {

        this.fullName = fullName;

    }

    public String getPhoneNumber() {

        return phoneNumber;

    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }

    public Role getRole() {

        return role;

    }

    public void setRole(Role role) {

        this.role = role;

    }

    public void setIsEnabled(Boolean isEnabled) {

        this.isEnabled = isEnabled;

    }

    public void setIsAccountNonExpired(Boolean isAccountNonExpired) {

        this.isAccountNonExpired = isAccountNonExpired;

    }

    public void setIsAccountNonLocked(Boolean isAccountNonLocked) {

        this.isAccountNonLocked = isAccountNonLocked;

    }

    public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {

        this.isCredentialsNonExpired = isCredentialsNonExpired;

    }

    public LocalDateTime getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;

    }

    public LocalDateTime getLastLoginAt() {

        return lastLoginAt;

    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {

        this.lastLoginAt = lastLoginAt;

    }

}

package com.artcom.community_platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users_list")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        if (userProfile != null) {
            userProfile.setUser(this); // maintain bidirectional consistency
        }
    }

    // Delegated Getters for UserProfile fields
    public String getWebsite() {
        return userProfile != null ? userProfile.getPortfolio() : null;
    }

    public String getBio() {
        return userProfile != null ? userProfile.getBio() : null;
    }

    public String getInstagram() {
        return userProfile != null ? userProfile.getInstagram() : null;
    }

    public UserProfile.UserRole getRole() {
        return userProfile != null ? userProfile.getRole() : null;
    }

    // Delegated Setters for UserProfile fields
    public void setWebsite(String website) {
        if (userProfile != null) userProfile.setPortfolio(website);
    }

    public void setBio(String bio) {
        if (userProfile != null) userProfile.setBio(bio);
    }

    public void setInstagram(String instagram) {
        if (userProfile != null) userProfile.setInstagram(instagram);
    }

    public void setRole(String role) {
        if (userProfile != null) {
            try {
                userProfile.setRole(UserProfile.UserRole.valueOf(role.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + role);
            }
        }
    }
}

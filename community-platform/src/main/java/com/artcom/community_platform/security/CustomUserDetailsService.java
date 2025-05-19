package com.artcom.community_platform.security;

import com.artcom.community_platform.entity.Admin;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.AdminRepository;
import com.artcom.community_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First try to find an admin by username
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // If not found as admin username, try as admin email
        admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }
        
        // If not an admin, try to find a regular user by email
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        if (user.getUserProfile() == null) {
            throw new UsernameNotFoundException("User profile not found for user: " + username);
        }

        // For Google-authenticated users, use a dummy password since they don't have one
        String password = user.getPassword() != null ? user.getPassword() : "{noop}google-auth";

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            password,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserProfile().getRole()))
        );
    }
} 
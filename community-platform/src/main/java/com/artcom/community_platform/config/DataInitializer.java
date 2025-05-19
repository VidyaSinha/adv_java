package com.artcom.community_platform.config;

import com.artcom.community_platform.entity.Admin;
import com.artcom.community_platform.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        // Create admin if not exists
        if (!adminRepository.findByUsername("admin@artcom.com").isPresent()) {
            Admin admin = new Admin();
            admin.setUsername("admin@artcom.com");
            admin.setEmail("admin@artcom.com");
            // Encode the password using BCrypt
            admin.setPassword(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
            System.out.println("Admin user created successfully!");
            System.out.println("Admin username: admin@artcom.com");
            System.out.println("Admin password: admin123");
        }
    }
} 
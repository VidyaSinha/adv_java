package com.artcom.community_platform.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testAdminCreation() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setUsername("admin@artcom.com");
        admin.setEmail("admin@artcom.com");
        admin.setPassword("encodedPassword");

        assertEquals(1L, admin.getId());
        assertEquals("admin@artcom.com", admin.getUsername());
        assertEquals("admin@artcom.com", admin.getEmail());
        assertEquals("encodedPassword", admin.getPassword());
    }

    @Test
    void testAdminEquality() {
        Admin admin1 = new Admin();
        admin1.setId(1L);
        admin1.setUsername("admin@artcom.com");
        admin1.setEmail("admin@artcom.com");
        admin1.setPassword("encodedPassword");

        Admin admin2 = new Admin();
        admin2.setId(1L);
        admin2.setUsername("admin@artcom.com");
        admin2.setEmail("admin@artcom.com");
        admin2.setPassword("encodedPassword");

        assertEquals(admin1.getId(), admin2.getId());
        assertEquals(admin1.getUsername(), admin2.getUsername());
        assertEquals(admin1.getEmail(), admin2.getEmail());
        assertEquals(admin1.getPassword(), admin2.getPassword());
    }
} 
package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AdminRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void whenFindByUsername_thenReturnAdmin() {
        // given
        Admin admin = new Admin();
        admin.setUsername("admin@artcom.com");
        admin.setEmail("admin@artcom.com");
        admin.setPassword("encodedPassword");
        entityManager.persist(admin);
        entityManager.flush();

        // when
        Optional<Admin> found = adminRepository.findByUsername("admin@artcom.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("admin@artcom.com");
        assertThat(found.get().getEmail()).isEqualTo("admin@artcom.com");
    }

    @Test
    void whenFindByEmail_thenReturnAdmin() {
        // given
        Admin admin = new Admin();
        admin.setUsername("admin@artcom.com");
        admin.setEmail("admin@artcom.com");
        admin.setPassword("encodedPassword");
        entityManager.persist(admin);
        entityManager.flush();

        // when
        Optional<Admin> found = adminRepository.findByEmail("admin@artcom.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("admin@artcom.com");
        assertThat(found.get().getUsername()).isEqualTo("admin@artcom.com");
    }

    @Test
    void whenFindByNonExistentUsername_thenReturnEmpty() {
        // when
        Optional<Admin> found = adminRepository.findByUsername("nonexistent@artcom.com");

        // then
        assertThat(found).isEmpty();
    }

    @Test
    void whenFindByNonExistentEmail_thenReturnEmpty() {
        // when
        Optional<Admin> found = adminRepository.findByEmail("nonexistent@artcom.com");

        // then
        assertThat(found).isEmpty();
    }
} 
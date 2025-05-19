package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Admin;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.repository.AdminRepository;
import com.artcom.community_platform.repository.UserRepository;
import com.artcom.community_platform.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;
    private User user;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        admin.setId(1L);
        admin.setUsername("admin@artcom.com");
        admin.setEmail("admin@artcom.com");
        admin.setPassword("encodedPassword");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
    }

    @Test
    void whenFindByUsername_thenReturnAdmin() {
        when(adminRepository.findByUsername("admin@artcom.com")).thenReturn(Optional.of(admin));

        Admin found = adminService.findByUsername("admin@artcom.com");

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("admin@artcom.com");
        verify(adminRepository).findByUsername("admin@artcom.com");
    }

    @Test
    void whenGetAllUsers_thenReturnUserList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = adminService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("testuser");
        verify(userRepository).findAll();
    }

    @Test
    void whenGetUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = adminService.getUserById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    void whenDeleteUser_thenUserIsDeleted() {
        doNothing().when(userRepository).deleteById(1L);

        adminService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void whenUpdateUser_thenUserIsUpdated() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = adminService.updateUser(user);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(1L);
        verify(userRepository).save(user);
    }
} 
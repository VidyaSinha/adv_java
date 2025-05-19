package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.repository.UserRepository;
import com.artcom.community_platform.repository.UserProfileRepository;
import com.artcom.community_platform.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserProfile userProfile;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setUser(user);
        userProfile.setUsername("testuser");
        userProfile.setRole(UserProfile.UserRole.USER);
    }

    @Test
    void whenCreateUser_thenReturnCreatedUser() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.createUser("test@example.com", "password", "testuser");

        assertThat(created).isNotNull();
        assertThat(created.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void whenCreateUserProfile_thenReturnCreatedProfile() {
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile created = userService.createUserProfile(user, "testuser", UserProfile.UserRole.USER);

        assertThat(created).isNotNull();
        assertThat(created.getUsername()).isEqualTo("testuser");
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void whenGetAllUsers_thenReturnUserList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo("test@example.com");
        verify(userRepository).findAll();
    }

    @Test
    void whenGetUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    void whenGetUserByEmail_thenReturnUser() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User found = userService.getUserByEmail("test@example.com");

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("test@example.com");
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void whenUpdateUser_thenReturnUpdatedUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser(user);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(1L);
        verify(userRepository).save(user);
    }

    @Test
    void whenGetUserProfileByUser_thenReturnProfile() {
        when(userProfileRepository.findByUser(user)).thenReturn(Optional.of(userProfile));

        UserProfile found = userService.getUserProfileByUser(user);

        assertThat(found).isNotNull();
        assertThat(found.getUser()).isEqualTo(user);
        verify(userProfileRepository).findByUser(user);
    }

    @Test
    void whenUpdateUserProfile_thenReturnUpdatedProfile() {
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfile);

        UserProfile updated = userService.updateUserProfile(userProfile);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(1L);
        verify(userProfileRepository).save(userProfile);
    }
} 
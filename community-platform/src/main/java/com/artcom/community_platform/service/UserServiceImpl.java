package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.entity.Artwork;
import com.artcom.community_platform.repository.UserRepository;
import com.artcom.community_platform.repository.UserProfileRepository;
import com.artcom.community_platform.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private ArtworkRepository artworkRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User createUser(String email, String password, String username) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setGoogleId(null); // For email registration
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserProfile createUserProfile(User user, String username, UserProfile.UserRole role) {
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setUsername(username);
        profile.setRole(role);
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile getUserProfileByUser(User user) {
        return userProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getEmail()));
    }

    @Override
    public UserProfile updateUserProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @Override
    public List<Artwork> getUserArtworks(User user) {
        return artworkRepository.findByArtist(user);
    }

    @Override
    public List<User> getUserFollowers(User user) {
        // TODO: Implement when follower functionality is added
        return new ArrayList<>();
    }

    @Override
    public List<User> getUserFollowing(User user) {

        return new ArrayList<>();
    }
}

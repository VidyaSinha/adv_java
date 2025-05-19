package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import com.artcom.community_platform.entity.Artwork;

import java.util.List;

public interface UserService {
    User createUser(String email, String password, String username);
    UserProfile createUserProfile(User user, String username, UserProfile.UserRole role);
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(User user);
    UserProfile getUserProfileByUser(User user);
    UserProfile updateUserProfile(UserProfile profile);
    List<Artwork> getUserArtworks(User user);
    List<User> getUserFollowers(User user);
    List<User> getUserFollowing(User user);
} 
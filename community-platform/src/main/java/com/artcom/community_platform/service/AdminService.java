package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Admin;
import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.Artwork;
import java.util.List;

public interface AdminService {
    Admin findByUsername(String username);
    List<User> getAllUsers();
    List<Artwork> getAllArtworks();
    User getUserById(Long id);
    Artwork getArtworkById(Long id);
    void deleteUser(Long id);
    void deleteArtwork(Long id);
    User updateUser(User user);
    Artwork updateArtwork(Artwork artwork);
} 
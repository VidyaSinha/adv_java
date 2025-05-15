package com.artcom.community_platform.service;

import java.util.List;
import com.artcom.community_platform.entity.User;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();

    User getUserById(Long id);
    User updateUser(User user);
}

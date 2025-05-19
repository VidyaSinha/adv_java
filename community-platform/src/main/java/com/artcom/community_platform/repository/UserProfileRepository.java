package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.User;
import com.artcom.community_platform.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserEmail(String email);
    Optional<UserProfile> findByUser(User user);
} 
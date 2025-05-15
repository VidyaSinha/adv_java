package com.artcom.community_platform.repository;

import com.artcom.community_platform.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}

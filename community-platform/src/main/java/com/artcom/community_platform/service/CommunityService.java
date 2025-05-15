package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Community;
import java.util.List;

public interface CommunityService {
    List<Community> getAllCommunities();
    Community getCommunityById(Long id);
    Community createCommunity(Community community); // <-- ADD THIS
    Community updateCommunity(Long id, Community updatedCommunity);
    void deleteCommunity(Long id);
}

package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Community;
import com.artcom.community_platform.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Override
    public Community getCommunityById(Long id) {
        Optional<Community> optional = communityRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community updateCommunity(Long id, Community updatedCommunity) {
        Community existing = communityRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updatedCommunity.getName());
            existing.setDescription(updatedCommunity.getDescription());
            // Add any other fields you need to update
            return communityRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }
}

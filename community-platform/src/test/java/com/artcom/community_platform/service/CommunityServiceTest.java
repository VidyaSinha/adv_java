package com.artcom.community_platform.service;

import com.artcom.community_platform.entity.Community;
import com.artcom.community_platform.repository.CommunityRepository;
//import com.artcom.community_platform.service.impl;
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
class CommunityServiceTest {

    @Mock
    private CommunityRepository communityRepository;

    @InjectMocks
    private CommunityServiceImpl communityService;

    private Community community;

    @BeforeEach
    void setUp() {
        community = new Community();
        community.setId(1L);
        community.setName("Test Community");
        community.setDescription("A test community");
    }

    @Test
    void whenGetAllCommunities_thenReturnCommunityList() {
        when(communityRepository.findAll()).thenReturn(Arrays.asList(community));

        List<Community> communities = communityService.getAllCommunities();

        assertThat(communities).hasSize(1);
        assertThat(communities.get(0).getName()).isEqualTo("Test Community");
        verify(communityRepository).findAll();
    }

    @Test
    void whenGetCommunityById_thenReturnCommunity() {
        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));

        Community found = communityService.getCommunityById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(communityRepository).findById(1L);
    }

    @Test
    void whenGetCommunityById_withNonExistentId_thenReturnNull() {
        when(communityRepository.findById(999L)).thenReturn(Optional.empty());

        Community found = communityService.getCommunityById(999L);

        assertThat(found).isNull();
        verify(communityRepository).findById(999L);
    }

    @Test
    void whenCreateCommunity_thenReturnCreatedCommunity() {
        when(communityRepository.save(any(Community.class))).thenReturn(community);

        Community created = communityService.createCommunity(community);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Test Community");
        verify(communityRepository).save(community);
    }

    @Test
    void whenUpdateCommunity_thenReturnUpdatedCommunity() {
        Community updatedCommunity = new Community();
        updatedCommunity.setName("Updated Community");
        updatedCommunity.setDescription("Updated description");

        when(communityRepository.findById(1L)).thenReturn(Optional.of(community));
        when(communityRepository.save(any(Community.class))).thenReturn(community);

        Community updated = communityService.updateCommunity(1L, updatedCommunity);

        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Updated Community");
        verify(communityRepository).findById(1L);
        verify(communityRepository).save(any(Community.class));
    }

    @Test
    void whenUpdateCommunity_withNonExistentId_thenReturnNull() {
        Community updatedCommunity = new Community();
        updatedCommunity.setName("Updated Community");

        when(communityRepository.findById(999L)).thenReturn(Optional.empty());

        Community updated = communityService.updateCommunity(999L, updatedCommunity);

        assertThat(updated).isNull();
        verify(communityRepository).findById(999L);
        verify(communityRepository, never()).save(any(Community.class));
    }

    @Test
    void whenDeleteCommunity_thenCommunityIsDeleted() {
        doNothing().when(communityRepository).deleteById(1L);

        communityService.deleteCommunity(1L);

        verify(communityRepository).deleteById(1L);
    }
} 
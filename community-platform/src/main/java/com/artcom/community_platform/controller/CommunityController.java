package com.artcom.community_platform.controller;

import com.artcom.community_platform.dto.CritiqueDTO;
import com.artcom.community_platform.entity.Community;
import com.artcom.community_platform.service.CommunityService;
import com.artcom.community_platform.service.CritiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CritiqueService critiqueService;

    @GetMapping
    public String showCommunityPage(Model model) {
        List<Community> communities = communityService.getAllCommunities();
        model.addAttribute("communities", communities);
        return "community"; // community.html
    }

    @GetMapping("/{communityId}/join")
    public String joinCommunity(@PathVariable String communityId, Model model) {
        // Add logic to handle community joining
        model.addAttribute("communityId", communityId);
        return "join_community"; // join_community.html
    }

    @PostMapping("/{communityId}/join")
    public String processJoinCommunity(@PathVariable String communityId) {
        // Add logic to process community joining
        return "redirect:/community";
    }

    @GetMapping("/art/{artId}/critique")
    public String showCritiqueForm(@PathVariable String artId, Model model) {
        model.addAttribute("artId", artId);
        model.addAttribute("critiqueDTO", new CritiqueDTO());
        return "art_critique"; // art_critique.html
    }

    @PostMapping("/art/{artId}/critique")
    public String submitCritique(@PathVariable String artId, @ModelAttribute CritiqueDTO critiqueDTO) throws Exception {
        // For now, using a hardcoded critic ID (1). In a real app, get this from the session
        critiqueDTO.setArtworkId(Long.parseLong(artId));
        critiqueService.createCritique(critiqueDTO, 1L);
        return "redirect:/community";
    }

    @GetMapping("/art/{artId}/collaborate")
    public String showCollaborationForm(@PathVariable String artId, Model model) {
        // Add logic to show collaboration form
        model.addAttribute("artId", artId);
        return "art_collaboration"; // art_collaboration.html
    }

    // Admin routes
    @GetMapping("/admin")
    public String listCommunities(Model model) {
        List<Community> communities = communityService.getAllCommunities();
        model.addAttribute("communities", communities);
        return "community_list"; // community_list.html
    }

    @GetMapping("/admin/new")
    public String showCreateForm(Model model) {
        model.addAttribute("community", new Community());
        return "create_community"; // create_community.html
    }

    @PostMapping("/admin")
    public String createCommunity(@ModelAttribute Community community) {
        communityService.createCommunity(community);
        return "redirect:/community/admin";
    }

    @GetMapping("/admin/{id}")
    public String viewCommunity(@PathVariable Long id, Model model) {
        Community community = communityService.getCommunityById(id);
        model.addAttribute("community", community);
        return "view_community"; // view_community.html
    }

    @GetMapping("/admin/{id}/edit")
    public String editCommunity(@PathVariable Long id, Model model) {
        Community community = communityService.getCommunityById(id);
        model.addAttribute("community", community);
        return "edit_community"; // edit_community.html
    }

    @PostMapping("/admin/{id}/edit")
    public String updateCommunity(@PathVariable Long id, @ModelAttribute Community updated) {
        communityService.updateCommunity(id, updated);
        return "redirect:/community/admin/" + id;
    }

    @PostMapping("/admin/{id}/delete")
    public String deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return "redirect:/community/admin";
    }
}

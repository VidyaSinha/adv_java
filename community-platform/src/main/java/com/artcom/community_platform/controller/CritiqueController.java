package com.artcom.community_platform.controller;

import com.artcom.community_platform.dto.CritiqueDTO;
import com.artcom.community_platform.entity.Critique;
import com.artcom.community_platform.service.CritiqueService;
import com.artcom.community_platform.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/art")
public class CritiqueController {

    @Autowired
    private CritiqueService critiqueService;

    @Autowired
    private ArtworkService artworkService;

    @GetMapping("/{artworkTitle}/critique")
    public String showCritiqueForm(@PathVariable String artworkTitle, Model model) {
        // Get artwork by title and add to model
        model.addAttribute("artwork", artworkService.getArtworkByTitle(artworkTitle));
        model.addAttribute("critiqueDTO", new CritiqueDTO());
        return "critique_form";
    }

    @PostMapping("/{artworkTitle}/critique")
    public String submitCritique(@PathVariable String artworkTitle, 
                               @ModelAttribute CritiqueDTO critiqueDTO,
                               @RequestParam Long criticId) {
        Critique critique = critiqueService.createCritique(critiqueDTO, criticId);
        return "redirect:/art/" + artworkTitle;
    }

    @GetMapping("/critiques")
    public String listCritiques(Model model) {
        model.addAttribute("critiques", critiqueService.getAllCritiques());
        return "critique_list";
    }

    @GetMapping("/critiques/{id}")
    public String viewCritique(@PathVariable Long id, Model model) {
        model.addAttribute("critique", critiqueService.getCritiqueById(id));
        return "view_critique";
    }
}

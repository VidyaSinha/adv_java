package com.artcom.community_platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // In the future, you can fetch data here and pass it to the view
        model.addAttribute("message", "Welcome to ArtistCom!");
        return "index"; // This corresponds to src/main/resources/templates/index.html
    }
}

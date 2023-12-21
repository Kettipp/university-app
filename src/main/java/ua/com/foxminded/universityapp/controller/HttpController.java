package ua.com.foxminded.universityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpController {
    @GetMapping("/")
    public String schedulePage(Model model) {
        return "schedule";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("name", "University Schedule");
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin";
    }
}

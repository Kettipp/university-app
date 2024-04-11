package ua.com.foxminded.universityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("name", "University Schedule");
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

}

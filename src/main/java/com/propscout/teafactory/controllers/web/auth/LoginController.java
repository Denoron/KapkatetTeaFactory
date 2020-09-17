package com.propscout.teafactory.controllers.web.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("login")
public class LoginController {

    @Value("${app.title}")
    private String app;

    @GetMapping
    public String showForm(Model model, Principal principal) {

        //If the user is already authenticated redirect to the home page
        if (principal != null) return "redirect:/";

        //Set the view attributes and return the view name
        model.addAttribute("app", app);
        model.addAttribute("title", "Login");

        return "login";
    }
}

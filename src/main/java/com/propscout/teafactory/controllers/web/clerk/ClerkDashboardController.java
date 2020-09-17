package com.propscout.teafactory.controllers.web.clerk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("clerk/dashboard")
public class ClerkDashboardController {

    @Value("${app.title}")
    private String app;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("app", app);
        model.addAttribute("title", "Clerk Dashboard");

        return "clerk/dashboard";
    }
}

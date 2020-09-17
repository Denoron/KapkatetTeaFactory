package com.propscout.teafactory.controllers.web.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/dashboard")
public class AdminDashboardController {

    @Value("${app.title}")
    private String app;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("app", app);

        return "admin/dashboard";
    }
}

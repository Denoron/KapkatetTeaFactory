package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.Settings;
import com.propscout.teafactory.services.SettingsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("admin/settings")
public class SettingsController {

    @Value("${app.title}")
    private String app;

    private SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    public String show(Model model) {

        Optional<Settings> optionalSettings = settingsService.getSettingsById(1);

        model.addAttribute("app", app);
        model.addAttribute("title", "Application Settings");

        if (optionalSettings.isEmpty()) {
            model.addAttribute("settings", new Settings());
        }

        optionalSettings.ifPresent(settings -> model.addAttribute("settings", settings));

        return "admin/settings/show";
    }

    @PostMapping
    public String store(Settings settings) {

        settingsService.updateSettings(settings);

        return "redirect:/admin/settings";
    }


    @GetMapping("edit")
    public String edit(Model model) {

        Optional<Settings> optionalSettings = settingsService.getSettingsById(1);

        model.addAttribute("app", app);
        model.addAttribute("title", "Application Settings");

        if (optionalSettings.isEmpty()) {
            model.addAttribute("settings", new Settings());
        }

        optionalSettings.ifPresent(settings -> model.addAttribute("settings", settings));

        return "admin/settings/edit";
    }

}

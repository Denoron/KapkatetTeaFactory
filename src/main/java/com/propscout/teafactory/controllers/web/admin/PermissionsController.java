package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.Permission;
import com.propscout.teafactory.services.PermissionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/permissions")
public class PermissionsController {

    @Value("${app.title}")
    private String app;

    private Logger logger = LoggerFactory.getLogger(PermissionsController.class);

    private PermissionsService permissionsService;

    public PermissionsController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping
    public String index(Model model) {

        List<Permission> permissions = permissionsService.getAll();
        logger.info(permissions.toString());
        model.addAttribute("title", "Admin Permissions");
        model.addAttribute("app", app);
        model.addAttribute("permissions", permissions);

        return "admin/permissions/index";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("title", "Admin Add Permission");
        model.addAttribute("app", app);

        return "admin/permissions/create";
    }

    @PostMapping
    public String store(@ModelAttribute Permission permission, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            logger.info(permission.toString());

            if (permissionsService.add(permission)) {
                return "redirect:/admin/permissions";
            }
        }

        return "redirect:/admin/permissions/create";
    }
}

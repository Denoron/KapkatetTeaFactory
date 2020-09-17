package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.Permission;
import com.propscout.teafactory.models.entities.Role;
import com.propscout.teafactory.services.RolesService;
import com.propscout.teafactory.services.PermissionsService;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("admin/roles")
public class RolesController {

    //Logger
    private Logger logger = LoggerFactory.getLogger(RolesController.class);

    //Service Instances
    private RolesService rolesService;
    private PermissionsService permissionsService;

    //Get app title from the properties files
    @Value("${app.title}")
    private String app;

    @Autowired
    public RolesController(
            RolesService rolesService,
            PermissionsService permissionsService
    ) {
        this.rolesService = rolesService;
        this.permissionsService = permissionsService;
    }

    @GetMapping
    public String index(Model model) {

        List<Role> roles = rolesService.getAllRoles();

        logger.info(roles.toString());

        model.addAttribute("title", "Admin Roles");
        model.addAttribute("roles", roles);
        model.addAttribute("app", app);

        return "admin/roles/index";
    }

    @GetMapping("create")
    public String create(Model model) {

        model.addAttribute("title", "Admin Create Role");
        model.addAttribute("app", app);

        return "admin/roles/create";
    }

    @PostMapping
    public String store(
            @ModelAttribute final Role newRole,
            final BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {

            //For debugging
            logger.info(newRole.toString());

            if (rolesService.add(newRole)) {
                return "redirect:/admin/roles";
            }

        }

        return "redirect:/admin/roles/create";
    }

    @GetMapping("{roleId}/edit")
    public String edit(
            @PathVariable("roleId") Integer roleId,
            Model model
    ) {

        Optional<Role> optionalRole = rolesService.getRoleById(roleId);

        if (optionalRole.isPresent()) {

            List<Permission> permissions = permissionsService.getAll();

            model.addAttribute("title", "Edit Role");
            model.addAttribute("app", app);
            model.addAttribute("permissions", permissions);
            model.addAttribute("role", optionalRole.get());

            return "admin/roles/edit";
        }

        return "redirect:/admin/roles";

    }

    @PostMapping("{roleId}/update")
    public String update(
            @PathVariable("roleId") Integer roleId,
            Role role
    ) {
        //Set the Id
        role.setId(roleId);

        //Log the role with the id set
        logger.info(role.toString());

        //Try saving the changes and get the saved role back
        Optional<Role> optionalRole = rolesService.updateRoleById(role);

        //Check whether the role has been updated and redirect appropriately
        if (optionalRole.isPresent()) {
            return String.format(Locale.ENGLISH, "redirect:/admin/roles/%d", roleId);
        }

        return String.format(Locale.ENGLISH, "redirect:/admin/roles/%d/edit", roleId);

    }

    @GetMapping("{roleId}")
    public String show(
            @PathVariable("roleId") Integer roleId,
            Model model
    ) throws Exception {
        //Get the role from the database
        Optional<Role> optionalRole = rolesService.getRoleById(roleId);

        optionalRole.orElseThrow(() -> new Exception("Role not found exception"));

        model.addAttribute("title", "Admin Role");
        model.addAttribute("app", app);
        model.addAttribute("role", optionalRole.get());

        return "admin/roles/show";
    }
}

package com.propscout.teafactory.controllers.web.admin;

import com.propscout.teafactory.models.entities.Location;
import com.propscout.teafactory.services.LocationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/locations")
public class LocationsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${app.title}")
    private String app;

    private final LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping
    public String index(Model model) {

        List<Location> locations = locationsService.getAllLocations();

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Locations");
        model.addAttribute("locations", locations);

        logger.info(locations.toString());

        return "admin/locations/index";
    }

    @GetMapping("create")
    public String create(Model model) {

        model.addAttribute("app", app);
        model.addAttribute("title", "Admin Create Location");
        model.addAttribute("location", new Location());

        return "admin/locations/create";
    }

    @PostMapping
    public String store(@ModelAttribute("location") Location location) {

        logger.info(location.toString());

        if (locationsService.addLocation(location)) {
            return "redirect:/admin/locations";
        }

        return "redirect:/admin/locations/create";
    }

    @GetMapping("{locationId}")
    public String show(
            @PathVariable("locationId") Integer locationId,
            Model model
    ) {
        final Optional<Location> optionalLocation = locationsService.getLocationById(locationId);

        if (optionalLocation.isEmpty()) {
            return "redirect:/admin/locations";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", optionalLocation.get().getName());
        model.addAttribute("location", optionalLocation.get());

        return "admin/locations/show";
    }

    @GetMapping("{locationId}/edit")
    public String edit(
            @PathVariable("locationId") Integer locationId,
            Model model
    ) {
        final Optional<Location> optionalLocation = locationsService.getLocationById(locationId);

        if (optionalLocation.isEmpty()) {
            return "redirect:/admin/locations";
        }

        model.addAttribute("app", app);
        model.addAttribute("title", "Edit" + optionalLocation.get().getName());
        model.addAttribute("location", optionalLocation.get());

        return "admin/locations/edit";
    }

    @PostMapping("{locationId}")
    public String update(
            @PathVariable("locationId") Integer locationId,
            @ModelAttribute("location") Location location
    ) {

        location.setId(locationId);

        if (!locationsService.updateLocationById(location)) {
            return "redirect:/admin/locations/" + locationId + "/edit";
        }

        return "redirect:/admin/locations/" + locationId;
    }

    @PostMapping("{locationId}/delete")
    public String delete(@PathVariable("locationId") Integer locationId) {

        locationsService.deleteLocationById(locationId);

        return "redirect:/admin/locations";
    }
}

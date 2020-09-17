package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Location;
import com.propscout.teafactory.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationsService {

    private LocationRepository locationRepository;

    public LocationsService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {

        final List<Location> locations = new ArrayList<>();

        locationRepository.findAll().forEach(locations::add);

        return locations;
    }

    public Optional<Location> getLocationById(Integer id) {
        return locationRepository.findById(id);
    }

    public boolean addLocation(Location location) {

        Optional<Location> optionalLocation = locationRepository.findByName(location.getName());

        if (optionalLocation.isPresent()) {
            return false;
        }

        locationRepository.save(location);

        return true;

    }

    public boolean updateLocationById(Location location) {

        if (locationRepository.existsById(location.getId())) {
            locationRepository.save(location);

            return true;
        }

        return false;
    }

    public boolean deleteLocationById(Integer id) {

        Optional<Location> optionalLocation = locationRepository.findById(id);

        if (optionalLocation.isEmpty()) {
            return false;
        }
        locationRepository.delete(optionalLocation.get());

        return true;
    }
}

package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    Optional<Location> findByName(String name);
}

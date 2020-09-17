package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Center;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CenterRepository extends CrudRepository<Center, Integer> {

    Optional<Center> findByName(String name);
}

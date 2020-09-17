package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByTitle(String title);
}

package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PermissionRepository extends CrudRepository<Permission, Integer> {

    Optional<Permission> findByTitle(String title);
}

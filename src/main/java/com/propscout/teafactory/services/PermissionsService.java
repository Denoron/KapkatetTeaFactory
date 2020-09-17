package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Permission;
import com.propscout.teafactory.repositories.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionsService {

    private Logger logger = LoggerFactory.getLogger(PermissionsService.class);

    private PermissionRepository permissionRepository;

    public PermissionsService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAll() {
        List<Permission> permissions = new ArrayList<>();

        permissionRepository.findAll().forEach(permissions::add);

        return permissions;
    }

    public boolean add(Permission permission) {
        Optional<Permission> permissionOptional = permissionRepository.findByTitle(permission.getTitle());

        if (permissionOptional.isPresent()) {
            logger.error("Permission title already exists: " + permission.getTitle());
            return false;
        }

        permissionRepository.save(permission);

        return true;
    }
}

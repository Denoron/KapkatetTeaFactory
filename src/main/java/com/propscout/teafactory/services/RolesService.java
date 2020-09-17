package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Role;
import com.propscout.teafactory.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    private RoleRepository roleRepository;

    public RolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean add(Role role) {

        final Optional<Role> optionalRole = roleRepository.findByTitle(role.getTitle());

        //Checking the role already exist
        if (optionalRole.isPresent()) {
            return false;
        }

        //Persisting the role
        roleRepository.save(role);

        return true;

    }

    public List<Role> getAllRoles() {

        List<Role> roles = new ArrayList<>();

        roleRepository.findAll().forEach(roles::add);

        return roles;

    }

    public Optional<Role> getRoleById(Integer id) {

        return roleRepository.findById(id);

    }

    public Optional<Role> getRoleByTitle(String title) {

        return roleRepository.findByTitle(title);

    }

    public Optional<Role> updateRoleById(Role role) {

        if (roleRepository.existsById(role.getId())) {

            return Optional.of(roleRepository.save(role));

        } else {

            return Optional.empty();

        }
    }
}

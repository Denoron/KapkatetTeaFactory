package com.propscout.teafactory.services;

import com.propscout.teafactory.models.entities.Role;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.repositories.RoleRepository;
import com.propscout.teafactory.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UsersService(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByNationalId(Long id) {
        return userRepository.findByNationalId(id);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Optional<Role> optionalRole = roleRepository.findByTitle("Farmer");

        if (optionalRole.isEmpty()) {
            Role role = roleRepository.save(new Role("Farmer", "The lowest role in the system"));
            user.setRole(role);
        }

        //Set user role the functional style
        optionalRole.ifPresent(user::setRole);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }


    public boolean updateUserById(User user) {

        Optional<User> optionalUser = userRepository.findById(user.getId());

        //Check whether a user with that Id exits
        if (optionalUser.isEmpty()) {
            return false;
        }

        //Maintain the password though
        user.setPassword(optionalUser.get().getPassword());

        //Save the updated user
        userRepository.save(user);

        return true;
    }
}

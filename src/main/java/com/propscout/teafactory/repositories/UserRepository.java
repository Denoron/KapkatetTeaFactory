package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByNationalId(Long nationalId);

    Optional<User> findByEmail(String email);
}

package com.propscout.teafactory.services;

import com.propscout.teafactory.models.UserAdapter;
import com.propscout.teafactory.models.entities.User;
import com.propscout.teafactory.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final Optional<User> optionalUser = userRepository.findByEmail(s);

        optionalUser.orElseThrow(() -> new UsernameNotFoundException("No user with such login credentials"));

        return optionalUser.map(UserAdapter::new).get();
    }

}

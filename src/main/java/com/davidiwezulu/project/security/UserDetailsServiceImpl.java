package com.davidiwezulu.project.security;

import com.davidiwezulu.project.model.User;
import com.davidiwezulu.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load a user by username or email.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with username or email : " + usernameOrEmail));

        return UserPrincipal.create(user);
    }

    /**
     * Load a user by ID.
     */
    @Transactional
    public UserDetails loadUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with id : " + id));

        return UserPrincipal.create(user);
    }
}


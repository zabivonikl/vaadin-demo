package com.zabivonikl.vaadindemo.data.service;

import com.zabivonikl.vaadindemo.data.entity.User;
import com.zabivonikl.vaadindemo.data.service.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User add(User user) {
        user.setPassword(getEncodedPassword(user));
        return repository.save(user);
    }

    public boolean isLoginAvailable(String login) {
        return repository.findByLogin(login) == null;
    }

    private String getEncodedPassword(User user) {
        return passwordEncoder.encode(user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = repository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}

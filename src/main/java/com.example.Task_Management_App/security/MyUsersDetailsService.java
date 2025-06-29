package com.example.Task_Management_App.security;

import com.example.Task_Management_App.dao.entity.Users;
import com.example.Task_Management_App.dao.repository.UsersRepository;
import com.example.Task_Management_App.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(users.getEmail())
                .password(users.getPassword())
                .authorities("ROLE_" + users.getRoles().getName())
                .build();
    }
}

package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.entity.User;
import com.icbt.pahanaedu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User getUsername(String username) {
        User user = null;
        if (userRepository.findByUsername(username).isPresent()) {
            user = userRepository.findByUsername(username).get();
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndStatusNot(username, Constants.USER_DELETE_STATUS)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}

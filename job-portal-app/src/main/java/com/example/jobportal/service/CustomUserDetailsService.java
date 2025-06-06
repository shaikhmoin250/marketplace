package com.example.jobportal.service;

import com.example.jobportal.model.User;
import com.example.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList; // For roles/authorities if not using a dedicated Role entity yet

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // For simplicity, using an empty list of authorities.
        // In a real app, you would map user roles (e.g., from user.getRoles()) to GrantedAuthority objects.
        // Example: return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            new ArrayList<>() // Empty authorities list for now
            // Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Example if all users are ROLE_USER
        );
    }

    // private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
    //    // Parse roles string and map to SimpleGrantedAuthority
    //    // Example: return Arrays.stream(roles.split(","))
    //    //          .map(SimpleGrantedAuthority::new)
    //    //          .collect(Collectors.toList());
    // }
}

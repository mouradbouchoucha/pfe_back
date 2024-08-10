package com.mrd.server.services;

import com.mrd.server.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<String> emailsList();
    boolean existsByEmail(String email);

    User findUserByEmail(String email);
}

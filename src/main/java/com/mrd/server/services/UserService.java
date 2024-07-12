package com.mrd.server.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<String> emailsList();
    boolean existsByEmail(String email);
}

package com.mrd.server.repositories;

import com.mrd.server.models.Role;
import com.mrd.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByRole(Role role);

    boolean existsByEmail(String email);

}

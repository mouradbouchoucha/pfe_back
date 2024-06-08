package com.mrd.server.repositories;

import com.mrd.server.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    void deleteByToken(String token);
}

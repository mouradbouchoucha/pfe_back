package com.mrd.server.repositories;

import com.mrd.server.dto.CenterDto;
import com.mrd.server.models.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, Long> {

}

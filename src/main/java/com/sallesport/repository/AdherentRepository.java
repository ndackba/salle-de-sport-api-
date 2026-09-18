package com.sallesport.repository;

import com.sallesport.entity.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdherentRepository extends JpaRepository<Adherent, Long> {

    Optional<Adherent> findByEmail(String email);

    boolean existsByEmail(String email);
}

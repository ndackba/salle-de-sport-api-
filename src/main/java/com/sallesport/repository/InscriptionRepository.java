package com.sallesport.repository;

import com.sallesport.entity.CoursCollectif;
import com.sallesport.entity.Inscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    // Utilisé par le service pour vérifier la capacité avant de créer une inscription.
    long countByCours(CoursCollectif cours);

    boolean existsByAdherentIdAndCoursId(Long adherentId, Long coursId);

    Page<Inscription> findByAdherentId(Long adherentId, Pageable pageable);

    Page<Inscription> findByCoursId(Long coursId, Pageable pageable);
}

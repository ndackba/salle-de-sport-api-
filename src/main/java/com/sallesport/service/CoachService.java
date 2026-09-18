package com.sallesport.service;

import com.sallesport.dto.coach.CoachRequest;
import com.sallesport.dto.coach.CoachResponse;
import com.sallesport.entity.Coach;
import com.sallesport.exception.ResourceNotFoundException;
import com.sallesport.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachResponse creer(CoachRequest request) {
        Coach coach = Coach.builder()
                .nom(request.nom())
                .prenom(request.prenom())
                .specialite(request.specialite())
                .build();
        return CoachResponse.from(coachRepository.save(coach));
    }

    @Transactional(readOnly = true)
    public CoachResponse recuperer(Long id) {
        return CoachResponse.from(trouverOuLeverErreur(id));
    }

    @Transactional(readOnly = true)
    public Page<CoachResponse> lister(Pageable pageable) {
        return coachRepository.findAll(pageable).map(CoachResponse::from);
    }

    public CoachResponse modifier(Long id, CoachRequest request) {
        Coach coach = trouverOuLeverErreur(id);
        coach.setNom(request.nom());
        coach.setPrenom(request.prenom());
        coach.setSpecialite(request.specialite());
        return CoachResponse.from(coachRepository.save(coach));
    }

    public void supprimer(Long id) {
        if (!coachRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Coach", id);
        }
        coachRepository.deleteById(id);
    }

    private Coach trouverOuLeverErreur(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Coach", id));
    }
}

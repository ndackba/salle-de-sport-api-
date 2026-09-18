package com.sallesport.service;

import com.sallesport.dto.cours.CoursRequest;
import com.sallesport.dto.cours.CoursResponse;
import com.sallesport.entity.Coach;
import com.sallesport.entity.CoursCollectif;
import com.sallesport.exception.ResourceNotFoundException;
import com.sallesport.repository.CoachRepository;
import com.sallesport.repository.CoursCollectifRepository;
import com.sallesport.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoursCollectifService {

    private final CoursCollectifRepository coursRepository;
    private final CoachRepository coachRepository;
    private final InscriptionRepository inscriptionRepository;

    public CoursResponse creer(CoursRequest request) {
        Coach coach = trouverCoach(request.coachId());

        CoursCollectif cours = CoursCollectif.builder()
                .nom(request.nom())
                .type(request.type())
                .capacite(request.capacite())
                .creneau(request.creneau())
                .salle(request.salle())
                .coach(coach)
                .build();

        return versReponse(coursRepository.save(cours));
    }

    @Transactional(readOnly = true)
    public CoursResponse recuperer(Long id) {
        return versReponse(trouverOuLeverErreur(id));
    }

    @Transactional(readOnly = true)
    public Page<CoursResponse> lister(Pageable pageable) {
        return coursRepository.findAll(pageable).map(this::versReponse);
    }

    public CoursResponse modifier(Long id, CoursRequest request) {
        CoursCollectif cours = trouverOuLeverErreur(id);
        Coach coach = trouverCoach(request.coachId());

        cours.setNom(request.nom());
        cours.setType(request.type());
        cours.setCapacite(request.capacite());
        cours.setCreneau(request.creneau());
        cours.setSalle(request.salle());
        cours.setCoach(coach);

        return versReponse(coursRepository.save(cours));
    }

    public void supprimer(Long id) {
        if (!coursRepository.existsById(id)) {
            throw ResourceNotFoundException.of("CoursCollectif", id);
        }
        coursRepository.deleteById(id);
    }

    private CoursResponse versReponse(CoursCollectif cours) {
        long nombreInscrits = inscriptionRepository.countByCours(cours);
        return CoursResponse.from(cours, nombreInscrits);
    }

    private Coach trouverCoach(Long coachId) {
        return coachRepository.findById(coachId)
                .orElseThrow(() -> ResourceNotFoundException.of("Coach", coachId));
    }

    private CoursCollectif trouverOuLeverErreur(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("CoursCollectif", id));
    }
}

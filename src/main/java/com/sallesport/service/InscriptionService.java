package com.sallesport.service;

import com.sallesport.dto.inscription.InscriptionRequest;
import com.sallesport.dto.inscription.InscriptionResponse;
import com.sallesport.entity.Adherent;
import com.sallesport.entity.CoursCollectif;
import com.sallesport.entity.Inscription;
import com.sallesport.exception.ConflictException;
import com.sallesport.exception.ResourceNotFoundException;
import com.sallesport.repository.AdherentRepository;
import com.sallesport.repository.CoursCollectifRepository;
import com.sallesport.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final AdherentRepository adherentRepository;
    private final CoursCollectifRepository coursRepository;

    public InscriptionResponse creer(InscriptionRequest request) {
        Adherent adherent = adherentRepository.findById(request.adherentId())
                .orElseThrow(() -> ResourceNotFoundException.of("Adherent", request.adherentId()));
        CoursCollectif cours = coursRepository.findById(request.coursId())
                .orElseThrow(() -> ResourceNotFoundException.of("CoursCollectif", request.coursId()));

        if (inscriptionRepository.existsByAdherentIdAndCoursId(adherent.getId(), cours.getId())) {
            throw new ConflictException("Cet adhérent est déjà inscrit à ce cours.");
        }

        long nombreInscrits = inscriptionRepository.countByCours(cours);
        if (nombreInscrits >= cours.getCapacite()) {
            throw new ConflictException("Ce cours a atteint sa capacité maximale (" + cours.getCapacite() + ").");
        }

        Inscription inscription = Inscription.builder()
                .adherent(adherent)
                .cours(cours)
                .dateInscription(request.dateInscription() != null ? request.dateInscription() : LocalDate.now())
                .presence(false)
                .build();

        return InscriptionResponse.from(inscriptionRepository.save(inscription));
    }

    @Transactional(readOnly = true)
    public InscriptionResponse recuperer(Long id) {
        return InscriptionResponse.from(trouverOuLeverErreur(id));
    }

    @Transactional(readOnly = true)
    public Page<InscriptionResponse> lister(Pageable pageable) {
        return inscriptionRepository.findAll(pageable).map(InscriptionResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<InscriptionResponse> listerParAdherent(Long adherentId, Pageable pageable) {
        return inscriptionRepository.findByAdherentId(adherentId, pageable).map(InscriptionResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<InscriptionResponse> listerParCours(Long coursId, Pageable pageable) {
        return inscriptionRepository.findByCoursId(coursId, pageable).map(InscriptionResponse::from);
    }

    /**
     * Marque (ou démarque) la présence d'un adhérent à son inscription.
     * Endpoint dédié plutôt qu'un simple champ dans une mise à jour générale,
     * car c'est une action métier distincte (typiquement faite par le coach).
     */
    public InscriptionResponse marquerPresence(Long id, boolean presence) {
        Inscription inscription = trouverOuLeverErreur(id);
        inscription.setPresence(presence);
        return InscriptionResponse.from(inscriptionRepository.save(inscription));
    }

    public void supprimer(Long id) {
        if (!inscriptionRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Inscription", id);
        }
        inscriptionRepository.deleteById(id);
    }

    private Inscription trouverOuLeverErreur(Long id) {
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Inscription", id));
    }
}

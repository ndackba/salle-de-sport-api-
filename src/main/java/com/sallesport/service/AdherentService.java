package com.sallesport.service;

import com.sallesport.dto.adherent.AdherentRequest;
import com.sallesport.dto.adherent.AdherentResponse;
import com.sallesport.entity.Adherent;
import com.sallesport.exception.ConflictException;
import com.sallesport.exception.ResourceNotFoundException;
import com.sallesport.repository.AdherentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdherentService {

    private final AdherentRepository adherentRepository;

    public AdherentResponse creer(AdherentRequest request) {
        verifierEmailDisponible(request.email(), null);

        Adherent adherent = Adherent.builder()
                .nom(request.nom())
                .prenom(request.prenom())
                .email(request.email())
                .dateNaissance(request.dateNaissance())
                .build();

        return AdherentResponse.from(adherentRepository.save(adherent));
    }

    @Transactional(readOnly = true)
    public AdherentResponse recuperer(Long id) {
        return AdherentResponse.from(trouverOuLeverErreur(id));
    }

    @Transactional(readOnly = true)
    public Page<AdherentResponse> lister(Pageable pageable) {
        return adherentRepository.findAll(pageable).map(AdherentResponse::from);
    }

    public AdherentResponse modifier(Long id, AdherentRequest request) {
        Adherent adherent = trouverOuLeverErreur(id);
        verifierEmailDisponible(request.email(), id);

        adherent.setNom(request.nom());
        adherent.setPrenom(request.prenom());
        adherent.setEmail(request.email());
        adherent.setDateNaissance(request.dateNaissance());

        return AdherentResponse.from(adherentRepository.save(adherent));
    }

    public void supprimer(Long id) {
        if (!adherentRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Adherent", id);
        }
        adherentRepository.deleteById(id);
    }

    private void verifierEmailDisponible(String email, Long idActuel) {
        adherentRepository.findByEmail(email).ifPresent(existant -> {
            if (!existant.getId().equals(idActuel)) {
                throw new ConflictException("Un adhérent utilise déjà l'email " + email);
            }
        });
    }

    private Adherent trouverOuLeverErreur(Long id) {
        return adherentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Adherent", id));
    }
}

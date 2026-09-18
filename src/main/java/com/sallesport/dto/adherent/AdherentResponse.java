package com.sallesport.dto.adherent;

import com.sallesport.entity.Adherent;

import java.time.LocalDate;

public record AdherentResponse(
        Long id,
        String nom,
        String prenom,
        String email,
        LocalDate dateNaissance
) {
    public static AdherentResponse from(Adherent adherent) {
        return new AdherentResponse(
                adherent.getId(),
                adherent.getNom(),
                adherent.getPrenom(),
                adherent.getEmail(),
                adherent.getDateNaissance()
        );
    }
}

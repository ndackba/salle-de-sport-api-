package com.sallesport.dto.inscription;

import com.sallesport.entity.Inscription;

import java.time.LocalDate;

public record InscriptionResponse(
        Long id,
        Long adherentId,
        String adherentNomComplet,
        Long coursId,
        String coursNom,
        LocalDate dateInscription,
        boolean presence
) {
    public static InscriptionResponse from(Inscription inscription) {
        return new InscriptionResponse(
                inscription.getId(),
                inscription.getAdherent().getId(),
                inscription.getAdherent().getPrenom() + " " + inscription.getAdherent().getNom(),
                inscription.getCours().getId(),
                inscription.getCours().getNom(),
                inscription.getDateInscription(),
                inscription.isPresence()
        );
    }
}

package com.sallesport.dto.cours;

import com.sallesport.entity.CoursCollectif;

public record CoursResponse(
        Long id,
        String nom,
        String type,
        Integer capacite,
        String creneau,
        String salle,
        Long coachId,
        String coachNomComplet,
        long nombreInscrits
) {
    /**
     * nombreInscrits doit être calculé par le service (le nombre d'inscriptions
     * n'est pas stocké sur l'entité elle-même) : voir CoursCollectifService.
     */
    public static CoursResponse from(CoursCollectif cours, long nombreInscrits) {
        return new CoursResponse(
                cours.getId(),
                cours.getNom(),
                cours.getType(),
                cours.getCapacite(),
                cours.getCreneau(),
                cours.getSalle(),
                cours.getCoach().getId(),
                cours.getCoach().getPrenom() + " " + cours.getCoach().getNom(),
                nombreInscrits
        );
    }
}

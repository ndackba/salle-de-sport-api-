package com.sallesport.dto.coach;

import com.sallesport.entity.Coach;

public record CoachResponse(
        Long id,
        String nom,
        String prenom,
        String specialite
) {
    public static CoachResponse from(Coach coach) {
        return new CoachResponse(coach.getId(), coach.getNom(), coach.getPrenom(), coach.getSpecialite());
    }
}

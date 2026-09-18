package com.sallesport.dto.coach;

import jakarta.validation.constraints.NotBlank;

public record CoachRequest(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,

        @NotBlank(message = "La spécialité est obligatoire")
        String specialite
) {
}

package com.sallesport.dto.cours;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CoursRequest(
        @NotBlank(message = "Le nom du cours est obligatoire")
        String nom,

        @NotBlank(message = "Le type est obligatoire")
        String type,

        @NotNull(message = "La capacité est obligatoire")
        @Positive(message = "La capacité doit être positive")
        Integer capacite,

        @NotBlank(message = "Le créneau est obligatoire")
        String creneau,

        @NotBlank(message = "La salle est obligatoire")
        String salle,

        @NotNull(message = "L'identifiant du coach est obligatoire")
        Long coachId
) {
}

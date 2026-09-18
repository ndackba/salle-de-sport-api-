package com.sallesport.dto.inscription;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscriptionRequest(
        @NotNull(message = "L'identifiant de l'adhérent est obligatoire")
        Long adherentId,

        @NotNull(message = "L'identifiant du cours est obligatoire")
        Long coursId,

        // Optionnel : si absent, le service utilise la date du jour.
        LocalDate dateInscription
) {
}

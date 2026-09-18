package com.sallesport.controller;

import com.sallesport.dto.inscription.InscriptionRequest;
import com.sallesport.dto.inscription.InscriptionResponse;
import com.sallesport.service.InscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
@Tag(name = "Inscriptions", description = "Inscriptions des adhérents aux cours collectifs")
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @PostMapping
    @Operation(summary = "Inscrire un adhérent à un cours (vérifie la capacité et les doublons)")
    public ResponseEntity<InscriptionResponse> creer(@Valid @RequestBody InscriptionRequest request) {
        InscriptionResponse cree = inscriptionService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @GetMapping("/{id}")
    public InscriptionResponse recuperer(@PathVariable Long id) {
        return inscriptionService.recuperer(id);
    }

    @GetMapping
    @Operation(summary = "Lister les inscriptions, avec filtres optionnels par adhérent ou par cours")
    public Page<InscriptionResponse> lister(
            @RequestParam(required = false) Long adherentId,
            @RequestParam(required = false) Long coursId,
            Pageable pageable
    ) {
        if (adherentId != null) {
            return inscriptionService.listerParAdherent(adherentId, pageable);
        }
        if (coursId != null) {
            return inscriptionService.listerParCours(coursId, pageable);
        }
        return inscriptionService.lister(pageable);
    }

    @PatchMapping("/{id}/presence")
    @Operation(summary = "Marquer (ou démarquer) la présence d'un adhérent à ce cours")
    public InscriptionResponse marquerPresence(@PathVariable Long id, @RequestParam boolean presence) {
        return inscriptionService.marquerPresence(id, presence);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une inscription")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        inscriptionService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}

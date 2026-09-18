package com.sallesport.controller;

import com.sallesport.dto.adherent.AdherentRequest;
import com.sallesport.dto.adherent.AdherentResponse;
import com.sallesport.service.AdherentService;
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
@RequestMapping("/api/adherents")
@RequiredArgsConstructor
@Tag(name = "Adhérents", description = "Gestion des adhérents de la salle de sport")
public class AdherentController {

    private final AdherentService adherentService;

    @PostMapping
    @Operation(summary = "Créer un nouvel adhérent")
    public ResponseEntity<AdherentResponse> creer(@Valid @RequestBody AdherentRequest request) {
        AdherentResponse cree = adherentService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un adhérent par son id")
    public AdherentResponse recuperer(@PathVariable Long id) {
        return adherentService.recuperer(id);
    }

    @GetMapping
    @Operation(summary = "Lister les adhérents (paginé, triable : ex. ?page=0&size=20&sort=nom,asc)")
    public Page<AdherentResponse> lister(Pageable pageable) {
        return adherentService.lister(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un adhérent")
    public AdherentResponse modifier(@PathVariable Long id, @Valid @RequestBody AdherentRequest request) {
        return adherentService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un adhérent")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        adherentService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}

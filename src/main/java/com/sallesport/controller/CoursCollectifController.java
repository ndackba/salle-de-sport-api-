package com.sallesport.controller;

import com.sallesport.dto.cours.CoursRequest;
import com.sallesport.dto.cours.CoursResponse;
import com.sallesport.service.CoursCollectifService;
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
@RequestMapping("/api/cours")
@RequiredArgsConstructor
@Tag(name = "Cours collectifs", description = "Gestion des cours collectifs")
public class CoursCollectifController {

    private final CoursCollectifService coursService;

    @PostMapping
    @Operation(summary = "Créer un nouveau cours collectif")
    public ResponseEntity<CoursResponse> creer(@Valid @RequestBody CoursRequest request) {
        CoursResponse cree = coursService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un cours par son id")
    public CoursResponse recuperer(@PathVariable Long id) {
        return coursService.recuperer(id);
    }

    @GetMapping
    @Operation(summary = "Lister les cours (paginé, triable : ex. ?page=0&size=20&sort=nom,asc)")
    public Page<CoursResponse> lister(Pageable pageable) {
        return coursService.lister(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un cours")
    public CoursResponse modifier(@PathVariable Long id, @Valid @RequestBody CoursRequest request) {
        return coursService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un cours")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        coursService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}

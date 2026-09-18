package com.sallesport.controller;

import com.sallesport.dto.coach.CoachRequest;
import com.sallesport.dto.coach.CoachResponse;
import com.sallesport.service.CoachService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coachs")
@RequiredArgsConstructor
@Tag(name = "Coachs", description = "Gestion des coachs")
public class CoachController {

    private final CoachService coachService;

    @PostMapping
    public ResponseEntity<CoachResponse> creer(@Valid @RequestBody CoachRequest request) {
        CoachResponse cree = coachService.creer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    @GetMapping("/{id}")
    public CoachResponse recuperer(@PathVariable Long id) {
        return coachService.recuperer(id);
    }

    @GetMapping
    public Page<CoachResponse> lister(Pageable pageable) {
        return coachService.lister(pageable);
    }

    @PutMapping("/{id}")
    public CoachResponse modifier(@PathVariable Long id, @Valid @RequestBody CoachRequest request) {
        return coachService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        coachService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}

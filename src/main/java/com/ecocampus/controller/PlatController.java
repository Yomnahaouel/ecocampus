package com.ecocampus.controller;

import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.service.PlatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    @Autowired
    private PlatService platService;

    // Routes publiques
    @GetMapping
    public ResponseEntity<List<PlatResponse>> getAllPlats() {
        return ResponseEntity.ok(platService.getAllPlats());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<PlatResponse>> getPlatsDisponibles() {
        return ResponseEntity.ok(platService.getPlatsDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatResponse> getPlatById(@PathVariable Long id) {
        return ResponseEntity.ok(platService.getPlatById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<PlatResponse>> getPlatsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(platService.getPlatsByRestaurant(restaurantId));
    }

    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<PlatResponse>> getPlatsByCategorie(@PathVariable Long categorieId) {
        return ResponseEntity.ok(platService.getPlatsByCategorie(categorieId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlatResponse>> searchPlats(@RequestParam String nom) {
        return ResponseEntity.ok(platService.searchPlatsByNom(nom));
    }

    // Routes protégées (nécessitent authentification)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    public ResponseEntity<PlatResponse> createPlat(@Valid @RequestBody PlatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platService.createPlat(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    public ResponseEntity<PlatResponse> updatePlat(@PathVariable Long id, @Valid @RequestBody PlatRequest request) {
        return ResponseEntity.ok(platService.updatePlat(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    public ResponseEntity<Void> deletePlat(@PathVariable Long id) {
        platService.deletePlat(id);
        return ResponseEntity.noContent().build();
    }
}
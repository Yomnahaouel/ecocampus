package com.ecocampus.controller;

import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.service.CatalogueService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    private final CatalogueService catalogueService;

    public PlatController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping
    public ResponseEntity<List<PlatResponse>> findAll() {
        return ResponseEntity.ok(catalogueService.findPlats());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<PlatResponse>> findDisponibles() {
        return ResponseEntity.ok(catalogueService.findPlatsDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(catalogueService.findPlat(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlatResponse>> search(@RequestParam String nom) {
        return ResponseEntity.ok(catalogueService.searchPlats(nom));
    }

    @GetMapping("/categorie/{id}")
    public ResponseEntity<List<PlatResponse>> byCategorie(@PathVariable Long id) {
        return ResponseEntity.ok(catalogueService.findPlatsByCategorie(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<PlatResponse> create(@Valid @RequestBody PlatRequest request) {
        return ResponseEntity.ok(catalogueService.createPlat(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<PlatResponse> update(@PathVariable Long id, @Valid @RequestBody PlatRequest request) {
        return ResponseEntity.ok(catalogueService.updatePlat(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catalogueService.deletePlat(id);
        return ResponseEntity.noContent().build();
    }
}

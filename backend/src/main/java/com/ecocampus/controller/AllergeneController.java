package com.ecocampus.controller;

import com.ecocampus.dto.request.AllergeneRequest;
import com.ecocampus.dto.response.AllergeneResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/allergenes")
public class AllergeneController {

    private final CatalogueService catalogueService;

    public AllergeneController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    @GetMapping
    public ResponseEntity<List<AllergeneResponse>> findAll() {
        return ResponseEntity.ok(catalogueService.findAllergenes());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AllergeneResponse> create(@Valid @RequestBody AllergeneRequest request) {
        return ResponseEntity.ok(catalogueService.createAllergene(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AllergeneResponse> update(@PathVariable Long id, @Valid @RequestBody AllergeneRequest request) {
        return ResponseEntity.ok(catalogueService.updateAllergene(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        catalogueService.deleteAllergene(id);
        return ResponseEntity.noContent().build();
    }
}

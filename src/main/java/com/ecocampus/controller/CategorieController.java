package com.ecocampus.controller;

import com.ecocampus.entity.Categorie;
import com.ecocampus.repository.CategorieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        return ResponseEntity.ok(categorieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        return categorieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieRepository.save(categorie));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @Valid @RequestBody Categorie categorie) {
        if (!categorieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categorie.setId(id);
        return ResponseEntity.ok(categorieRepository.save(categorie));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        if (!categorieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
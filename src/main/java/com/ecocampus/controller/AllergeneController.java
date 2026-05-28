package com.ecocampus.controller;

import com.ecocampus.entity.Allergene;
import com.ecocampus.repository.AllergeneRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/allergenes")
public class AllergeneController {

    @Autowired
    private AllergeneRepository allergeneRepository;

    @GetMapping
    public ResponseEntity<List<Allergene>> getAllAllergenes() {
        return ResponseEntity.ok(allergeneRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Allergene> getAllergeneById(@PathVariable Long id) {
        return allergeneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Allergene> createAllergene(@Valid @RequestBody Allergene allergene) {
        return ResponseEntity.status(HttpStatus.CREATED).body(allergeneRepository.save(allergene));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Allergene> updateAllergene(@PathVariable Long id, @Valid @RequestBody Allergene allergene) {
        if (!allergeneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        allergene.setId(id);
        return ResponseEntity.ok(allergeneRepository.save(allergene));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllergene(@PathVariable Long id) {
        if (!allergeneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        allergeneRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
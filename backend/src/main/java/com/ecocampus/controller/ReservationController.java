package com.ecocampus.controller;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.service.ReservationService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> create(
            Principal principal,
            @Valid @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(reservationService.create(principal.getName(), request));
    }

    @GetMapping({"/me", "/user"})
    public ResponseEntity<List<ReservationResponse>> myReservations(Principal principal) {
        return ResponseEntity.ok(reservationService.myReservations(principal.getName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<List<ReservationResponse>> allReservations() {
        return ResponseEntity.ok(reservationService.allReservations());
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<List<ReservationResponse>> restaurantReservations(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reservationService.restaurantReservations(restaurantId));
    }

    @PutMapping("/{id}/annuler")
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> annuler(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(reservationService.annuler(id, principal.getName()));
    }

    @PutMapping("/{id}/confirmer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CAFETERIA_RESP')")
    public ResponseEntity<ReservationResponse> confirmer(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.confirmer(id));
    }
}

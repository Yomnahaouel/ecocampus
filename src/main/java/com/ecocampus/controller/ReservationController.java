package com.ecocampus.controller;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(request, userEmail));
    }

    @PutMapping("/{id}/annuler")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<ReservationResponse> annulerReservation(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return ResponseEntity.ok(reservationService.annulerReservation(id, userEmail));
    }

    @PutMapping("/{id}/confirmer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> confirmerReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.confirmerReservation(id));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<List<ReservationResponse>> getMyReservations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return ResponseEntity.ok(reservationService.getReservationsByUser(userEmail));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESTAURANT')")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reservationService.getReservationsByRestaurant(restaurantId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
}
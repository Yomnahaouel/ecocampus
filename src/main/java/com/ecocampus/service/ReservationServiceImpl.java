package com.ecocampus.service;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.entity.*;
import com.ecocampus.entity.enums.StatutReservation;
import com.ecocampus.mapper.ReservationMapper;
import com.ecocampus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Plat plat = platRepository.findById(request.getPlatId())
                .orElseThrow(() -> new RuntimeException("Plat non trouvé"));

        if (plat.getQuantiteDisponible() <= 0) {
            throw new RuntimeException("Ce plat n'est plus disponible");
        }

        // Réduire la quantité disponible
        plat.setQuantiteDisponible(plat.getQuantiteDisponible() - 1);
        platRepository.save(plat);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPlat(plat);
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponse(savedReservation);
    }

    @Override
    @Transactional
    public ReservationResponse annulerReservation(Long id, String userEmail) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        if (!reservation.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Vous ne pouvez annuler que vos propres réservations");
        }

        reservation.setStatut(StatutReservation.ANNULEE);

        // Remettre la quantité disponible
        Plat plat = reservation.getPlat();
        plat.setQuantiteDisponible(plat.getQuantiteDisponible() + 1);
        platRepository.save(plat);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponse(updatedReservation);
    }

    @Override
    @Transactional
    public ReservationResponse confirmerReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        reservation.setStatut(StatutReservation.CONFIRMEE);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponse(updatedReservation);
    }

    @Override
    public List<ReservationResponse> getReservationsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return reservationRepository.findByUserId(user.getId()).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationsByRestaurant(Long restaurantId) {
        return reservationRepository.findReservationsByRestaurantId(restaurantId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
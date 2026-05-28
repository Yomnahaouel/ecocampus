package com.ecocampus.service;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.entity.Plat;
import com.ecocampus.entity.Reservation;
import com.ecocampus.entity.User;
import com.ecocampus.entity.enums.ReservationStatus;
import com.ecocampus.mapper.ReservationMapper;
import com.ecocampus.repository.PlatRepository;
import com.ecocampus.repository.ReservationRepository;
import com.ecocampus.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PlatRepository platRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            PlatRepository platRepository,
            ReservationMapper reservationMapper
    ) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.platRepository = platRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    @Transactional
    public ReservationResponse create(String email, ReservationRequest request) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable."));
        Plat plat = platRepository.findById(request.getPlatId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plat introuvable."));

        if (!plat.isDisponible()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce plat n'est pas disponible.");
        }
        if (plat.getQuantite() == null || plat.getQuantite() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantite insuffisante.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPlat(plat);
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.EN_ATTENTE);
        plat.setQuantite(plat.getQuantite() - 1);
        plat.setDisponible(plat.getQuantite() > 0);

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> myReservations(String email) {
        return reservationRepository.findByUserEmailOrderByDateReservationDesc(email.toLowerCase()).stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> allReservations() {
        return reservationRepository.findAllByOrderByDateReservationDesc().stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> restaurantReservations(Long restaurantId) {
        return reservationRepository.findByPlatRestaurantIdOrderByDateReservationDesc(restaurantId).stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponse annuler(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation introuvable."));
        if (!reservation.getUser().getEmail().equalsIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu ne peux annuler que tes reservations.");
        }
        if (reservation.getStatus() != ReservationStatus.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seule une reservation en attente peut etre annulee.");
        }
        reservation.setStatus(ReservationStatus.ANNULEE);
        Plat plat = reservation.getPlat();
        plat.setQuantite((plat.getQuantite() == null ? 0 : plat.getQuantite()) + 1);
        plat.setDisponible(true);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public ReservationResponse confirmer(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation introuvable."));
        if (reservation.getStatus() != ReservationStatus.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seule une reservation en attente peut etre confirmee.");
        }
        reservation.setStatus(ReservationStatus.CONFIRMEE);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }
}

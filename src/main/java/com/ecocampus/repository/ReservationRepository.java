package com.ecocampus.repository;

import com.ecocampus.entity.Reservation;
import com.ecocampus.entity.enums.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Trouver les réservations d'un utilisateur
    List<Reservation> findByUserId(Long userId);

    // Trouver les réservations d'un plat
    List<Reservation> findByPlatId(Long platId);

    // Trouver les réservations par statut
    List<Reservation> findByStatut(StatutReservation statut);

    // Trouver les réservations par période
    List<Reservation> findByDateReservationBetween(LocalDateTime debut, LocalDateTime fin);

    // Trouver les réservations d'un restaurant (via le plat)
    @Query("SELECT r FROM Reservation r JOIN r.plat p WHERE p.restaurant.id = :restaurantId")
    List<Reservation> findReservationsByRestaurantId(@Param("restaurantId") Long restaurantId);

    // Annuler une réservation (requête de modification)
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.statut = 'ANNULEE' WHERE r.id = :reservationId")
    int annulerReservation(@Param("reservationId") Long reservationId);

    // Compter le nombre de réservations confirmées pour un plat
    long countByPlatIdAndStatut(Long platId, StatutReservation statut);
}
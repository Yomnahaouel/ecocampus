package com.ecocampus.repository;

import com.ecocampus.entity.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserEmailOrderByDateReservationDesc(String email);

    List<Reservation> findAllByOrderByDateReservationDesc();

    List<Reservation> findByPlatRestaurantIdOrderByDateReservationDesc(Long restaurantId);
}

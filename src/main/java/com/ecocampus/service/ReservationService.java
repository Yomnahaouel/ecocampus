package com.ecocampus.service;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest request, String userEmail);
    ReservationResponse annulerReservation(Long id, String userEmail);
    ReservationResponse confirmerReservation(Long id); // Pour admin
    List<ReservationResponse> getReservationsByUser(String userEmail);
    List<ReservationResponse> getReservationsByRestaurant(Long restaurantId);
    List<ReservationResponse> getAllReservations();
}
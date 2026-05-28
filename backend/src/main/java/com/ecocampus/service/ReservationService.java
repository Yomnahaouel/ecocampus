package com.ecocampus.service;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import java.util.List;

public interface ReservationService {

    ReservationResponse create(String email, ReservationRequest request);

    List<ReservationResponse> myReservations(String email);

    List<ReservationResponse> allReservations();

    List<ReservationResponse> restaurantReservations(Long restaurantId);

    ReservationResponse annuler(Long id, String email);

    ReservationResponse confirmer(Long id);
}

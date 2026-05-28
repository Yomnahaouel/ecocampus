package com.ecocampus.service;

import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.response.PlatResponse;
import java.util.List;

public interface PlatService {
    PlatResponse createPlat(PlatRequest request);
    PlatResponse updatePlat(Long id, PlatRequest request);
    void deletePlat(Long id);
    PlatResponse getPlatById(Long id);
    List<PlatResponse> getAllPlats();
    List<PlatResponse> getPlatsByRestaurant(Long restaurantId);
    List<PlatResponse> getPlatsByCategorie(Long categorieId);
    List<PlatResponse> getPlatsDisponibles();
    List<PlatResponse> searchPlatsByNom(String nom);
}
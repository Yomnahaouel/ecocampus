package com.ecocampus.service;

import com.ecocampus.dto.request.CategorieRequest;
import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.request.RestaurantRequest;
import com.ecocampus.dto.request.AllergeneRequest;
import com.ecocampus.dto.response.AllergeneResponse;
import com.ecocampus.dto.response.CategorieResponse;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.dto.response.RestaurantResponse;
import java.util.List;

public interface CatalogueService {

    List<RestaurantResponse> findRestaurants();

    RestaurantResponse createRestaurant(RestaurantRequest request);

    RestaurantResponse updateRestaurant(Long id, RestaurantRequest request);

    void deleteRestaurant(Long id);

    List<CategorieResponse> findCategories();

    CategorieResponse createCategorie(CategorieRequest request);

    CategorieResponse updateCategorie(Long id, CategorieRequest request);

    void deleteCategorie(Long id);

    List<AllergeneResponse> findAllergenes();

    AllergeneResponse createAllergene(AllergeneRequest request);

    AllergeneResponse updateAllergene(Long id, AllergeneRequest request);

    void deleteAllergene(Long id);

    List<PlatResponse> findPlats();

    List<PlatResponse> findPlatsDisponibles();

    PlatResponse findPlat(Long id);

    List<PlatResponse> searchPlats(String nom);

    List<PlatResponse> findPlatsByCategorie(Long categorieId);

    PlatResponse createPlat(PlatRequest request);

    PlatResponse updatePlat(Long id, PlatRequest request);

    void deletePlat(Long id);
}

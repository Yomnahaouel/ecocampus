package com.ecocampus.service;

import com.ecocampus.dto.request.AllergeneRequest;
import com.ecocampus.dto.request.CategorieRequest;
import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.request.RestaurantRequest;
import com.ecocampus.dto.response.AllergeneResponse;
import com.ecocampus.dto.response.CategorieResponse;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.dto.response.RestaurantResponse;
import com.ecocampus.entity.Allergene;
import com.ecocampus.entity.Categorie;
import com.ecocampus.entity.Plat;
import com.ecocampus.entity.Restaurant;
import com.ecocampus.mapper.AllergeneMapper;
import com.ecocampus.mapper.CategorieMapper;
import com.ecocampus.mapper.PlatMapper;
import com.ecocampus.mapper.RestaurantMapper;
import com.ecocampus.repository.AllergeneRepository;
import com.ecocampus.repository.CategorieRepository;
import com.ecocampus.repository.PlatRepository;
import com.ecocampus.repository.RestaurantRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CatalogueServiceImpl implements CatalogueService {

    private final RestaurantRepository restaurantRepository;
    private final CategorieRepository categorieRepository;
    private final PlatRepository platRepository;
    private final AllergeneRepository allergeneRepository;
    private final RestaurantMapper restaurantMapper;
    private final CategorieMapper categorieMapper;
    private final PlatMapper platMapper;
    private final AllergeneMapper allergeneMapper;

    public CatalogueServiceImpl(
            RestaurantRepository restaurantRepository,
            CategorieRepository categorieRepository,
            PlatRepository platRepository,
            AllergeneRepository allergeneRepository,
            RestaurantMapper restaurantMapper,
            CategorieMapper categorieMapper,
            PlatMapper platMapper,
            AllergeneMapper allergeneMapper
    ) {
        this.restaurantRepository = restaurantRepository;
        this.categorieRepository = categorieRepository;
        this.platRepository = platRepository;
        this.allergeneRepository = allergeneRepository;
        this.restaurantMapper = restaurantMapper;
        this.categorieMapper = categorieMapper;
        this.platMapper = platMapper;
        this.allergeneMapper = allergeneMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> findRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        Restaurant restaurant = restaurantMapper.toEntity(request);
        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant introuvable."));
        restaurant.setNom(request.getNom());
        restaurant.setAdresse(request.getAdresse());
        restaurant.setTelephone(request.getTelephone());
        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieResponse> findCategories() {
        return categorieRepository.findAll().stream()
                .map(categorieMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CategorieResponse createCategorie(CategorieRequest request) {
        Categorie categorie = categorieMapper.toEntity(request);
        return categorieMapper.toResponse(categorieRepository.save(categorie));
    }

    @Override
    @Transactional
    public CategorieResponse updateCategorie(Long id, CategorieRequest request) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable."));
        categorie.setNom(request.getNom());
        categorie.setDescription(request.getDescription());
        return categorieMapper.toResponse(categorieRepository.save(categorie));
    }

    @Override
    @Transactional
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllergeneResponse> findAllergenes() {
        return allergeneRepository.findAll().stream()
                .map(allergeneMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AllergeneResponse createAllergene(AllergeneRequest request) {
        Allergene allergene = allergeneMapper.toEntity(request);
        return allergeneMapper.toResponse(allergeneRepository.save(allergene));
    }

    @Override
    @Transactional
    public AllergeneResponse updateAllergene(Long id, AllergeneRequest request) {
        Allergene allergene = allergeneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Allergene introuvable."));
        allergene.setNom(request.getNom());
        allergene.setDescription(request.getDescription());
        return allergeneMapper.toResponse(allergeneRepository.save(allergene));
    }

    @Override
    @Transactional
    public void deleteAllergene(Long id) {
        allergeneRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatResponse> findPlats() {
        return platRepository.findAll().stream()
                .map(platMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatResponse> findPlatsDisponibles() {
        return platRepository.findByDisponibleTrue().stream()
                .filter(plat -> plat.getQuantite() != null && plat.getQuantite() > 0)
                .map(platMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlatResponse findPlat(Long id) {
        return platRepository.findById(id)
                .map(platMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plat introuvable."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatResponse> searchPlats(String nom) {
        return platRepository.findByNomContainingIgnoreCase(nom == null ? "" : nom).stream()
                .map(platMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatResponse> findPlatsByCategorie(Long categorieId) {
        return platRepository.findByCategorieId(categorieId).stream()
                .map(platMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PlatResponse createPlat(PlatRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant introuvable."));
        Categorie categorie = categorieRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable."));

        Plat plat = new Plat();
        applyPlatRequest(plat, request, restaurant, categorie);
        return platMapper.toResponse(platRepository.save(plat));
    }

    @Override
    @Transactional
    public PlatResponse updatePlat(Long id, PlatRequest request) {
        Plat plat = platRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plat introuvable."));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant introuvable."));
        Categorie categorie = categorieRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categorie introuvable."));
        applyPlatRequest(plat, request, restaurant, categorie);
        return platMapper.toResponse(platRepository.save(plat));
    }

    @Override
    @Transactional
    public void deletePlat(Long id) {
        platRepository.deleteById(id);
    }

    private void applyPlatRequest(Plat plat, PlatRequest request, Restaurant restaurant, Categorie categorie) {
        plat.setNom(request.getNom());
        plat.setDescription(request.getDescription());
        plat.setPrix(request.getPrix());
        plat.setQuantite(request.getQuantite());
        plat.setPhotoUrl(request.getPhotoUrl());
        plat.setDisponible(request.isDisponible() && request.getQuantite() != null && request.getQuantite() > 0);
        plat.setRestaurant(restaurant);
        plat.setCategorie(categorie);
        plat.setAllergenes(allergeneRepository.findAllById(
                request.getAllergeneIds() == null ? Collections.emptyList() : request.getAllergeneIds()
        ));
    }
}

package com.ecocampus.service;

import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.entity.*;
import com.ecocampus.mapper.PlatMapper;
import com.ecocampus.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatServiceImpl implements PlatService {

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private AllergeneRepository allergeneRepository;

    @Autowired
    private PlatMapper platMapper;

    @Override
    @Transactional
    public PlatResponse createPlat(PlatRequest request) {
        Plat plat = platMapper.toEntity(request);

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant non trouvé"));
        plat.setRestaurant(restaurant);

        Categorie categorie = categorieRepository.findById(request.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        plat.setCategorie(categorie);

        if (request.getAllergeneIds() != null) {
            List<Allergene> allergenes = allergeneRepository.findAllById(request.getAllergeneIds());
            plat.setAllergenes(allergenes);
        }

        Plat savedPlat = platRepository.save(plat);
        return platMapper.toResponse(savedPlat);
    }

    @Override
    @Transactional
    public PlatResponse updatePlat(Long id, PlatRequest request) {
        Plat plat = platRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plat non trouvé"));

        plat.setNom(request.getNom());
        plat.setDescription(request.getDescription());
        plat.setPrix(request.getPrix());
        plat.setQuantiteDisponible(request.getQuantiteDisponible());
        plat.setPhotoUrl(request.getPhotoUrl());

        if (request.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(request.getCategorieId())
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            plat.setCategorie(categorie);
        }

        if (request.getAllergeneIds() != null) {
            List<Allergene> allergenes = allergeneRepository.findAllById(request.getAllergeneIds());
            plat.setAllergenes(allergenes);
        }

        Plat updatedPlat = platRepository.save(plat);
        return platMapper.toResponse(updatedPlat);
    }

    @Override
    @Transactional
    public void deletePlat(Long id) {
        platRepository.deleteById(id);
    }

    @Override
    public PlatResponse getPlatById(Long id) {
        Plat plat = platRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plat non trouvé"));
        return platMapper.toResponse(plat);
    }

    @Override
    public List<PlatResponse> getAllPlats() {
        return platRepository.findAll().stream()
                .map(platMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatResponse> getPlatsByRestaurant(Long restaurantId) {
        return platRepository.findByRestaurantId(restaurantId).stream()
                .map(platMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatResponse> getPlatsByCategorie(Long categorieId) {
        return platRepository.findByCategorieId(categorieId).stream()
                .map(platMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatResponse> getPlatsDisponibles() {
        return platRepository.findByQuantiteDisponibleGreaterThan(0).stream()
                .map(platMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatResponse> searchPlatsByNom(String nom) {
        return platRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(platMapper::toResponse)
                .collect(Collectors.toList());
    }
}
package com.ecocampus.repository;

import com.ecocampus.entity.Plat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatRepository extends JpaRepository<Plat, Long> {

    List<Plat> findByDisponibleTrue();

    List<Plat> findByDisponibleTrueAndQuantiteGreaterThan(Integer quantite);

    List<Plat> findByRestaurantId(Long restaurantId);

    List<Plat> findByNomContainingIgnoreCase(String nom);

    List<Plat> findByCategorieId(Long categorieId);
}

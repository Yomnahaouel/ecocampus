package com.ecocampus.repository;

import com.ecocampus.entity.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {

    // Trouver les plats par restaurant
    List<Plat> findByRestaurantId(Long restaurantId);

    // Trouver les plats par catégorie
    List<Plat> findByCategorieId(Long categorieId);

    // Trouver les plats par prix maximum
    List<Plat> findByPrixLessThanEqual(Double prix);

    // Trouver les plats disponibles (quantité > 0)
    List<Plat> findByQuantiteDisponibleGreaterThan(Integer quantite);

    // Trouver les plats par nom (recherche partielle)
    List<Plat> findByNomContainingIgnoreCase(String nom);

    // Trouver les plats par allergène (via relation ManyToMany)
    @Query("SELECT p FROM Plat p JOIN p.allergenes a WHERE a.id = :allergeneId")
    List<Plat> findPlatsByAllergeneId(@Param("allergeneId") Long allergeneId);

    // Trouver les plats avec JOIN FETCH pour éviter le problème N+1
    @Query("SELECT p FROM Plat p JOIN FETCH p.categorie WHERE p.restaurant.id = :restaurantId")
    List<Plat> findPlatsByRestaurantIdWithCategorie(@Param("restaurantId") Long restaurantId);
}
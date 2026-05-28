package com.ecocampus.repository;

import com.ecocampus.entity.Categorie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    Optional<Categorie> findByNom(String nom);
}

